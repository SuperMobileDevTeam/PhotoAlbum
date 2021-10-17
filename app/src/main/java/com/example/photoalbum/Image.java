package com.example.photoalbum;

import androidx.annotation.NonNull;

public class Image{
    private String _name;
    private int _srcImg;

    public Image(){}
    public Image(String _name, int _srcImg) {
        this._name = _name;
        this._srcImg = _srcImg;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_srcImg() {
        return _srcImg;
    }

    public void set_srcImg(int _srcImg) {
        this._srcImg = _srcImg;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
