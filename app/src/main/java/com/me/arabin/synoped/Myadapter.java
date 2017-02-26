package com.me.arabin.synoped;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arabin on 2/23/17.
 */

public class Myadapter extends BaseAdapter {

    private ArrayList<NewsItems> news;
    private Activity activity;
    private LayoutInflater inflater;

    public Myadapter(ArrayList<NewsItems>news, Activity act){
        this.news = news;
        this.activity = act;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null){
           view = inflater.inflate(R.layout.list_row,null);
        }

        TextView tv = (TextView) view.findViewById(R.id.news_title);

        tv.setText(news.get(position).getTitle());

        return view;
    }
}
