package com.me.arabin.synoped;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arabin on 2/23/17.
 */



//Unused Class
public class FillMems{

    private Activity activity;
    private ArrayList<Imgur>mems;
    private LinearLayout memsLayout;
    public FillMems(Activity act, ArrayList<Imgur>mems, LinearLayout layout){
        this.activity = act;
        this.mems = mems;
        this.memsLayout = layout;
        //memsLayout = (LinearLayout)activity.findViewById(R.id.horizontal_image_layout);


    }

    private View getImageView(final int position){
        ImageView iv = new ImageView(activity);
        //iv.setScaleType(Imag);
        iv.setAdjustViewBounds(true);
        StringBuilder sb = new StringBuilder("https://i.imgur.com/");
        sb.append(mems.get(position).getLink());
        sb.append(".jpg");
        Picasso.with(activity).load(sb.toString()).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,MemsImageView.class);
                intent.putExtra("imagePosition",position);
            }
        });

        return iv;
    }

}
