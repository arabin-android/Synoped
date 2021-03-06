package com.me.arabin.synoped;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by arabin on 2/25/17.
 */

public class MemsImageView extends Activity {


    private ArrayList<Imgur> myList;
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_meme_pager);


        int position = (int)getIntent().getSerializableExtra("position");
        myList = (ArrayList<Imgur>) getIntent().getSerializableExtra("virals");

        //Toast.makeText(getApplicationContext(),""+myList.size(),Toast.LENGTH_LONG).show();
        pager = (ViewPager)findViewById(R.id.detail_meme_pager);
        adapter = new MemePagerAdapter(MemsImageView.this,myList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

    }
}
