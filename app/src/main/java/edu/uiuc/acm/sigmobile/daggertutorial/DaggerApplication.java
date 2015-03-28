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

    /**
     * This stores a DAG created according to the modules it receives.  Whenever
     * dependencies are injected into an object, the ObjectGraph is traversed to find and return
     * all dependencies the object needs, throwing an error if it can't find all dependencies
     */
    ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        // This takes an array containing all modules required for the dependency graph.
        objectGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A helper method that defines a list of modules
     * @return A list of all modules we have defined for the app
     */
    public List<Object> getModules() {
        return Arrays.asList(new ApiModule(),
                             new AppModule(this),
                             new DataModule());
    }

    /**
     * Inject all dependencies into a given object (can be any object, such as an Activity or Fragment).
     * Dependencies are marked within the object by the @Inject annotation.
     * @param object
     */
    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
