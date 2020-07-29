package com.urveshtanna.imgur.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.api.APIHelper
import com.urveshtanna.imgur.data.api.APIServiceImpl
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.databinding.ActivityMainSearchBinding
import com.urveshtanna.imgur.ui.base.ViewModelFactory
import com.urveshtanna.imgur.ui.gallerydetails.view.GalleryDetailsActivity
import com.urveshtanna.imgur.ui.main.adapter.MainSearchResultAdapter
import com.urveshtanna.imgur.ui.main.navigator.MainSearchNavigator
import com.urveshtanna.imgur.ui.main.viewmodel.MainSearchViewModel


class MainSearchActivity : AppCompatActivity(), MainSearchNavigator {

    lateinit var binding: ActivityMainSearchBinding
    lateinit var adapter: MainSearchResultAdapter
    lateinit var mainSearchViewModel: MainSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_search)
        setupUI()
        setupViewModel();
        setupObserver();
    }

    private fun setupObserver() {
        mainSearchViewModel.getGalleryFromSearchQuery("Android")
    }

    private fun setupViewModel() {
        mainSearchViewModel =
            ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIServiceImpl())))
                .get(MainSearchViewModel::class.java)
        mainSearchViewModel.setNavigator(this)
        mainSearchViewModel.mIsLoading.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if ((sender as ObservableBoolean).get()) {
                    binding.progressBar.visibility = View.VISIBLE;
                    binding.searchResultRecyclerview.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE;
                    binding.searchResultRecyclerview.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setupUI() {
        binding.searchResultRecyclerview.layoutManager = GridLayoutManager(this, 3)
        adapter = MainSearchResultAdapter(arrayListOf(), { galleryData, view ->
            openImageDetailsScreen(galleryData, view)
        })
        binding.searchResultRecyclerview.adapter = adapter;
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message.toString(), Toast.LENGTH_LONG).show()
    }

    override fun loadData(galleryData: List<GalleryData>) {
        adapter.addData(galleryData)
        adapter.notifyDataSetChanged()
    }

    override fun openImageDetailsScreen(galleryData: GalleryData, view: View) {
        GalleryDetailsActivity.newInstance(galleryData, view, this)
    }
}