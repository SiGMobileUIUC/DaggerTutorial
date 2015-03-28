package edu.uiuc.acm.sigmobile.daggertutorial.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stephen on 3/28/2015.
 */
public class DataRequest {

    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }
}
