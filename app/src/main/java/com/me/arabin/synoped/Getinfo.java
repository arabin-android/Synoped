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


    private ArrayList<Imgur> virals;
    private ArrayList<NewsItems>items;
    private ArrayList<Memes>memes;
    private ProgressDialog progressDialog;
    public FetchDataInterface callBack = null;
    private static final String urlnews = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=97ef12b9d8cd46b5b17902ca8b255d26";
    private static final String urlVirals = "https://api.imgur.com/3/gallery/hot/top/1.json?showViral=bool";
    private static final String urlMemes = "https://api.imgur.com/3/g/memes/viral/day/1.json?showMeme=bool";
    private String TAG = Getinfo.class.getName();
    private Context context;
    private ArrayList<ArrayList>totalitem;

    public Getinfo(Context ctx){
        this.context = ctx;
        totalitem = new ArrayList();
        items = new ArrayList();
        virals = new ArrayList<>();
        memes = new ArrayList<>();

    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
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

            jsnStr = http.makeServiceCall1(urlVirals);
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
                                String desc = item.getString("title");
                                virals.add(new Imgur(link,desc));
                            }

                        }
                        totalitem.add(virals);

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }
            jsnStr = http.makeServiceCall1(urlMemes);
            if (jsnStr!=null){
                try {
                    JSONObject jsonObject = new JSONObject(jsnStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        if (item.getBoolean("is_album")){
                            String id = item.getString("cover");
                            String title = item.getString("title");
                            memes.add(new Memes(id,title));
                        }

                    }
                    totalitem.add(memes);
                }
                catch (final  JSONException e){
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
