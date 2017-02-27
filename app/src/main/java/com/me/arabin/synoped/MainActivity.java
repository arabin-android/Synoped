package com.me.arabin.synoped;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchDataInterface{

    // data field
    private LinearLayout viralsLayout;
    private LinearLayout memesLayout;
    private ListView lists;
    private ArrayList<Imgur>virals;
    private ArrayList<Memes>memes;
    private ArrayList<NewsItems>newsItemses;
    private ListAdapter adapter;
    private final String IMGUR = "https://i.imgur.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(),"No Internet... Turn on Wifi or Mobile data.",Toast.LENGTH_LONG).show();
            return;
        }

        //initialize adapters and get values
        initcontents();

        //parse data using json
        Getinfo info = new Getinfo(MainActivity.this);
        info.callBack = this;
        info.execute();

    }

    private void initcontents() {
        lists = (ListView)findViewById(R.id.news_list_id);
        newsItemses = new ArrayList<>();
        virals = new ArrayList<>();
        memes = new ArrayList<>();
        viralsLayout = (LinearLayout)findViewById(R.id.horizontal_image_viral_layout);
        memesLayout = (LinearLayout)findViewById(R.id.horizontal_image_memes_layout);
    }

    @Override
    public void fetch_data(ArrayList<ArrayList> items) {

        newsItemses = (ArrayList<NewsItems>) items.get(0);
        virals = (ArrayList<Imgur>) items.get(1);
        memes = (ArrayList<Memes>)items.get(2);

        set_virals(virals);
        set_memes(memes);
        set_news(newsItemses);

    }

    private void set_memes(ArrayList<Memes> memes) {

        for (int i = 0;i<memes.size();i++){
            memesLayout.addView(getImageViewmemes(i));
        }

    }

    private void set_news(final ArrayList<NewsItems> newsItemses) {

        adapter = new Myadapter(newsItemses, MainActivity.this);
        lists.setAdapter(adapter);



        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"Size of news"+newsItemses.size(),Toast.LENGTH_LONG).show();

               Intent intent = new Intent(MainActivity.this,ViewPagerActitiy.class);
                intent.putExtra("newsList",newsItemses);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });

    }

    private void set_virals(ArrayList<Imgur> virals) {
        for (int i = 0;i<virals.size();i++){
            viralsLayout.addView(getImageView(i));
        }
    }

    private View getImageViewmemes(final int position){
        ImageView iv = new ImageView(MainActivity.this);
        //iv.setScaleType(Imag);
        iv.setAdjustViewBounds(true);
        StringBuilder sb = new StringBuilder(IMGUR);
        sb.append(memes.get(position).getId());
        sb.append(".png");
        Glide.with(MainActivity.this).load(sb.toString()).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            //String url = memes.get(position).getId();
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViralsImageView.class);
                intent.putExtra("memes",memes);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        return iv;
    }

    private View getImageView(final int position){
        ImageView iv = new ImageView(MainActivity.this);
        //iv.setScaleType(Imag);
        iv.setAdjustViewBounds(true);
        StringBuilder sb = new StringBuilder(IMGUR);
        sb.append(virals.get(position).getLink());
        sb.append(".png");
        Glide.with(MainActivity.this).load(sb.toString()).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            //String url = virals.get(position).getLink();
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MemsImageView.class);
                intent.putExtra("virals",virals);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        return iv;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
