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
 * Created by arabin on 2/27/17.
 */

public class MemePagerAdapter extends PagerAdapter {

    private final String IMGUR = "https://i.imgur.com/";
    private Activity activity;
    private ArrayList<Imgur>memeList;
    private LayoutInflater inflater;

    public MemePagerAdapter(Activity act, ArrayList<Imgur>memeList){
        this.activity = act;
        this.memeList = memeList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.mems_image,container,false);

        TextView tv = (TextView)view.findViewById(R.id.memes_text_id);
        ImageView imageView = (ImageView)view.findViewById(R.id.mems_image_id);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setAdjustViewBounds(true);
        tv.setText(memeList.get(position).getDescription());
        //String link = news.get(position).getUrl();
        StringBuilder builder = new StringBuilder(IMGUR);
        String url = memeList.get(position).getLink();
        builder.append(url);
        builder.append(".png");
        //Toast.makeText(MemsImageView.this,""+url, Toast.LENGTH_LONG).show();
        //String i = mems.get(position);
        Glide.with(activity)
                .load(builder.toString())
                .into(imageView);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return memeList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
