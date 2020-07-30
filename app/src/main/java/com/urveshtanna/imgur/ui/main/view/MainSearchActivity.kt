package com.urveshtanna.imgur.ui.main.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.error.ANError
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.local.DataManager
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.data.remote.APIHelper
import com.urveshtanna.imgur.data.remote.APIServiceImpl
import com.urveshtanna.imgur.databinding.ActivityMainSearchBinding
import com.urveshtanna.imgur.ui.base.ViewModelFactory
import com.urveshtanna.imgur.ui.gallerydetails.view.GalleryDetailsActivity
import com.urveshtanna.imgur.ui.main.adapter.MainSearchResultAdapter
import com.urveshtanna.imgur.ui.main.navigator.MainSearchNavigator
import com.urveshtanna.imgur.ui.main.viewmodel.MainSearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainSearchActivity : AppCompatActivity(), MainSearchNavigator {

    private val SAVED_SEARCH_QUERY: String = "search_query"
    lateinit var binding: ActivityMainSearchBinding
    lateinit var adapter: MainSearchResultAdapter
    lateinit var mainSearchViewModel: MainSearchViewModel
    var searchView: SearchView? = null
    var currentQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_search)
        if (savedInstanceState != null)
            currentQuery = savedInstanceState.getString(SAVED_SEARCH_QUERY);
        setupUI()
        setupViewModel();
    }

    private fun setupViewModel() {
        mainSearchViewModel = ViewModelProvider(
            this,
            ViewModelFactory(APIHelper(APIServiceImpl()), DataManager(this))
        ).get(MainSearchViewModel::class.java)
        binding.viewModel = mainSearchViewModel
        mainSearchViewModel.setNavigator(this)
    }

    private fun setupUI() {
        binding.searchResultRecyclerview.layoutManager = GridLayoutManager(this, 3)
        adapter = MainSearchResultAdapter(arrayListOf(), { galleryData, view ->
            openImageDetailsScreen(galleryData, view)
        })
        binding.searchResultRecyclerview.adapter = adapter
        (binding.searchResultRecyclerview.layoutManager as GridLayoutManager).spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.getItemViewType(position) == adapter.ITEM_TYPE_LOADER) 3 else 1
                }
            }
        binding.searchResultRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: GridLayoutManager? =
                    recyclerView.layoutManager as GridLayoutManager?

                if (!mainSearchViewModel.isRecyclerViewLoading && mainSearchViewModel.hasMoreSearchResultData && linearLayoutManager != null) {
                    val visibleItemCount: Int = linearLayoutManager.getChildCount()
                    val totalItemCount: Int = linearLayoutManager.getItemCount()
                    val firstVisibleItemPosition: Int =
                        linearLayoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                        //End of list
                        loadMore()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        mainSearchViewModel.isRecyclerViewLoading = true
        mainSearchViewModel.getGalleryFromSearchQuery(searchView?.query.toString())
    }

    override fun handleError(error: Throwable) {
        if ((error is ANError) && error.getErrorCode() != 0) {
            // received error from server
            Log.d(this::class.simpleName, "onError errorCode : " + error.getErrorCode())
            Log.d(this::class.simpleName, "onError errorBody : " + error.getErrorBody())
            Log.d(this::class.simpleName, "onError errorDetail : " + error.getErrorDetail())
            if (error.errorCode == 403) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }

    override fun loadNewData(galleryData: List<GalleryData>) {
        Log.d(this::class.simpleName, "New Search Result Count " + galleryData.size.toString())
        adapter.addNewData(galleryData)
        adapter.notifyDataSetChanged()
    }

    override fun loadData(galleryData: List<GalleryData>) {
        Log.d(this::class.simpleName, "More Search Result Count " + galleryData.size.toString())
        adapter.addData(galleryData)
        adapter.notifyDataSetChanged()
        Log.d(
            this::class.simpleName,
            "Search Result Adapter Item Count " + adapter.itemCount.toString()
        )
    }

    @SuppressLint("CheckResult")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_gallery_search_activity, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        RxSearchObservable.fromView(searchView)
            .debounce(250, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(this::class.simpleName, it)
                if (it != null && !it.trim().isEmpty()) {
                    mainSearchViewModel.resetPaginationValues()
                    mainSearchViewModel.getGalleryFromSearchQuery(it)
                }
            }, {
                Log.d(this::class.simpleName, it.message.toString())
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
                    .show()
            })

        if (currentQuery != null && !currentQuery?.isEmpty()!!) {
            searchItem.expandActionView();
            searchView?.setQuery(currentQuery, false);
            searchView?.clearFocus();
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (searchView != null && searchView?.query != null) {
            currentQuery = searchView?.query.toString()
            outState.putString(SAVED_SEARCH_QUERY, currentQuery)
        }
    }

    override fun openImageDetailsScreen(galleryData: GalleryData, view: View) {
        GalleryDetailsActivity.newInstance(galleryData, view, this)
    }
}