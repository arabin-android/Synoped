package com.me.arabin.synoped;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by arabin on 2/27/17.
 */

public class ViralsImageView extends Activity {

    private ArrayList<Memes> myList;
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viral_view);


        int position = (int) getIntent().getSerializableExtra("position");
        myList = (ArrayList<Memes>) getIntent().getSerializableExtra("memes");

        //Toast.makeText(getApplicationContext(),""+myList.size(),Toast.LENGTH_LONG).show();
        pager = (ViewPager) findViewById(R.id.detail_memes_pager);
        adapter = new ViralPagerAdapter(ViralsImageView.this, myList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

    }
}