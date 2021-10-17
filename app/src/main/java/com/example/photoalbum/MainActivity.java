package com.example.photoalbum;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ListView myList;
    private ArrayList<Image> images = null;
    ListViewAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        findId();
        initImages();
        myAdapter = new ListViewAdapter(MainActivity.this, R.layout.list_item, images);
        myList.setAdapter(myAdapter);

    }
    //
    private void findId(){
        myList = (ListView) findViewById(R.id.myList);
    }
    private void initImages(){
        if(images == null){
            images = new ArrayList<>();
        }
        images.clear();
        images.add(new Image("Shin_3", R.drawable.shin_3));
        images.add(new Image("Shin_4", R.drawable.shin_4));
        images.add(new Image("Shin_5", R.drawable.shin_5));
        images.add(new Image("Shin_6", R.drawable.shin_6));
        images.add(new Image("Shin_3", R.drawable.shin_3));
        images.add(new Image("Shin_4", R.drawable.shin_4));
        images.add(new Image("Shin_5", R.drawable.shin_5));
        images.add(new Image("Shin_6", R.drawable.shin_6));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }
}