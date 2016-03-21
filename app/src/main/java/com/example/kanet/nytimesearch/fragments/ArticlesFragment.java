package com.example.kanet.nytimesearch.fragments;


import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.activities.SearchActivity;
import com.example.kanet.nytimesearch.activities.WebActivity;
import com.example.kanet.nytimesearch.adapters.SearchAdapter;
import com.example.kanet.nytimesearch.interfaces.EndlessRecyclerViewScrollListener;
import com.example.kanet.nytimesearch.interfaces.SpacesItemDecoration;
import com.example.kanet.nytimesearch.models.Article;
import com.example.kanet.nytimesearch.models.SettingSearch;
import com.example.kanet.nytimesearch.net.Internet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment{

    public static final int MAX_PAGES=100;
    SearchAdapter mSearchAdapter;
    ArrayList<Article> articles;
    Integer mPage;
    SettingSearch mSearchSetting;
    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_articles, container, false);
        setupView(rootView);
        onArticleDSearch(mSearchSetting, 0);
        return rootView;
    }

    public void setupView(View rootView){
        mSearchSetting=new SettingSearch();
        mSearchSetting.setQuery("");
        mSearchSetting= SearchActivity.mSettingSearch;

        RecyclerView rvSearch = (RecyclerView)rootView.findViewById(R.id.rvSearch);
        articles=new ArrayList<>();
        articles.clear();
        mSearchAdapter=new SearchAdapter(articles);

        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(mSearchAdapter);
        final StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(gridLayoutManager);
        rvSearch.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        rvSearch.addItemDecoration(decoration);

      rvSearch.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(getActivity(),"a",Toast.LENGTH_SHORT).show();
          }
      });
        rvSearch.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (page + 1 < MAX_PAGES)
                    onArticleDSearch(mSearchSetting, page + 1);
            }


        });
    }

    public void onArticleDSearch(SettingSearch settingSearch,Integer page){
     /*   if (Internet.isOnline()==false) {
            Toast.makeText(getActivity(),"Not connect internet",Toast.LENGTH_LONG).show();
            return;
        }*/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";
        params.put("api-key", "5218fcb01044215d7c234f16a1535797:6:74696115");
        params.put("sort",SettingSearch.getSortOrder(settingSearch.getSortOrder()));
        Date date=new Date(settingSearch.getBeginDate());
        String beginDate= DateFormat.format("yyyyMMdd", date).toString();
        params.put("begin_date",beginDate);
        if (settingSearch.getNewDeskValues().size()>0){
            String fq=mSearchSetting.getNewsDeskValueKey();
            params.put("fq","news_desk:("+fq+")");
        }

        params.put("page", page);
        if (settingSearch.getQuery()!="")
            params.put("q",settingSearch.getQuery());
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (articles == null)
                            articles = new ArrayList<>();
                        Log.d("DEBUG", response.toString());
                        JSONArray articleJSON = null;
                        try {
                            if (response.getJSONObject("response").has("docs")){
                                articleJSON = response.getJSONObject("response").getJSONArray("docs");
                                articles.addAll(Article.fromJSONArray(articleJSON));
                            }
                            Log.d("DEBUG", articles.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSearchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );
    }

  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                articles.clear();
                mSearchSetting.setQuery(query);
                mPage=0;
                onArticleDSearch(mSearchSetting,mPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }*/

}
