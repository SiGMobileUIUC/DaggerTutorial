package edu.uiuc.acm.sigmobile.daggertutorial.modules;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.uiuc.acm.sigmobile.daggertutorial.MainAdapter;

/**
 * Created by Stephen on 3/24/2015.
 */
@Module(// If this module requires a dependency from other modules, specify those other modules here
        includes = AppModule.class,

        // Any classes that this module injects have to be declared here
        injects = MainAdapter.class,

        // If an injectable class requires dependencies from other modules, set complete = false
        complete = false,

        // If an injectable class only require some of this module's dependencies, declare library = true
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
        return new Picasso.Builder(app)
                .loggingEnabled(true)
                .build();
    }
}
