package com.example.photoalbum.db;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotoAlbumService {
    private static PhotoAlbumService instance;
    private static Uri uri;
    private static ArrayList<String> projection;
    private ContentResolver contentResolver;

    private PhotoAlbumService(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    public static void createInstance(ContentResolver contentResolver){
        if(instance == null) instance = new PhotoAlbumService(contentResolver);
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        projection = new ArrayList<>(Arrays.asList(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.WIDTH
        ));
    }

    public static PhotoAlbumService getInstance(){
        return instance;
    }

    public List<Photo> getPhotos(String order, String selection, String[] selectionArgs) throws Exception {
        List<Photo> result = new ArrayList<>();

        Cursor cursor = contentResolver.query(uri, getColumns(), selection, selectionArgs, order);
        int count = cursor.getCount();
        if (count == 0) {
            throw new Exception("No image found");
        }
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            Photo photo = new Photo(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            result.add(photo);
        }

        cursor.close();
        return result;
    }

    private String[] getColumns(){
        int len = projection.size();
        String[] result = new String[len];
        for(int i = 0; i < len; i++){
            result[i] = projection.get(i);
        }

        return result;
    }
}