package com.example.photoalbum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ContentActivity extends Activity {
    // Button
    ImageButton btnBack, btnLike, btnEdit, btnShare, btnMore;

    // Grouping - Small icon
    ViewGroup scrollViewgroup;
    ImageView imageSelected;
    HorizontalScrollView horizontalScrollView;

    // Image uri content
    ArrayList<String> images;
    int Pre_position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Base Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Binding those buttons
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnLike = (ImageButton) findViewById(R.id.btnLike);
        btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnMore = (ImageButton) findViewById(R.id.btnMore);

        // Binding the ScrollView
        imageSelected = (ImageView) findViewById(R.id.imageSelected);
        scrollViewgroup = (ViewGroup) findViewById(R.id.viewgroup);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScroll);

        // Main content → Lấy uri được nhấn vào
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        images = bundle.getStringArrayList("Images");
        Pre_position = bundle.getInt("Position");

        ImageView pictureView;

        showLargeImage(Pre_position);

        for (int i = 0; i < images.size(); i++) {
            // Infate
            final View singleFrame = getLayoutInflater().inflate(R.layout.content_scroller_bar, null);
            singleFrame.setId(i);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.icon);

            Glide.with(this).load(images.get(i)).placeholder(R.drawable.ic_launcher_foreground).into(icon);

            // Add frame to the scrollView
            scrollViewgroup.addView(singleFrame);

            // Add its own click listener
            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLargeImage(singleFrame.getId());
                }
            });// listener
        }// for binding ScrollView

        // Back Button
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        // Like Button
        btnLike.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        // Edit Button
        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        // Share Button
        btnShare.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        // More Button
        btnMore.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
    }//onCreate

    //display a high-quality version of the image selected using thumbnails
    protected void showLargeImage(int frameId) {
        Glide.with(this)
                .load(images.get(frameId))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageSelected);
    }


} // main