package com.example.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.photoalbum.ContentActivity;
import com.example.photoalbum.MyUtil;
import com.example.photoalbum.R;
import com.example.photoalbum.db.Photo;
import com.example.photoalbum.db.PhotoAlbumService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GalleryFragment extends Fragment {
    ArrayList<String> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.nav_gallery);
        View layout = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridView = (GridView) layout.findViewById(R.id.gridviewGallery);
        if(MyUtil.isInNightMode(requireActivity())) gridView.setBackgroundColor(Color.BLACK);
        else gridView.setBackgroundColor(Color.WHITE);

        gridView.setAdapter(new GalleryFragment.ImageAdapter(getActivity()));
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(null != images && !images.isEmpty()) {
                Intent myIntent = new Intent(getActivity(), ContentActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("Position", i);
                myBundle.putStringArrayList("Images", images);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });

        return layout;
    }

    private class ImageAdapter extends BaseAdapter {
        private Activity context;

        public ImageAdapter(Activity context){
            this.context = context;
            images = getAllShownImagePath(context);
        }

        private ArrayList<String> getAllShownImagePath(Activity activity) {
            List<Photo> photos = null;
            try {
                photos = PhotoAlbumService.getInstance().getPhotos("DATE_ADDED DESC", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<String> result = new ArrayList<>();
            assert photos != null;
            for (Photo p: photos) {
                result.add(p.getAbsolutePath());
            }

            return result;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView pictureView;
            if(view == null){
                pictureView = new ImageView(context);
                pictureView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else{
                pictureView = (ImageView) view;
            }

            Glide.with(context).load(images.get(i))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop().into(pictureView);

            return pictureView;
        }
    }
}
