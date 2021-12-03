package com.example.photoalbum.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoalbum.R;


public class AlbumFragment extends Fragment {
    ArrayList<String> images;
    ArrayList<String> nameAlbums = new ArrayList<>();

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
                if(null != images && !images.isEmpty()) {
                    Toast.makeText(getActivity(), "Position:" + i + " " + images.get(i),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return layout;
    }

    private class ImageAdapter extends BaseAdapter {
        private Activity context;
        int layout;

        public ImageAdapter(Activity context, int layout){
            this.context = context;
            this.layout = layout;
            images = getAllShownImagePath(context);
        }

        private ArrayList<String> getAllShownImagePath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data;

            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            };
            cursor = activity.getContentResolver().query(uri, projection, null, null, null);

            int count = cursor.getCount();
            ArrayList<String> listOfAllImages = new ArrayList<>(count);
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToPosition(-1);
            while(cursor.moveToNext()){
                listOfAllImages.add(cursor.getString(column_index_data));
            }

            cursor.close();
            return listOfAllImages;
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

            String data_item = images.get(i);
            ImageView img = (ImageView) pictureView.findViewById(R.id.imgAlbum);

            Glide.with(context).load(images.get(i))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop().into(img);

            TextView nameAlbum = (TextView) pictureView.findViewById(R.id.nameAlbum);
            nameAlbum.setText(nameAlbums.get(i));

            return pictureView;
        }
    }
}