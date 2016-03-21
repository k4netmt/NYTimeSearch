package com.example.kanet.nytimesearch.models;


import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Kanet on 3/19/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class SettingSearch{
    private HashMap<String,String> hsNewsDeskValue;
    private ArrayList<String> arrKeyNewsDeskValue;

    private static final int SORTODRDER_OLDEST=0;
    private static final int SORTODRDER_NEWSEST=1;
    private ArrayList<String> newDeskValues;
    private int sortOrder;
    private long beginDate;
    private String query;
    private int page;

    public HashMap<String, String> getHsNewsDeskValue() {
        return hsNewsDeskValue;
    }

    public ArrayList<String> getArrKeyNewsDeskValue() {
        return arrKeyNewsDeskValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ArrayList<String> getNewDeskValues() {
        return newDeskValues;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public SettingSearch() {
        this.newDeskValues=new ArrayList<>();
        this.query="";
        this.sortOrder=SORTODRDER_OLDEST;
        this.beginDate=System.currentTimeMillis();
        hsNewsDeskValue=new HashMap<>();
        hsNewsDeskValue.put("Arts", "Arts");
        hsNewsDeskValue.put("Fashion & Style", "Fashion");
        hsNewsDeskValue.put("Sports", "Sports");
        arrKeyNewsDeskValue=new ArrayList<>();
        arrKeyNewsDeskValue.add("Arts");
        arrKeyNewsDeskValue.add("Fashion & Style");
        arrKeyNewsDeskValue.add("Sports");
    }

    @ParcelConstructor
    public SettingSearch(ArrayList<String> newDeskValues, int sortOrder, long beginDate, String query, int page) {
        this.newDeskValues = newDeskValues;
        this.sortOrder = sortOrder;
        this.beginDate = beginDate;
        this.query = query;
        this.page = page;
    }

    public static String getSortOrder(int sortOrderId){
        switch (sortOrderId){
            case SORTODRDER_OLDEST: return "oldest";
            case SORTODRDER_NEWSEST: return "newest";
        }
        return "oldest";
    }

    public String getNewsDeskValueKey(){
        String query="";
        for (int i=0;i<newDeskValues.size();i++)
        {
            String value=getValueDesk(newDeskValues.get(i));
            query=String.format("%s\"%s\"",query,value);
        }
        return query;
    }

    private String getValueDesk(String key){
        switch (key){
            case "Arts": return "Arts";
            case "Fashion & Style":return "Fashion";
            case "Sports": return "Sports";
        }
        return "";
    }
}
