package edu.uiuc.acm.sigmobile.daggertutorial;

import java.util.List;

import edu.uiuc.acm.sigmobile.daggertutorial.model.DataRequest;
import retrofit.http.GET;
import rx.Observable;

import edu.uiuc.acm.sigmobile.daggertutorial.model.Listing;
/**
 * Created by Stephen on 3/25/2015.
 */
public interface RedditService {

    @GET("/.json")
    public Observable<DataRequest> front();
}
