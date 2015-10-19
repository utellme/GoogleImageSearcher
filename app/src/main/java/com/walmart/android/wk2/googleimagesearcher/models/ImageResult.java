package com.walmart.android.wk2.googleimagesearcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dvalia on 10/15/15.
 */
public class ImageResult implements Serializable{

    private static final long serialVersionUID = 6247379114250114116L;
    private String imageWidth;
    private String imageHeight;
    private String url;
    private String tbUrl;
    private String title;

    public ImageResult(JSONObject jsonObject) {

        try {

            this.imageWidth = jsonObject.getString("width");
            this.imageHeight = jsonObject.getString("height");
            this.url = jsonObject.getString("url");
            this.tbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //take an array of JSON Images and create a list of ImageResults
    public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonArray){

        ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject imageJson = null;
            try {
                imageJson = jsonArray.getJSONObject(i);

                ImageResult imageResult = new ImageResult(imageJson);
                if (imageResult != null) {
                    imageResults.add(imageResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }


        }

        return imageResults;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getUrl() {
        return url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public String getTitle() {
        return title;
    }
}
