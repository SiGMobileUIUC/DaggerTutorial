package edu.uiuc.acm.sigmobile.daggertutorial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.uiuc.acm.sigmobile.daggertutorial.model.Listing;

/**
 * Created by Stephen on 3/25/2015.
 */
public class MainAdapter extends BaseAdapter {

    @Inject
    Picasso picasso;

    private LayoutInflater inflater;
    private List<Listing> listings;

    public MainAdapter(Activity activity) {
        inflater = LayoutInflater.from(activity);
        this.listings = new ArrayList<>();
        DaggerApplication app = (DaggerApplication) activity.getApplication();
        app.inject(this);
    }

    public void setData(List<Listing> listings) {
        this.listings = listings;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listings.size();
    }

    @Override
    public Object getItem(int position) {
        return listings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_main, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Listing listing = listings.get(position);
        holder.title.setText(listing.getTitle());
        picasso.load(listing.getThumbnailUrl())
               .resize(100, 100)
               .into(holder.thumbnail);

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.thumbnail)
        ImageView thumbnail;

        @InjectView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}
