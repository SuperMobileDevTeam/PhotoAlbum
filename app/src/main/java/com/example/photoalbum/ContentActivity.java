package com.example.photoalbum;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentActivity extends Activity {
    // Button
    ImageButton btnBack, btnLike, btnEdit, btnShare, btnMore;

    // Grouping - Small icon
    ViewGroup scrollViewgroup;
    ImageView imageSelected;

    // Image uri content
    Integer[] contents = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Base Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding those buttons
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnLike = (ImageButton) findViewById(R.id.btnLike);
        btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnMore = (ImageButton) findViewById(R.id.btnMore);

        // Binding the ScrollView
        imageSelected = (ImageView) findViewById(R.id.imageSelected);
        scrollViewgroup = (ViewGroup) findViewById(R.id.viewgroup);

        //> Main content → Lấy uri được nhấn vào
        //if (largeImages.length > 0) imageSelected.setImageResource(largeImages[0]);

        //> Scroll content → Lấy mảng uri của cái đang sort
        // contents

        for (int i = 0; i < contents.length; i++) {
            // Infate
            final View singleFrame = getLayoutInflater().inflate(R.layout.content_scroller_bar, null);
            singleFrame.setId(i);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.icon);
            icon.setImageResource(contents[i]);

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
    }//onCreate

    //display a high-quality version of the image selected using thumbnails
    protected void showLargeImage(int frameId) {
        imageSelected.setImageResource(contents[frameId]);
    }
} // main