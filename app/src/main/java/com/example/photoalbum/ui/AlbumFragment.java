package com.example.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photoalbum.MainActivity;
import com.example.photoalbum.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {
    ArrayList<String> byMonth;
    ArrayList<Album_Item> Albums = new ArrayList<>();

    private class Album_Item {
        private String path;
        private String name;

        public Album_Item(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.nav_album);
        View layout = inflater.inflate(R.layout.fragment_album, container, false);

        GridView gridView = (GridView) layout.findViewById(R.id.gridviewAlbum);
        gridView.setAdapter(new AlbumFragment.ImageAdapter(getActivity(), R.layout.album_item));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(null != Albums && !Albums.isEmpty()) {
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putString("Path", Albums.get(i).getPath());
                    myBundle.putString("Name", Albums.get(i).getName());
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                }
            }
        });

        return layout;
    }

    private class ImageAdapter extends BaseAdapter {
        private Activity context;
        int idLayout;

        private class ViewHolder{
            ImageView imgAlbum;
            TextView nameAlbum;
        }

        public ImageAdapter(Activity context, int idLayout) {
            this.context = context;
            this.idLayout = idLayout;
            getAllShownAlbumPath(context);
        }

        private void getAllShownAlbumPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int bucketIdIndex, bucketNameIndex, imageUriIndex;

            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.BUCKET_ID,
                    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.DATA
            };

            cursor = activity.getContentResolver().query(uri, projection, null, null, null);

            int count = cursor.getCount();
            ArrayList<Album_Item> listOfAllAlbums = new ArrayList<>(count);
            bucketIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID);
            bucketNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME);
            imageUriIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            cursor.moveToPosition(-1);
            List<String> list = new ArrayList<String>();
            while(cursor.moveToNext()){
                int index = list.indexOf(cursor.getString(bucketIdIndex));
                if (index == -1) {
                    Album_Item temp = new Album_Item(cursor.getString(imageUriIndex), cursor.getString(bucketNameIndex));
                    listOfAllAlbums.add(temp);
                    list.add(cursor.getString(bucketIdIndex));
                }
            }

            cursor.close();
            Albums = listOfAllAlbums;
        }

        @Override
        public int getCount() {
            return Albums.size();
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
        @NonNull
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            LayoutInflater inflater = context.getLayoutInflater();
            if(convertView == null){
                convertView = inflater.inflate(R.layout.album_item,null);
                holder = new ViewHolder();
                holder.imgAlbum=(ImageView)convertView.findViewById(R.id.imgAlbum);
                holder.nameAlbum=(TextView)convertView.findViewById(R.id.nameAlbum);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Glide.with(context).load(Albums.get(position).getPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop().into(holder.imgAlbum);

            holder.nameAlbum.setText(Albums.get(position).getName());

            return convertView;
        }
    }
}