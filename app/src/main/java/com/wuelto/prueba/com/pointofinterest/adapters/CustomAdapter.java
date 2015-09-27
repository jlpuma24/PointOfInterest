package com.wuelto.prueba.com.pointofinterest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wuelto.prueba.com.pointofinterest.R;
import com.wuelto.prueba.com.pointofinterest.model.Places;

import java.util.ArrayList;

/**
 * Created by grodriguez on 26/09/2015.
 */
public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Places> mPlacesArrayList;
    private ListView mListView;
    private LayoutInflater mLayoutInflater;

    public CustomAdapter(Context context, ArrayList<Places> placesArrayList, ListView listView) {
        this.mContext = context;
        this.mPlacesArrayList = placesArrayList;
        this.mListView = listView;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPlacesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlacesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        View v = mListView.getChildAt(position-mListView.getFirstVisiblePosition());
        Places place = mPlacesArrayList.get(position);

        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.row_list, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewPlace = (TextView) v.findViewById(R.id.list_item_place_textview);
            viewHolder.textViewLocation = (TextView) v.findViewById(R.id.list_item_place_textview);
            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.textViewPlace.setText(place.getName());
        viewHolder.textViewLocation.setText(mContext.getString(R.string.location_format,place.getLat(),place.getLon()));
        return v;
    }

    static class ViewHolder {

        TextView textViewPlace;
        TextView textViewLocation;

    }
}
