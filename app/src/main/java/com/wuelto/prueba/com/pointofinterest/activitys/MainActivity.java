package com.wuelto.prueba.com.pointofinterest.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.wuelto.prueba.com.pointofinterest.R;
import com.wuelto.prueba.com.pointofinterest.adapters.CustomAdapter;
import com.wuelto.prueba.com.pointofinterest.helpers.ConnectionManager;
import com.wuelto.prueba.com.pointofinterest.helpers.DataBaseHelper;
import com.wuelto.prueba.com.pointofinterest.model.Places;
import com.wuelto.prueba.com.pointofinterest.network.FetchJsonAsyncTask;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private FetchJsonAsyncTask mJsonFileAsyncTask;
    private ListView mListView;
    private ArrayList<Places> mPlacesArrayList;
    private DataBaseHelper mDataBaseHelper;
    private Intent intent;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBaseHelper = new DataBaseHelper(this);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        try {
            if (intent.getStringExtra("search") != null) {
                searchText = intent.getStringExtra("search");
            }
            else {
                searchText = "";
            }

        } catch (NullPointerException e) {
            searchText = "";
            Log.v("SearchText", e.getMessage());
        }

        ImageButton imageButton = (ImageButton)findViewById(R.id.button_search);
        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = ((EditText)findViewById(R.id.search_text)).getText().toString();
                if (searchText.length() !=0) {
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.putExtra("search",searchText);
                    startActivity(intent);
                    finish();
                }
            }
        });

        if (ConnectionManager.isConnected() && mDataBaseHelper.select()!= null) {
            mJsonFileAsyncTask = new FetchJsonAsyncTask(this);
            mJsonFileAsyncTask.execute();
        } else {
            notifyFinishingAsyncTask(false);
        }

    }

    public void notifyFinishingAsyncTask (boolean isConnected) {

        if (isConnected) {
            mPlacesArrayList = mJsonFileAsyncTask.getmPlacesArrayList();
        } else {
            mPlacesArrayList = mDataBaseHelper.select();
        }
        ArrayList<Places> searchArrayList = new ArrayList<Places>();
        mListView = (ListView)findViewById(R.id.listview_places);

        if (searchText != "") {
            for (Places place : mPlacesArrayList) {
                if (place.getName().contains(searchText)) {
                    searchArrayList.add(place);
                }
            }
            mListView.setAdapter(new CustomAdapter(this,searchArrayList,mListView));
        } else {
            mListView.setAdapter(new CustomAdapter(this,mPlacesArrayList,mListView));
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("description",mPlacesArrayList.get(position).getDescription());
                intent.putExtra("latitude", mPlacesArrayList.get(position).getLat());
                intent.putExtra("longitude", mPlacesArrayList.get(position).getLon());
                intent.putExtra("title", mPlacesArrayList.get(position).getName());
                startActivity(intent);
            }
        });

    }

}
