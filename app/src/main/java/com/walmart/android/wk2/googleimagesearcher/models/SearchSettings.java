package com.walmart.android.wk2.googleimagesearcher.models;

import java.io.Serializable;

/**
 * Created by dvalia on 10/17/15.
 */
public class SearchSettings implements Serializable{


    private static final long serialVersionUID = -9101974526852498045L;
    private String sImageSize;
    private String sImageType;
    private String sColorFilter;
    private String sSiteFilter;

    public SearchSettings() {

        //initialize to null as default values
        sImageSize = null;
        sImageType = null;
        sColorFilter = null;
        sSiteFilter = null;

    }

    public String getsSiteFilter() {
        return sSiteFilter;
    }

    public void setsSiteFilter(String sSiteFilter) {
        this.sSiteFilter = sSiteFilter;
    }

    public String getsColorFilter() {
        return sColorFilter;
    }

    public void setsColorFilter(String sColorFilter) {
        this.sColorFilter = sColorFilter;
    }

    public String getsImageType() {
        return sImageType;
    }

    public void setsImageType(String sImageType) {
        this.sImageType = sImageType;
    }

    public String getsImageSize() {
        return sImageSize;
    }

    public void setsImageSize(String sImageSize) {
        this.sImageSize = sImageSize;
    }




}
