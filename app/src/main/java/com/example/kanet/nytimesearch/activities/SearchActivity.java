package com.example.kanet.nytimesearch.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;

import android.os.Parcel;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.adapters.MenuSlideAdapter;
import com.example.kanet.nytimesearch.fragments.ArticlesFragment;
import com.example.kanet.nytimesearch.fragments.SettingFragment;
import com.example.kanet.nytimesearch.models.SettingSearch;


import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity{
    @Bind(R.id.drawer_layout)DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    public static SettingSearch mSettingSearch;
    // slide menu items
    private String[] menuTitles;
    // drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    private ArrayList<String> navDrawerItems;
    @Bind(R.id.toolbar)Toolbar mToobar;
    private MenuSlideAdapter menuSlideAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSettingSearch=new SettingSearch();
        setSupportActionBar(mToobar);
        loadSildeMenu(savedInstanceState);
    }

    protected void loadSildeMenu(Bundle savedInstanceState){
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        menuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navDrawerItems = new ArrayList<>();
        // adding nav drawer items to array
        for (int i=0;i<mSettingSearch.getHsNewsDeskValue().size();i++){
            navDrawerItems.add(mSettingSearch.getArrKeyNewsDeskValue().get(i));
        }
        mDrawerList= (ListView)findViewById(R.id.lv_slidermenu);
        // setting the nav drawer list adapter
        menuSlideAdapter=new MenuSlideAdapter(this,navDrawerItems);
        mDrawerList.setAdapter(menuSlideAdapter);
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               // R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                mSettingSearch.getNewDeskValues().clear();
                mSettingSearch.getNewDeskValues().addAll(menuSlideAdapter.getArrayCheck());
                displayView();
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSettingSearch.setQuery(query);
                mSettingSearch.setPage(0);
                displayView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView() {
        // update the main content by replacing fragments
        Fragment fragment  = getFragmentManager().findFragmentByTag("articles");
        if (fragment != null) {
            getFragmentManager().beginTransaction().detach(fragment).commit();
            getFragmentManager().beginTransaction().attach(fragment).commit();
            //fragment.onResume();
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            fragment=new ArticlesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle=new Bundle();
            bundle.putParcelable("setting",Parcels.wrap(mSettingSearch));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container,fragment,"articles").commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(getApplicationContext(),"close",Toast.LENGTH_LONG);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final SettingFragment settingFragment=new SettingFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("settingitem", Parcels.wrap(mSettingSearch));
            settingFragment.setArguments(bundle);
            settingFragment.setOnSaveListener(new SettingFragment.OnSaveListener() {
                @Override
                public void onSave(SettingSearch settingSeacrh) {
                    mSettingSearch=settingSeacrh;
                    displayView();
                }
            });
            settingFragment.show(getSupportFragmentManager(),"datapicker");
            return true;
        }
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
