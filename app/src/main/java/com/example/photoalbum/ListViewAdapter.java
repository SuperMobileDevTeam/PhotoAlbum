package com.example.photoalbum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context _context;
    private int _layout;
    private ArrayList<Image> _list;

    public ListViewAdapter(Context context, int layout, ArrayList<Image> list){
        _context = context;
        _layout = layout;
        _list = list;
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(_context, _layout, null);
        }
        //
        Image item = _list.get(i);
        ImageView img = (ImageView) view.findViewById(R.id.imgView);
        img.setImageResource(item.get_srcImg());
        TextView txt = (TextView) view.findViewById(R.id.txtNamePic);
        txt.setText(item.get_name());
        //
        return view;
    }
}
