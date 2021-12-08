package com.example.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.photoalbum.R;
import com.example.photoalbum.db.Photo;
import com.example.photoalbum.db.PhotoAlbumService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    ArrayList<String> images;
    ArrayList<String> ids;
    ArrayList<String> isFavorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.nav_gallery);
        View layout = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridView = (GridView) layout.findViewById(R.id.gridviewGallery);
        gridView.setAdapter(new GalleryFragment.ImageAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(null != images && !images.isEmpty()) {
                    /*Toast.makeText(getActivity(), "Position:" + i + " " + images.get(i),
                            Toast.LENGTH_SHORT).show();*/
                    Intent myIntent = new Intent(getActivity(), ContentActivity.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putInt("Position", i);
                    myBundle.putStringArrayList("Images", images);
                    myBundle.putStringArrayList("IDs", ids);
                    myBundle.putStringArrayList("Favorites", isFavorites);
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                }
            }
        });

        return layout;
    }

    private class ImageAdapter extends BaseAdapter {
        private Activity context;

        public ImageAdapter(Activity context){
            this.context = context;
            getAllShownImagePath(context);
        }

        private void getAllShownImagePath(Activity activity) {
            // Uri uri;
            // Cursor cursor;
            // int column_index_data;
            // int column_index_id;
            // int column_index_fav;


            // uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            // String[] projection = {
            //         MediaStore.Images.Media.DATA,
            //         MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            //         MediaStore.Images.Media._ID,
            //         MediaStore.Images.Media.IS_FAVORITE
            // };
            // cursor = activity.getContentResolver().query(uri, projection, null, null, null);


            // int count = cursor.getCount();
            // ArrayList<String> listOfAllImages = new ArrayList<>(count);
            // ArrayList<String> listIds = new ArrayList<>(count);
            // ArrayList<String> listOfFavorites = new ArrayList<>(count);

            // column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // column_index_id = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            // column_index_fav = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.IS_FAVORITE);

            // cursor.moveToPosition(-1);
            // while(cursor.moveToNext()){

            //    listOfAllImages.add(cursor.getString(column_index_data));
            //     listIds.add(cursor.getString(column_index_id));
            //     listOfFavorites.add(cursor.getString(column_index_fav));
            // }

            List<Photo> photos = null;

            try{
                photos = PhotoAlbumService.getInstance().getPhotos(null, null, null);
            }
            catch(Exception err){
                err.printStackTrace();
            }

            int count = photos.size();
            ArrayList<String> listOfAllImages = new ArrayList<>(count);
            ArrayList<String> listIds = new ArrayList<>(count);
            ArrayList<String> listOfFavorites = new ArrayList<>(count);

            for(Photo p : photos){
                listOfAllImages.add(p.getAbsolutePath());
                listIds.add(p.getId());
                listOfFavorites.add(p.getIsFavorite());
            }

            images = listOfAllImages;
            ids = listIds;
            isFavorites = listOfFavorites;
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
                pictureView.setLayoutParams(new GridView.LayoutParams(270, 270));
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
