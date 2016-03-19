package com.example.kanet.nytimesearch.models;

import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Kanet on 3/16/2016.
 */
@Parcel
public class Article {

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public ArrayList<Photo> getThumbNails() {
        return thumbNails;
    }

    String webUrl;
    String headline;
    ArrayList<Photo> thumbNails;
    String new_desk;
    String date;

    public Article() {
    }

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl=jsonObject.has("web_url") ? jsonObject.getString("web_url") : "";
            this.headline=jsonObject.getJSONObject("headline").has("main") ? jsonObject.getJSONObject("headline").getString("main") : "";            ;
            this.new_desk=jsonObject.has("new_desk") ? jsonObject.getString("new_desk") : "";

            if (jsonObject.has("multimedia")){
                JSONArray multimediaJSONArray=jsonObject.getJSONArray("multimedia");
                this.thumbNails=new ArrayList<>();
                this.thumbNails=Photo.fromJSONArray(multimediaJSONArray);
            }else {
                this.thumbNails=new ArrayList<>();
            }
        }catch (JSONException e){
                e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array){
        ArrayList<Article> results=new ArrayList<>();
        for (int i=0;i<array.length();i++){
            try {
                results.add(new Article(array.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
