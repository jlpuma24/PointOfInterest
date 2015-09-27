package com.wuelto.prueba.com.pointofinterest.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ConnectionManager.isConnected()) {
            mJsonFileAsyncTask = new FetchJsonAsyncTask(this);
            mJsonFileAsyncTask.execute();
        } else {
            mDataBaseHelper = new DataBaseHelper(this);
            notifyFinishingAsyncTask(false);
        }
    }

    public void notifyFinishingAsyncTask (boolean isConnected) {

        if (isConnected) {
            mPlacesArrayList = mJsonFileAsyncTask.getmPlacesArrayList();
        } else {
            mPlacesArrayList = mDataBaseHelper.select();
        }

        mListView = (ListView)findViewById(R.id.listview_places);
        mListView.setAdapter(new CustomAdapter(this,mPlacesArrayList,mListView));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Description",mPlacesArrayList.get(position).getDescription());
                startActivity(intent);
            }
        });

    }

}
