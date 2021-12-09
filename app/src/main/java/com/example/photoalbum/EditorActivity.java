package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditorActivity extends AppCompatActivity {

    PhotoEditorView mPhotoEditorView = findViewById(R.id.photoEditorView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //
        mPhotoEditorView.getSource().setImageResource(R.drawable.river);
    }
}