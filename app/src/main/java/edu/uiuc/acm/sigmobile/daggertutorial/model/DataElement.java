package edu.uiuc.acm.sigmobile.daggertutorial.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stephen on 3/28/2015.
 */
public class DataElement {

    @SerializedName("data")
    Listing listing;

    public Listing getListing() {
        return listing;
    }
}
