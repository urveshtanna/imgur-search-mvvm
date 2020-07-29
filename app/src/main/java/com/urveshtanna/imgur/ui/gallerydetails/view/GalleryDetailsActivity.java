package com.urveshtanna.imgur.ui.gallerydetails.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

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

public class GalleryDetailsActivity extends AppCompatActivity {

    private ActivityGalleryDetailsBinding binding;
    private GalleryData galleryData;

    public static void newInstance(GalleryData galleryData, View view, Activity activity) {
        Intent intent = new Intent(activity, GalleryDetailsActivity.class);
        intent.putExtra(GalleryData.class.getSimpleName(), galleryData);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.gallery_preview_transition_name));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_details);
        galleryData = getIntent().getParcelableExtra(GalleryData.class.getSimpleName());
        getSupportActionBar().setHomeButtonEnabled(true);
        if (galleryData != null) {
            GalleryData.loadImage(binding.imgPreview, galleryData.getImageUrl());
            setTitle(galleryData.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}