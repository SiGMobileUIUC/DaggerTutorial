package edu.uiuc.acm.sigmobile.daggertutorial;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import edu.uiuc.acm.sigmobile.daggertutorial.modules.ApiModule;
import edu.uiuc.acm.sigmobile.daggertutorial.modules.AppModule;
import edu.uiuc.acm.sigmobile.daggertutorial.modules.DataModule;

/**
 * Created by Stephen on 3/24/2015.
 */
public class DaggerApplication extends Application {

    ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules());
    }

    public List<Object> getModules() {
        return Arrays.asList(new ApiModule(),
                             new AppModule(this),
                             new DataModule());
    }
}
