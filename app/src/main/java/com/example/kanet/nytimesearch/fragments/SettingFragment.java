package com.example.kanet.nytimesearch.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.activities.SearchActivity;
import com.example.kanet.nytimesearch.models.SettingSearch;
import com.example.kanet.nytimesearch.models.TimeStamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends DialogFragment implements View.OnClickListener{

    SettingSearch mSettingSeacrh;
    public interface OnSaveListener{
        public void onSave(SettingSearch t);
    }

    private OnSaveListener onSaveListener;
    EditText etBeginDate;
    Spinner spSortOrder;
    private DialogInterface.OnCancelListener mOnCancelListener;
    Button btnSave;
    public SettingFragment() {
        // Required empty public constructor
    }

    public void setOnSaveListener(OnSaveListener onSaveListener){

        this.onSaveListener=onSaveListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container,
                false);
        getDialog().setTitle("Setting");
        // Get field from view
        etBeginDate=(EditText)rootView.findViewById(R.id.etBeginDate);
        spSortOrder=(Spinner)rootView.findViewById(R.id.spSortOder);
        btnSave=(Button)rootView.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
        etBeginDate.setOnClickListener(this);

        mSettingSeacrh= SearchActivity.mSettingSearch;
        Log.d(String.valueOf(mSettingSeacrh.getSortOrder()),"1");
        setSortOrder();
        setBegindate();
        return rootView;
    }


    public void setBegindate(){
        String myFormat = "dd/MM/yyyy"; // your format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        etBeginDate.setText(sdf.format(mSettingSeacrh.getBeginDate()));
    }
    public void setSortOrder() {
        ArrayList<String> sortOrders=new ArrayList<>();
        sortOrders.add("Oldest");
        sortOrders.add("Newest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, sortOrders);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(dataAdapter);
        spSortOrder.setSelection(mSettingSeacrh.getSortOrder());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etBeginDate:
            {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        etBeginDate.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                DatePickerDialog datePickerDialog;
                SimpleDateFormat sdf ;
                if (mSettingSeacrh.getBeginDate()!=0){
                    Date date=new Date(mSettingSeacrh.getBeginDate());
                    int year=TimeStamp.getYear(date);
                    int month=TimeStamp.getMonthOfYear(date);
                    int day=TimeStamp.getDayOfYear(date);
                    datePickerDialog=new DatePickerDialog(getActivity(),dateSetListener, year,
                            month,day);
                }else {
                    datePickerDialog=new DatePickerDialog(getActivity(),dateSetListener,myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                }
                datePickerDialog.show();
                break;
            }
            case R.id.btnSave:{
                if (onSaveListener!=null){
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date=new Date();
                    try
                    {
                        date=format.parse(etBeginDate.getText().toString());
                    }catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    int position=spSortOrder.getSelectedItemPosition();
                    mSettingSeacrh=new SettingSearch(mSettingSeacrh.getNewDeskValues(),spSortOrder.getSelectedItemPosition(),date.getTime(),
                            mSettingSeacrh.getQuery(),0);
                    onSaveListener.onSave(mSettingSeacrh);
                    getDialog().dismiss();
                }
                break;
            }
        }
    }

}
