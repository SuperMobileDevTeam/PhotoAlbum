package com.example.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photoalbum.ContentActivity;
import com.example.photoalbum.R;
import com.example.photoalbum.db.Photo;
import com.example.photoalbum.db.PhotoAlbumService;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    ArrayList<String> images;
    ArrayList<String> ids;
    ArrayList<String> isFavorites;
    String isTrash;
    String nameAlbum;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        path = bundle.getString("Path");
        nameAlbum = bundle.getString("Name");

        requireActivity().setTitle(nameAlbum);
        View layout = inflater.inflate(R.layout.fragment_favorite, container, false);

        GridView gridView = (GridView) layout.findViewById(R.id.gridviewGallery);
        gridView.setAdapter(new CategoryFragment.ImageAdapter(getActivity()));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(null != images && !images.isEmpty()) {
                    Intent myIntent = new Intent(getActivity(), ContentActivity.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putInt("Position", i);
                    myBundle.putStringArrayList("Images", images);
                    myBundle.putStringArrayList("IDs", ids);
                    myBundle.putStringArrayList("Favorites", isFavorites);
                    myBundle.putString("Trash", isTrash);
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

            List<Photo> photos = null;

            try{
                photos = PhotoAlbumService.getInstance()
                        .getPhotos(null, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?", new String[]{nameAlbum});
            }
            catch(Exception err){
                err.printStackTrace();
            }

            int count = 0;
            if (photos != null) count = photos.size();

            ArrayList<String> listOfAllImages = new ArrayList<>(count);
            ArrayList<String> listIds = new ArrayList<>(count);
            ArrayList<String> listOfFavorites = new ArrayList<>(count);

            if (photos != null) {
                for(Photo p : photos){
                    listOfAllImages.add(p.getAbsolutePath());
                    listIds.add(p.getId());
                    listOfFavorites.add(p.getIsFavorite());
                    isTrash = p.getIsTrash();
                }
            } else isTrash = "0";


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
                pictureView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                pictureView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                pictureView.setAdjustViewBounds(true);
            }
            else{
                pictureView = (ImageView) view;
            }

            Glide.with(context).load(images.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop().into(pictureView);

            return pictureView;
        }
    } // End ImageAdapter
}