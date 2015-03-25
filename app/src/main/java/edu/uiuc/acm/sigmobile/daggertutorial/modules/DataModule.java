package edu.uiuc.acm.sigmobile.daggertutorial.modules;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Stephen on 3/24/2015.
 */
@Module(complete = false,
        library = true)
public class DataModule {

    @Provides @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
    }


    @Provides @Singleton
    public Picasso providePicasso(Application app) {
        // By default Picasso uses 1/7 of usable memory, we want a little more for loading highlights
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        return new Picasso.Builder(app)
                .loggingEnabled(true)
                .memoryCache(new LruCache((int) (maxMemory / 3)))
                .build();
    }
}
