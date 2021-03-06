package com.example.photoalbum;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.util.ArrayList;
import java.util.Collection;

public class ContentActivity extends AppCompatActivity {
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
    String isTrash;
    int pos = 1;

    ActivityResultLauncher<Intent> editPhotoActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(this, "Edit photo successfully", Toast.LENGTH_SHORT).show();
                }
            });

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

        // Main content ??? L???y uri ???????c nh???n v??o
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        images = bundle.getStringArrayList("Images");
        ids = bundle.getStringArrayList("IDs");
        isFavorites = bundle.getStringArrayList("Favorites");
        isTrash = bundle.getString("Trash");
        pos = bundle.getInt("Position");

        ImageView pictureView;

        showLargeImage(pos);
        changeFavoriteButton(pos);
        if (isTrash.equals("1")) btnTrash.setImageResource(R.drawable.icon_restore);

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
        btnEdit.setOnClickListener(v -> {
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(ids.get(pos)));;
            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
            dsPhotoEditorIntent.setData(uri);
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Edited");
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#66d1ff"));
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, Color.parseColor("#FFFFFF"));
            int[] toolsToHide = {DsPhotoEditorActivity.TOOL_PIXELATE};
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);

            editPhotoActivityLauncher.launch(dsPhotoEditorIntent);
        });

        // Share Button
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            shareIntent.setType("image/*");
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(ids.get(pos)));;
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(shareIntent);
        });

        // More Button
        btnTrash.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Collection<Uri> collect = new ArrayList<Uri>();
                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(ids.get(pos)));

                collect.add(uri);
                PendingIntent editPendingIntent;
                if (isTrash.equals("0")) {
                    editPendingIntent = MediaStore.createTrashRequest(getContentResolver(), collect, true);
                } else {
                    editPendingIntent = MediaStore.createTrashRequest(getContentResolver(), collect, false);
                }

                try {
                    startIntentSenderForResult(editPendingIntent.getIntentSender(), 101, null, 0, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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