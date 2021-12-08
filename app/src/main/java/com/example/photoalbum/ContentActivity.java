package com.example.photoalbum;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;

public class ContentActivity extends Activity {
    // Button
    ImageButton btnBack, btnLike, btnEdit, btnShare, btnTrash;

    // Grouping - Small icon
    ViewGroup scrollViewgroup;
    ImageView imageSelected;
    HorizontalScrollView horizontalScrollView;

    // Image uri content
    ArrayList<String> images;
    ArrayList<String> ids;
    ArrayList<String> isFavorites;
    int pos = 1;

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
        btnTrash = (ImageButton) findViewById(R.id.btnTrash);

        // Binding the ScrollView
        imageSelected = (ImageView) findViewById(R.id.imageSelected);
        scrollViewgroup = (ViewGroup) findViewById(R.id.viewgroup);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScroll);

        // Main content → Lấy uri được nhấn vào
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        images = bundle.getStringArrayList("Images");
        ids = bundle.getStringArrayList("IDs");
        isFavorites = bundle.getStringArrayList("Favorites");
        pos = bundle.getInt("Position");

        ImageView pictureView;

        showLargeImage(pos);
        changeFavoriteButton(pos);

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
                    changeFavoriteButton(pos);
                    pos = singleFrame.getId();
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
                final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
                final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC" );

                Collection<Uri> collect = new ArrayList<Uri>();
                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(ids.get(pos)));

                collect.add(uri);
                PendingIntent editPendingIntent;
                if (isFavorites.get(pos).equals("1")) {
                    editPendingIntent = MediaStore.createFavoriteRequest(getContentResolver(), collect, false);
                    isFavorites.set(pos, "0");
                }
                else  {
                    editPendingIntent = MediaStore.createFavoriteRequest(getContentResolver(), collect, true);
                    isFavorites.set(pos, "1");
                }

                try {
                    startIntentSenderForResult(editPendingIntent.getIntentSender(), 101, null, 0, 0, 0);
                    changeFavoriteButton(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        btnTrash.setOnClickListener(new View.OnClickListener()
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

    protected void changeFavoriteButton(int frameId) {
        if (isFavorites.get(frameId).equals("1")) btnLike.setImageResource(R.drawable.icon_heart_red);
        else btnLike.setImageResource(R.drawable.icon_heart);
    }
} // main