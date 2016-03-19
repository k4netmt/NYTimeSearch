package com.example.kanet.nytimesearch.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.adapters.SearchAdapter;
import com.example.kanet.nytimesearch.models.Article;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FashionStyleFragment extends Fragment {


    public FashionStyleFragment() {
        // Required empty public constructor
    }
    GridView gvData;
    SearchAdapter mSearchAdapter;
    ArrayList<Article> articles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fashion_style, container, false);
    }

}
