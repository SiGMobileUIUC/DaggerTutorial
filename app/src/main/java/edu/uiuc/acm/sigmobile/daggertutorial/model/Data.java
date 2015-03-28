package edu.uiuc.acm.sigmobile.daggertutorial.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Stephen on 3/24/2015.
 */
public class Data {

    @SerializedName("children")
    List<DataElement> elements;

    public List<DataElement> getDataElements() {
        return elements;
    }
}
