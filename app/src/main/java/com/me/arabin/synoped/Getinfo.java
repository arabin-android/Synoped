package com.me.arabin.synoped;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arabin on 2/25/17.
 */

public class Getinfo extends AsyncTask<Void, Void, ArrayList<ArrayList>>{


    private ArrayList<Imgur> mems;
    private ArrayList<NewsItems>items;
    private ProgressDialog progressDialog;
    public FetchDataInterface callBack = null;
    private static final String urlnews = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=97ef12b9d8cd46b5b17902ca8b255d26";
    private static final String urlMems = "https://api.imgur.com/3/g/memes/top/0.json";
    private String TAG = Getinfo.class.getName();
    private Context context;
    private ArrayList<ArrayList>totalitem;

    public Getinfo(Context ctx){
        this.context = ctx;
        totalitem = new ArrayList();
        items = new ArrayList();
        mems = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected ArrayList doInBackground(Void... voids) {

        HttpClass http = new HttpClass();
        String jsnStr = http.makeServiceCall(urlnews);
        //String jsnStr2 = http.makeServiceCall(urlnews[1]);
        Log.e(TAG, "Response from..." + jsnStr);

        if (jsnStr != null) {
            //name = "news";
            try {
                JSONObject jsonObject = new JSONObject(jsnStr);

                JSONArray jsonArray = jsonObject.getJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    String image = item.getString("urlToImage");
                    String title = item.getString("title");
                    String description = item.getString("description");
                    items.add(new NewsItems(title, description, image));
                }
                totalitem.add(items);

            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error:" + e.getMessage());
            }

            jsnStr = http.makeServiceCall1(urlMems);
                if (jsnStr != null){
                    try {
                        //items.clear();
                        JSONObject jsonObject = new JSONObject(jsnStr);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        System.out.print("Length of Imgur : "+jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);

                            if (item.getBoolean("is_album")){
                                String link = item.getString("cover");
                                mems.add(new Imgur(link));
                            }

                        }
                        totalitem.add(mems);

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }
            }
        return totalitem;
    }
    @Override
    protected void onPostExecute(ArrayList<ArrayList>newsItemses) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
       callBack.fetch_data(newsItemses);
    }
}
