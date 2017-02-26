package com.me.arabin.synoped;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchDataInterface{

    // data field
    private LinearLayout memsLayout;
    private ListView lists;
    private ArrayList<Imgur>mems;
    private ArrayList<NewsItems>newsItemses;
    private ProgressDialog progressDialog;
    private ListAdapter adapter;
    private final String IMGUR = "https://i.imgur.com/";
    //private static final String urlMems = "http://startupian.net/meme.json";
    //private String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        mems = new ArrayList<>();
        memsLayout = (LinearLayout)findViewById(R.id.horizontal_image_layout);
    }

    @Override
    public void fetch_data(ArrayList<ArrayList> items) {

        newsItemses = (ArrayList<NewsItems>) items.get(0);
        mems = (ArrayList<Imgur>) items.get(1);

        set_mems(mems);
        set_news(newsItemses);
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

    private void set_mems(ArrayList<Imgur> mems) {
        for (int i = 0;i<mems.size();i++){
            memsLayout.addView(getImageView(i));
        }
    }

    private View getImageView(final int position){
        ImageView iv = new ImageView(MainActivity.this);
        //iv.setScaleType(Imag);
        iv.setAdjustViewBounds(true);
        StringBuilder sb = new StringBuilder(IMGUR);
        sb.append(mems.get(position).getLink());
        sb.append(".png");
        Glide.with(MainActivity.this).load(sb.toString()).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            String url = mems.get(position).getLink();
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MemsImageView.class);
                intent.putExtra("imagePosition",url);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,mems.get(position),Toast.LENGTH_LONG).show();
            }
        });
        return iv;
    }
    /*private class GetInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpClass httpObj = new HttpClass();
            //making a request to url and getting response

            String jsnStr = httpObj.makeServiceCall(urlnews);

            Log.e(TAG, "Response from..." + jsnStr);

            if (jsnStr != null) {
                try {

                    JSONObject jsonObject = new JSONObject(jsnStr);

                    JSONArray jsonArray = jsonObject.getJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String title = item.getString("title");
                        String description = item.getString("description");
                        String url = item.getString("urlToImage");
                        newsItemses.add(new NewsItems(title, description, url));
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }

            jsnStr = httpObj.makeServiceCall(urlMems);

            Log.e(TAG,"Response from..."+jsnStr);

            if (jsnStr != null){
                try {
                    JSONObject jsonObject = new JSONObject(jsnStr);

                    JSONArray jsonArray = jsonObject.getJSONArray("images");

                    for (int i = 0; i<jsonArray.length(); i++){
                        mems.add(jsonArray.getString(i));
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            adapter = new Myadapter(newsItemses, MainActivity.this);
            lists.setAdapter(adapter);

            //filling mems
            new FillMems(MainActivity.this, mems, memsLayout);

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

    }*/

}
