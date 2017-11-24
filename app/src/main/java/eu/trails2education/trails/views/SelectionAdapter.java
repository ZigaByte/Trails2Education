package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import eu.trails2education.trails.R;
import eu.trails2education.trails.network.RequestManager;
import eu.trails2education.trails.path.Path;
import eu.trails2education.trails.path.PathUtils;

import static android.R.attr.country;

/**
 * Created by Žiga on 29. 09. 2017.
 */

public class SelectionAdapter extends BaseAdapter {

    private Activity activity;

    private static LayoutInflater inflater = null;
/*
    // Temporary data
    private String[] paths = {"Estonian test track", "Italian Test Track", "Portuguese test track", "Martinique test track"};
    private String[] country = {"Estonia", "Italy", "Portugal", "France"};
    private String[] area = {"Urban", "Urban", "Trail", "Trail"};
    private String[] vehicle = {"On Foot", "On Foot", "Bycicle", "Bycicle"};
    private int[] images = {R.drawable.estonia, R.drawable.italy, R.drawable.portugal, R.drawable.france};

    private String[] rating = {"4.7", "4.0", "3.14", "3.9"};
    private String[] distance = {"950 m", "750 m", "2000 m", "3500 m"};
    private String[] duration = {"50 min", "75 min", "45 min", "90 min"};
    private String[] calories = {"250 cal", "300  cal", "280  cal", "170  cal"};
*/
    ArrayList<Path> paths;

    public SelectionAdapter(Activity a) {
        activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get all the paths
        paths = new ArrayList<Path>();
        paths = PathUtils.createPathList(a.getApplicationContext(), R.raw.track_list);
    }

    public int getCount() {
        //return data.size();
        return paths.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.selection_option, null);

        TextView titleText = (TextView)vi.findViewById(R.id.path_name); // title
        TextView countryText = (TextView)vi.findViewById(R.id.path_country); // country
        TextView areaText = (TextView)vi.findViewById(R.id.path_area); // area
        TextView vehicleText = (TextView)vi.findViewById(R.id.path_vehicle);// vehicle

        TextView ratingText = (TextView)vi.findViewById(R.id.path_rating);// vehicle
        TextView distanceText = (TextView)vi.findViewById(R.id.path_distance);// vehicle
        TextView durationText = (TextView)vi.findViewById(R.id.path_length);// vehicle
        TextView caloriesText = (TextView)vi.findViewById(R.id.path_calories);// vehicle

        Path p = paths.get(position);
        titleText.setText(p.name);
        countryText.setText(p.region); // NOT COUNTRY TODO
        areaText.setText(p.area);
        vehicleText.setText(p.vehicle);

        ratingText.setText("5.0");
        distanceText.setText(p.totalMeters + " m"); // NOT COUNTRY TODO
        durationText.setText(p.estimatedTime + " min");
        caloriesText.setText(p.estimatedCalories + " cal");

        NetworkImageView imageView = (NetworkImageView) vi.findViewById(R.id.path_thumbnail);
        String url = "http://trails2education.eu/img/pathways/" + p.ID + ".jpg";
        ImageLoader loader = RequestManager.getInstance(activity.getApplicationContext()).getImageLoader();
        loader.get(url, loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        imageView.setImageUrl(url, loader);

        return vi;
    }
}