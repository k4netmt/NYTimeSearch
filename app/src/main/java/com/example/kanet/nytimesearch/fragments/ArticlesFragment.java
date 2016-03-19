package com.example.kanet.nytimesearch.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.adapters.SearchAdapter;
import com.example.kanet.nytimesearch.interfaces.EndlessRecyclerViewScrollListener;
import com.example.kanet.nytimesearch.interfaces.SpacesItemDecoration;
import com.example.kanet.nytimesearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment implements View.OnTouchListener{

    SearchAdapter mSearchAdapter;
    ArrayList<Article> articles;
    String mQuery;
    Integer mPage;
    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_articles, container, false);
        setupView(rootView);
        Bundle bundle = getArguments();
        int position=0;
        if(bundle != null)
        {
            position = bundle.getInt("require");
        }
        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        onArticleDSearch("", 0);
        return rootView;
    }

    public void setupView(View rootView){
        RecyclerView rvSearch = (RecyclerView)rootView.findViewById(R.id.rvSearch);
        articles=new ArrayList<>();
        mSearchAdapter=new SearchAdapter(articles);

        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(mSearchAdapter);
        final StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(gridLayoutManager);
        rvSearch.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        rvSearch.addItemDecoration(decoration);
        rvSearch.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                onArticleDSearch(mQuery, page+1);
            }


        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String url = "http://www.vogella.com";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        v.getContext().startActivity(i);
        return false;
    }

    public void onArticleDSearch(String query,Integer page){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";
        params.put("api-key", "5218fcb01044215d7c234f16a1535797:6:74696115");
        params.put("fq","Sports");
        params.put("page",page);
        if (query!="")
            params.put("q",query);
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (articles==null)
                            articles=new ArrayList<>();
                        Log.d("DEBUG", response.toString());
                        JSONArray articleJSON = null;
                        try {
                            articleJSON = response.getJSONObject("response").getJSONArray("docs");
                            articles.addAll(Article.fromJSONArray(articleJSON));
                            Log.d("DEBUG", articles.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSearchAdapter.notifyItemInserted(0);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                articles.clear();
                mQuery=query;
                mPage=0;
                onArticleDSearch(mQuery,mPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
