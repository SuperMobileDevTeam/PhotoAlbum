package com.example.photoalbum.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photoalbum.R;
import com.example.photoalbum.db.Photo;
import com.example.photoalbum.db.PhotoAlbumService;

import java.util.List;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.nav_settings);
        PhotoAlbumService.createInstance(requireActivity().getContentResolver());
        try {
            List<Photo> photos = PhotoAlbumService.getInstance().getPhotos(null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(String.valueOf(e), "something has gone wrong");
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}