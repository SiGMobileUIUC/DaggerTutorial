# Dependency Injection in Android (work in progress)
If you read enough about Android development, you'll inevitably run into a library named [Dagger][1], a dependency injector built for Android.  Everyone online seems to rave about it, but it can be daunting to learn how or why to use it.  The tutorial Square provides (on the Dagger webpage) is good, but the example they use is kind of abstract, and it assumes you have a decent amount of prior knowledge on the subject.  Here I'll start from the ground up and teach you everything you need to know to get up and running with Dagger.

Let's start with dependency injection: it's a term that's thrown around a lot within industry, but what does it really mean?  The best explanation I've found is [this one][2] by James Shore.  It's a quick read (1-3 mins), but if you don't want to go through it, the main takeaway from the article is the following quote:
> Dependency injection means giving an object its instance variables. Really. That's it.

It's true, and that's what Dagger does to help: it allows you to define all of your dependencies individually and organize them into a [directed acyclic graph][3] (which is where the name comes from, if you're wondering).  Any of the objects defined within your DAG can then be instantiated within your application through a simple `@Inject` annotation.  This is good for a number of reasons:
-You don't have to worry about how to order your dependencies, which can be a pain for large projects.
-It seriously decouples your code.  If you want to modify or swap out integral components in your app, you can now do it very efficiently.  
..-This is great for unit testing--for example, if you want to mock your data instead of retrieve it from a server.

Now we know what Dagger does, let's take a look at some code.  I made a sample app with some practical applications of Dagger.  The app doesn't do anything fancy, it just takes the top posts from Reddit (using [Retrofit][4]) and displays the title and thumbnail of each post in a list.  The thumbnails are loaded with [Picasso][5] (Square libraries are awesome).  

Dagger is added to the project through the following lines in the the [Gradle file][6]:
```groovy
  compile 'com.squareup.dagger:dagger:1.2.2'
  provided 'com.squareup.dagger:dagger-compiler:1.2.2'
```
The first thing we have to understand is how we define a graph of dependencies within Dagger.
Take a look at the [DaggerApplication][7] class.
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
[7]: http://github.com/SiGMobileUIUC/DaggerTutorial/blob/master/app/src/main/java/edu/uiuc/acm/sigmobile/daggertutorial/DaggerApplication.java
