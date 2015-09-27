package com.wuelto.prueba.com.pointofinterest.network;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.wuelto.prueba.com.pointofinterest.MyPlaces;
import com.wuelto.prueba.com.pointofinterest.R;
import com.wuelto.prueba.com.pointofinterest.activitys.MainActivity;
import com.wuelto.prueba.com.pointofinterest.helpers.DataBaseHelper;
import com.wuelto.prueba.com.pointofinterest.model.Places;
import com.wuelto.prueba.com.pointofinterest.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by grodriguez on 26/09/2015.
 * Web Service Reading
 */
public class FetchJsonAsyncTask extends AsyncTask<Void,Void,Void> {

    private ArrayList<Places> mPlacesArrayList;
    private MainActivity mContext;
    private ProgressDialog mProgressDialog;
    private DataBaseHelper mDataBaseHelper;

    public FetchJsonAsyncTask (MainActivity context) {

        this.mContext = context;
        this.mDataBaseHelper = new DataBaseHelper(context);

    }

    private void getDataFromJsoN(String jsonString) throws JSONException {

        // Get Values from Json Array from URL http://t21services.herokuapp.com/points
        JSONObject dataJson = new JSONObject(jsonString);
        JSONArray dataArray = dataJson.getJSONArray(Constants.LIST);
        mDataBaseHelper.delete();
        setmPlacesArrayList(new ArrayList<Places>());

        for (int arrayElements = 0; arrayElements < dataArray.length(); arrayElements++) {

            JSONObject place = dataArray.getJSONObject(arrayElements);

            // Create the Model Object
            Places placeObj = new Places(place.getString(Constants.TITLE),
                    place.getString(Constants.COORDINATES).split(",")[0],
                    place.getString(Constants.COORDINATES).split(",")[1],
                    place.getString(Constants.ID));
            // Complete the data with the new query
            placeObj.setDescription(getDescription(placeObj.getIndex()));
            // Adding to the AarrayList and the DataBase
            getmPlacesArrayList().add(placeObj);
            mDataBaseHelper.insert(placeObj.getName(), placeObj.getLat(),
                    placeObj.getLon(),placeObj.getDescription(),placeObj.getIndex());
        }
    }

    private String getDescription(String id) {

        String jsonString = readURL(MyPlaces.getInstance().getString(R.string.url_extended,id));
        try {
            return getExtraDataFromJsoN(jsonString);
        } catch (JSONException e) {
            Log.v("AsyncTaskException", e.getMessage());
            return null;
        }
    }

    private String getExtraDataFromJsoN(String jsonString) throws JSONException {

        // Get Values from Json Array from URL http://t21services.herokuapp.com/points/{id}
        JSONObject dataJson = new JSONObject(jsonString);
        return dataJson.getString(Constants.DESCRIPTION);
    }

    // Wait message while the data is downloading
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext,
                mContext.getString(R.string.downloading_title),
                mContext.getString(R.string.wait_message),
                true);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String jsonString = readURL(MyPlaces.getInstance().getString(R.string.url_base));
        // Reading from the JsonString
        try {
            getDataFromJsoN(jsonString);
        } catch (JSONException e) {
            Log.v("AsyncTaskException", e.getMessage());
        }

        return null;
    }

    private String readURL(String urlString) {

        // Variables for network connection
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = null;

        // Try network connection
        try {
            URL url = new URL(urlString);

            // Connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Reading the data
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonString = buffer.toString();

        } catch (IOException e) {
            Log.v("AsynkTaskException", e.getMessage());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.v("AsynkTaskException", e.getMessage());
                }
            }
        }

        return jsonString;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressDialog.dismiss();
        mContext.notifyFinishingAsyncTask(true);
    }

    public ArrayList<Places> getmPlacesArrayList() {
        return mPlacesArrayList;
    }

    public void setmPlacesArrayList(ArrayList<Places> mPlacesArrayList) {
        this.mPlacesArrayList = mPlacesArrayList;
    }
}
