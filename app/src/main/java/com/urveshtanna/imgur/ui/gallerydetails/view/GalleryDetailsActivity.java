package com.urveshtanna.imgur.ui.gallerydetails.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.utils.Utils;
import com.urveshtanna.imgur.R;
import com.urveshtanna.imgur.data.model.GalleryData;
import com.urveshtanna.imgur.databinding.ActivityGalleryDetailsBinding;
import com.urveshtanna.imgur.ui.comment.view.CommentSectionFragment;
import com.urveshtanna.imgur.ui.gallerydetails.adapter.GalleryImageAdapter;

public class GalleryDetailsActivity extends AppCompatActivity {

    private ActivityGalleryDetailsBinding binding;
    private GalleryData galleryData;

    public static void newInstance(GalleryData galleryData, View view, Activity activity) {
        Intent intent = new Intent(activity, GalleryDetailsActivity.class);
        intent.putExtra(GalleryData.class.getSimpleName(), galleryData);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_details);
        galleryData = getIntent().getParcelableExtra(GalleryData.class.getSimpleName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (galleryData != null) {
            setupViewModel();
            setupObserver();
            setupUI();
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupObserver() {

    }

    private void setupUI() {
        setTitle(galleryData.getTitle());
        binding.tvDescription.setText(galleryData.getDescription());
        binding.imageRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.imageRecyclerview.setAdapter(new GalleryImageAdapter(galleryData.getImages()));
        setupCommentFragment();
    }

    private void setupViewModel() {

    }

    private void setupCommentFragment() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.comment_fragment, CommentSectionFragment.newInstance(getIntent().getExtras()));
            t.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}