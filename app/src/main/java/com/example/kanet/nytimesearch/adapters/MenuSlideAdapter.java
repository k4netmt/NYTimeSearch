package com.example.kanet.nytimesearch.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.models.SettingSearch;
import com.example.kanet.nytimesearch.viewholders.ViewHolderItemSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanet on 3/20/2016.
 */
public class MenuSlideAdapter extends ArrayAdapter<String>{
    ArrayList<String> arrayCheck;

    public ArrayList<String> getArrayCheck() {
        return arrayCheck;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItemSearch viewHolder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_menuslide, null);

            viewHolder=new ViewHolderItemSearch();
            viewHolder.cbItem=(CheckBox) convertView.findViewById(R.id.cbItem);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolderItemSearch)convertView.getTag();
        }



        final String settingSearch=getItem(position);
        viewHolder.cbItem.setText(settingSearch);
        viewHolder.cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    arrayCheck.add(buttonView.getText().toString());
                }else{
                    arrayCheck.remove(buttonView.getText().toString());
                }
            }
        });
        return convertView;
    }

    public MenuSlideAdapter(Context context, List<String> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
        arrayCheck=new ArrayList<>();
    }
}
