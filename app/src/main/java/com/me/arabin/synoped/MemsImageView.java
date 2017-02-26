package com.me.arabin.synoped;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by arabin on 2/25/17.
 */

public class MemsImageView extends Activity {

    private final String IMGUR = "https://i.imgur.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mems_image);

        ImageView image = (ImageView) findViewById(R.id.mems_image_id);

        Bundle data =getIntent().getExtras();
        //ArrayList<String>list = data.getStringArrayList("imagePosition");
        StringBuilder builder = new StringBuilder(IMGUR);
        String url = data.getString("imagePosition");
        builder.append(url);
        builder.append(".png");
        Toast.makeText(MemsImageView.this,""+url, Toast.LENGTH_LONG).show();
        image.setAdjustViewBounds(true);
        //String i = mems.get(position);
        Glide.with(MemsImageView.this)
                .load(builder.toString())
                .into(image);
    }
}
