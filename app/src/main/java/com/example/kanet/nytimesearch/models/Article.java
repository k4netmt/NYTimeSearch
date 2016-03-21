package com.example.kanet.nytimesearch.models;

import android.provider.ContactsContract;

import com.example.kanet.nytimesearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

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

    public int getColor_id() {
        return color_id;
    }

    public String getNew_desk() {
        return new_desk;
    }

    public String getDate() {
        return date;
    }

    String webUrl;
    String headline;
    ArrayList<Photo> thumbNails;
    String new_desk;
    String date;
    int color_id;
    String document_type;

    public Article() {
    }

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl=jsonObject.has("web_url") ? jsonObject.getString("web_url") : "";
            this.headline=jsonObject.getJSONObject("headline").has("main") ? jsonObject.getJSONObject("headline").getString("main") : "";            ;
            this.new_desk=jsonObject.has("news_desk") ? jsonObject.getString("news_desk") : "";
            this.color_id=this.getColorId(this.new_desk);
            this.date=jsonObject.has("pub_date") ? jsonObject.getString("pub_date") : "";
            if(this.date!="")
            {
                int index=this.date.indexOf("T");
                this.date=this.date.substring(0,index);
            }

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

    protected int getColorId(String new_desk){
        switch (new_desk){
            case "Arts":
                return R.color.arts_news;
            case "Sports":
                return R.color.sport_news;
            case "Fashion & Style":
                return R.color.fashionstyle_news;
        }
        return R.color.others_news;
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
