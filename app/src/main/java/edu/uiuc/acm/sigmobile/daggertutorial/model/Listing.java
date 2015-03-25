package edu.uiuc.acm.sigmobile.daggertutorial.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stephen on 3/24/2015.
 */
public class Listing {

    @SerializedName("thumbnail")
    String thumbnailUrl;

    @SerializedName("title")
    String title;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

}
