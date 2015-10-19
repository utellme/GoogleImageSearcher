package com.walmart.android.wk2.googleimagesearcher.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.walamrt.android.wk2.googleimagesearcher.R;
import com.walmart.android.wk2.googleimagesearcher.models.ImageResult;

public class ImageDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        getSupportActionBar().hide();

        ImageResult imageResult = (ImageResult)getIntent().getSerializableExtra("result");
        ImageView ivImageDetails = (ImageView)findViewById(R.id.ivImageDetail);

        Picasso.with(this).load(imageResult.getUrl()).into(ivImageDetails);

    }
}
