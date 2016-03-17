package com.example.kanet.nytimesearch.models;

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

    public ArrayList<String> getThumbNails() {
        return thumbNails;
    }

    String webUrl;
    String headline;
    ArrayList<String> thumbNails;
    String new_desk;

    public Article(String webUrl, String headline, ArrayList<String> thumbNails) {
        this.webUrl = webUrl;
        this.headline = headline;
        this.thumbNails = thumbNails;
    }

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
                for (int i=0;i<multimediaJSONArray.length();i++){
                    JSONObject multimediaJson=multimediaJSONArray.getJSONObject(0);
                    String thumnNail="http://www.nytimes.com/"+multimediaJson.getString("url");
                    this.thumbNails.add(thumnNail);
                }
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
