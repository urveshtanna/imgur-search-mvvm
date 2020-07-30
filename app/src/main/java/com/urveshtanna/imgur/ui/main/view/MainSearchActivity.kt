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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.androidnetworking.error.ANError
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.local.DataManager
import com.urveshtanna.imgur.data.local.db.AppDatabase
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
        binding.searchResultRecyclerview.adapter = adapter;
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
            // error.getErrorDetail() : connectionError, parseError, requestCancelledError or any other error
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }

    override fun loadNewData(galleryData: List<GalleryData>) {
        if (galleryData.size == 0) {
            Toast.makeText(this, getString(R.string.no_result_found), Toast.LENGTH_LONG).show()
        }
        adapter.addNewData(galleryData)
        adapter.notifyDataSetChanged()
    }

    override fun loadData(galleryData: List<GalleryData>) {
        adapter.addData(galleryData)
        adapter.notifyDataSetChanged()
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
                Log.d("SEARCH_QUERY", it)
                if (it != null && !it.trim().isEmpty()) {
                    mainSearchViewModel.getGalleryFromSearchQuery(it)
                }
            }, {
                Log.e("Error", it.message.toString())
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
        currentQuery = searchView?.query.toString()
        outState.putString(SAVED_SEARCH_QUERY, currentQuery)
    }

    override fun openImageDetailsScreen(galleryData: GalleryData, view: View) {
        GalleryDetailsActivity.newInstance(galleryData, view, this)
    }
}