package edu.uiuc.acm.sigmobile.daggertutorial.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.uiuc.acm.sigmobile.daggertutorial.MainActivity;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

import edu.uiuc.acm.sigmobile.daggertutorial.RedditService;

/**
 * Created by Stephen on 3/24/2015.
 */
@Module(injects = MainActivity.class,
        includes = DataModule.class)
public class ApiModule {

    private static final String API_URL = "http://www.reddit.com";

    @Provides
    public Client provideClient() {
        return new OkClient();
    }

    @Provides
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(API_URL);
    }

    @Provides
    Converter provideConverter(Gson gson) {
        return new GsonConverter(gson);
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client, Converter converter) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setConverter(converter)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .build();
    }

    @Provides @Singleton
    RedditService provideRedditService(RestAdapter adapter) {
        return adapter.create(RedditService.class);
    }
}
