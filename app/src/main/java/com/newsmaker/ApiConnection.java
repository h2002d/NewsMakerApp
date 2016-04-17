package com.newsmaker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User Pc on 4/17/2016.
 */
public class ApiConnection extends AsyncTask<Integer,Void,List<Post>> {
    public static List<Post> getPostFromApi() {
        try {
            URL url = new URL("http://www.newsmaker.somee.com/api/Post");

            JSONArray json = readJsonFromUrl(String.valueOf(url));
            List<Post> newpost = new ArrayList<Post>();

            if (json != null) {
                int len = json.length();
                for (int i=0;i<len;i++){
                    Post nPost = new Post();
                    nPost.setId(json.getJSONObject(i).getInt("Id"));
                    nPost.setContent(json.getJSONObject(i).getString("Content"));
                    nPost.setCreateDate(json.getJSONObject(i).getString("CreateDate"));
                    nPost.setTitle(json.getJSONObject(i).getString("Title"));
                    nPost.setType(json.getJSONObject(i).getInt("Type"));
                    nPost.setUserId(json.getJSONObject(i).getInt("UserId"));
                    newpost.add(nPost);
                }
            }

            return newpost;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    @Override
    protected List<Post> doInBackground(Integer... params) {

        return getPostFromApi();
    }


}
