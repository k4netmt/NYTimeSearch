package com.example.kanet.nytimesearch.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kanet.nytimesearch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends DialogFragment implements View.OnClickListener{


    EditText etBeginDate;
    Spinner spSortOrder;
    Spinner spNewDeskValue;
    public SettingFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Setting");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container,
                false);
        // Get field from view
        etBeginDate=(EditText)rootView.findViewById(R.id.etBeginDate);
        spSortOrder=(Spinner)rootView.findViewById(R.id.spSortOder);
        spNewDeskValue=(Spinner)rootView.findViewById(R.id.spNewDeskValue);
        etBeginDate.setOnClickListener(this);
        setSortOrder();
        //setNewValueDesk();
        return rootView;
    }

    public void setSortOrder() {
        ArrayList<String> sortOrders=new ArrayList<>();
        sortOrders.add("Oldest");
        sortOrders.add("Newest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, sortOrders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etBeginDate:
            {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd MMMM yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        etBeginDate.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),date,Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_YEAR);
                datePickerDialog.show();
                break;
            }
        }
    }
}
