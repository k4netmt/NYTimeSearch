package com.example.kanet.nytimesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Kanet on 3/19/2016.
 */
@Parcel
public class Photo {
    public static final String SUBTYPE_THUMBNAIL="thumbnail";
    public static final String SUBTYPE_WIDE="wide";
    public static final String SUBTYPE_XLARGE="xlarge";
    String url;
    int height;
    int width;
    String subtype;
    String type;
    public String getUrl() {
        return url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    public Photo(String url, int height, int width, String subtype, String type) {
        this.url = url;
        this.height = height;
        this.width = width;
        this.subtype = subtype;
        this.type = type;
    }

    public Photo() {
    }

    public Photo(JSONObject jsonObject) {
        try {

            this.url=jsonObject.has("url") ? "http://www.nytimes.com/" + jsonObject.getString("url") : "";
            this.height=jsonObject.has("height") ?  jsonObject.getInt("height") : 0;
            this.width=jsonObject.has("width") ?  jsonObject.getInt("width") : 0;
            this.subtype=jsonObject.has("subtype") ? jsonObject.getString("subtype") : "";
            this.type=jsonObject.has("type") ? jsonObject.getString("type") : "";

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public static ArrayList<Photo> fromJSONArray(JSONArray array){
        ArrayList<Photo> results=new ArrayList<>();
        for (int i=0;i<array.length();i++){
            try {
                results.add(new Photo(array.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

    public static Photo searchSubType(ArrayList<Photo> array,String subtype){
        if (array==null)
            return null;
        for (int i=0;i<array.size();i++){
            if (array.get(i).subtype.compareTo(subtype)==0){
                return array.get(i);
            }
        }
        return null;
    }
}
