package com.me.arabin.synoped;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by arabin on 2/24/17.
 */

public class ViewPagerAdapter extends PagerAdapter {


    private Activity activity;
    private ArrayList<NewsItems>news;
    private LayoutInflater inflater;


    public ViewPagerAdapter(Activity act, ArrayList<NewsItems> news){

        this.activity = act;
        this.news = news;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //position = pos;

        View view = inflater.inflate(R.layout.details_item,container,false);

        TextView tv = (TextView)view.findViewById(R.id.news_desc_id);
        ImageView imageView = (ImageView)view.findViewById(R.id.details_news_image_id);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        tv.setText(news.get(position).getDescription());
        //String link = news.get(position).getUrl();
        Glide.with(activity).load(news.get(position).getUrl()).into(imageView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
