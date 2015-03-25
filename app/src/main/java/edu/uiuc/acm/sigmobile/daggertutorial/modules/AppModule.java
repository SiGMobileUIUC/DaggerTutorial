package edu.uiuc.acm.sigmobile.daggertutorial.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Stephen on 3/24/2015.
 */
@Module(includes = DataModule.class)
public class AppModule {

    final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides @Singleton
    Application provideApp() {
        return app;
    }
}
