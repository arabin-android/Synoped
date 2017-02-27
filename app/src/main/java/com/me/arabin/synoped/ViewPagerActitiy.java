package com.me.arabin.synoped;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;


/**
 * Created by arabin on 2/24/17.
 */
public class ViewPagerActitiy extends Activity {

    private  ArrayList<NewsItems> myList;
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_view);

        //Bundle data = new Bundle();
        int position = (int) getIntent().getSerializableExtra("position");

        //Toast.makeText(getApplicationContext(),"Position"+position,Toast.LENGTH_LONG).show();

        //new ViewPagerAdapter(activity,news,position);
        myList = (ArrayList<NewsItems>) getIntent().getSerializableExtra("newsList");
        pager = (ViewPager)findViewById(R.id.details_view_viewPager);
        adapter = new ViewPagerAdapter(ViewPagerActitiy.this,myList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

    }

}
