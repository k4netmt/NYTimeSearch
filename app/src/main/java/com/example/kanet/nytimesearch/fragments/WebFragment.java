package com.example.kanet.nytimesearch.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.kanet.nytimesearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_web, container, false);
        WebView wvUrl = (WebView)rootView.findViewById(R.id.wvUrl);
        wvUrl.getSettings().setJavaScriptEnabled(true);
        wvUrl.loadUrl("http://www.tutorialspoint.com/android/android_webview_layout.htm");
        return rootView;
    }

}
