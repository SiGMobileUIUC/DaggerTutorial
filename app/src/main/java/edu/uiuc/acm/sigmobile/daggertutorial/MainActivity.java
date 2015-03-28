package edu.uiuc.acm.sigmobile.daggertutorial;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.uiuc.acm.sigmobile.daggertutorial.model.Data;
import edu.uiuc.acm.sigmobile.daggertutorial.model.DataElement;
import edu.uiuc.acm.sigmobile.daggertutorial.model.DataRequest;
import edu.uiuc.acm.sigmobile.daggertutorial.model.Listing;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {

    @Inject
    RedditService redditService;

    // This is a Butterknife annotation.  This has nothing to do with Dagger.
    @InjectView(R.id.main_list)
    ListView list;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // These two lines will inject all dependencies into this activity
        DaggerApplication app = (DaggerApplication) this.getApplication();
        app.inject(this);

        // View initialization, just ignore this
        ButterKnife.inject(this);
        adapter = new MainAdapter(this);
        refreshData();
        list.setAdapter(adapter);
    }

    private void refreshData() {
        redditService.front()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                new Action1<DataRequest>() {
                    @Override
                    public void call(DataRequest request) {
                        updateListings(request.getData().getDataElements());
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
        );
    }

    private void updateListings(List<DataElement> elements) {
        adapter.setData(elements);
    }
}
