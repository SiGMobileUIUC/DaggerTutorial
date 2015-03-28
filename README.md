# Dependency Injection in Android (work in progress)
If you read enough about Android development, you'll inevitably run into a library named [Dagger][1], a dependency injector built for Android.  Everyone online seems to rave about it, but it can be daunting to learn how or why to use it.  The tutorial Square provides (on the Dagger webpage) is good, but the example they use is kind of abstract, and it assumes you have a decent amount of prior knowledge on the subject.  Here I'll start from the ground up and teach you everything you need to know to get up and running with Dagger.

## What does Dagger do?

Let's start with dependency injection: it's a term that's thrown around a lot within industry, but what does it really mean?  The best explanation I've found is [this one][2] by James Shore.  It's a quick read (1-3 mins), but if you don't want to go through it, the main takeaway from the article is the following quote:
> **Dependency injection means giving an object its instance variables. Really. That's it.**

It's true, and that's what Dagger does to help: it allows you to define all of your dependencies individually and organize them into a [directed acyclic graph][3] (which is where the name comes from, if you're wondering).  Any of the objects defined within your DAG can then be instantiated within your application through a simple `@Inject` annotation.  This is good for a number of reasons:
* You don't have to worry about how to order your dependencies, which can be a pain for large projects.
* It seriously decouples your code.  If you want to modify or swap out integral components in your app, you can now do it very efficiently.
  * This is great for unit testing--for example, if you want to mock your data instead of retrieve it from a server.
It should be noted that Dagger's benefits become exponentially more apparent the larger a project becomes.

## Code Example

Now that we know what Dagger does, let's take a look at some code.  I made a sample app with some practical applications of Dagger.  The app doesn't do anything fancy, it just takes the top posts from Reddit (using [Retrofit][4]) and displays the title and thumbnail of each post in a list.  The thumbnails are loaded with [Picasso][5] (Square libraries are awesome).  

Dagger is added to the project through the following lines in the the [Gradle file][6]:
```groovy
  compile 'com.squareup.dagger:dagger:1.2.2'
  provided 'com.squareup.dagger:dagger-compiler:1.2.2'
```

### Marking fields for injection

We use the `javax.inject.Inject` annotation to mark fields for injection.  Take a look at this snippet from [`MainActivity`][7]:
```java
  @Inject
  RedditService redditService;
```
In this case, the `@Inject` annotation tells Dagger that it should look in its graph of dependencies to obtain a `RedditService` instance.  Please note that `@Inject` does not perform any injection for you, it's simply used as a marker for Dagger to read.  We'll get to the actual injection in just a bit.

### Creating the graph of dependencies

In order for Dagger to inject something, we need to define a graph of dependencies.  There are two annotations we use to create this graph.
Whenever we want to satisfy a dependency, we use the `@Provides` annotation.  This annotaion is used on a method, where the return type is equal to the dependency we're satisfying.  Here's an example from [ApiModule][8]:
```java
  @Provides
  Endpoint provideEndpoint() {
      return Endpoints.newFixedEndpoint(API_URL);
  }
```
This method will return an instance of `Endpoint` to any specified injectable class.  But what if we have to satisfy a dependency that relies on a few other objects?  The solution is simple, `@Provides` methods have dependencies specified as their parameters.  Take a look at this snippet from [ApiModule][8]:
```java
  @Provides @Singleton
  RestAdapter provideRestAdapter(Endpoint endpoint, Client client, Converter converter) {
      return new RestAdapter.Builder()
              .setClient(client)
              .setConverter(converter)
              .setLogLevel(RestAdapter.LogLevel.FULL)
              .setEndpoint(endpoint)
              .build();
  }
```
In order to produce a `RestAdapter`, we need to define providers for `Endpoint`, `Client`, and `Converter`.  `@Singleton` is an optional annotation that tells Dagger to return the same instance across all injectable classes.  Through these `@Provides` methods, we are able to create the graph Dagger needs to find and satisfy its dependencies.

All methods annotated with `@Provides` have to be contained within a module.  A module is simply a class with a `@Module` annotation.  Modules serve a few purposes:
* They allow dependencies to be organized logically
* They contain features which help Dagger better understand the graph you're defining.
  * Compile-time validation of the graph is one of these features.

Let's take a look at a module, [DataModule][9]:
```java
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
```

### Performing the injection

Take a look at the [DaggerApplication][8] class.
```java
public class DaggerApplication extends Application {

    /**
     * This creates and stores the DAG defined by the modules it receives.  Whenever
     * dependencies are injected into an object, the ObjectGraph is traversed to find
     * all dependencies the object needs, throwing an error if it can't find all dependencies
     */
    ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules());
    }

    /**
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
```



[1]: http://square.github.io/dagger/
[2]: http://www.jamesshore.com/Blog/Dependency-Injection-Demystified.html
[3]: http://en.wikipedia.org/wiki/Directed_acyclic_graph
[4]: http://square.github.io/retrofit/
[5]: http://square.github.io/picasso/
[6]: http://github.com/SiGMobileUIUC/DaggerTutorial/blob/master/app/build.gradle
[7]: http://github.com/SiGMobileUIUC/DaggerTutorial/blob/master/app/src/main/java/edu/uiuc/acm/sigmobile/daggertutorial/MainActivity.java
[8]: 
[9]: 
[10]: http://github.com/SiGMobileUIUC/DaggerTutorial/blob/master/app/src/main/java/edu/uiuc/acm/sigmobile/daggertutorial/DaggerApplication.java
