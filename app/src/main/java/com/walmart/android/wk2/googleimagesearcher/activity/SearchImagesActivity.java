package com.walmart.android.wk2.googleimagesearcher.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.walamrt.android.wk2.googleimagesearcher.R;
import com.walmart.android.wk2.googleimagesearcher.adapters.ImageResultAdapter;
import com.walmart.android.wk2.googleimagesearcher.com.walmart.android.wk2.googleimagesearcher.listners.EndlessScrollListener;
import com.walmart.android.wk2.googleimagesearcher.models.ImageResult;
import com.walmart.android.wk2.googleimagesearcher.models.SearchSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchImagesActivity extends AppCompatActivity {

    private EditText etText;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter imageResultAdapter;
    private SearchSettings searchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_images);

        setUpViews();

        imageResults = new ArrayList<ImageResult>();
        imageResultAdapter = new ImageResultAdapter(this, imageResults);
        searchSettings = new SearchSettings();

        gvResults.setAdapter(imageResultAdapter);


    }

    private void setUpViews(){

        etText = (EditText)findViewById(R.id.etText);
        gvResults = (GridView) findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //create intent
                Intent i = new Intent(SearchImagesActivity.this, ImageDetailsActivity.class);

                ImageResult result = imageResults.get(position);

                i.putExtra("result", result);

                startActivityForResult(i, 201);


            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //customLoadMoreDataFromApi(page);
                customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
       // imageResultAdapter.

        System.out.println("Scrolling....");

        getItemsfromGoogle(offset, false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // Toast.makeText(this, "RequestCode: " + requestCode + "resultCode: " + resultCode, Toast.LENGTH_SHORT).show();

        if(requestCode == 202 && resultCode==RESULT_OK){
             searchSettings = (SearchSettings)data.getSerializableExtra("settings");
        }

        System.out.println("Search Images Activity");
        System.out.println("Search Images Activity: " + searchSettings.getsColorFilter() + "," + searchSettings.getsImageSize() + "," + searchSettings.getsImageType() + "," + searchSettings.getsSiteFilter());
    }

    //on button click
    public void onImageSearch(View v){


       // Toast.makeText(this,text, Toast.LENGTH_SHORT).show();

        getItemsfromGoogle(0, true);


    }

    private void getItemsfromGoogle(int offset, final boolean bClick)
    {

        if(!isNetworkAvailable()) {

            etText.setText("!!! Search failed!. No Network Connection!!!");
            return;
        }

        String query = etText.getText().toString();
        String options = getSettingOptions();

        String searchURL = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";

        if(options.length() !=0)
            searchURL += options;

        if(bClick != true) {
            searchURL += "&start=";
            searchURL += offset;
        }

        System.out.println("Search URL: " + searchURL);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(searchURL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);

                JSONObject imageObject = null;

                try {
                    JSONArray array = response.getJSONObject("responseData").getJSONArray("results");

                    if (bClick == true)
                        imageResultAdapter.clear(); //clearing adapter than imageResults
                    //imageResults.clear();


                    //adding directly into adapter while will interally trigger data changes
                    imageResultAdapter.addAll(ImageResult.fromJsonArray(array));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.i("info", imageResults.toString());

            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private String getSettingOptions(){

        String imageSize, colorFilter, imageType, siteFilter = null;

//        imageSize = (searchSettings.getsImageSize()==null )? " ": searchSettings.getsImageSize();
//        colorFilter = (searchSettings.getsColorFilter()==null )? " ": searchSettings.getsColorFilter();
//        imageType = (searchSettings.getsImageType()==null )? " ": searchSettings.getsImageType();
//        siteFilter = (searchSettings.getsSiteFilter()==null )? " ": searchSettings.getsSiteFilter();
//
//        String options = String.format("&imgsz=%s&imgcolor=%s&imgtype=%s&as_sitesearch=%s",imageSize,colorFilter,imageType,siteFilter);

        String options = "";
        if(searchSettings.getsImageSize().length() !=0) {
            options += "&imgsz=";
            options += searchSettings.getsImageSize();
        }

        if(searchSettings.getsImageType().length() !=0) {
            options += "&imgtype=";
            options += searchSettings.getsImageType();
        }

        if(searchSettings.getsColorFilter().length()!=0) {
            options += "&imgcolor=";
            options += searchSettings.getsColorFilter();
        }

        if(searchSettings.getsSiteFilter().length()!=0) {
            options += "&as_sitesearch=";
            options += searchSettings.getsSiteFilter();
        }

        return options;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSettingsAction(MenuItem mi){

       // Toast.makeText(getApplicationContext(),"Text", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SearchOptionsActivity.class);

        intent.putExtra("settings", searchSettings);

        startActivityForResult(intent, 202);


    }
}
