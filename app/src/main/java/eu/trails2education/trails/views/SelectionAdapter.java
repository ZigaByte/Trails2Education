package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.util.ArrayList;

import eu.trails2education.trails.R;
import eu.trails2education.trails.network.RequestManager;
import eu.trails2education.trails.path.Path;
import eu.trails2education.trails.path.PathUtils;

import static android.R.attr.country;
import static android.R.attr.path;

/**
 * Created by Žiga on 29. 09. 2017.
 */

public class SelectionAdapter extends BaseAdapter {

    private Activity activity;

    private static LayoutInflater inflater = null;

    ArrayList<Path> paths;

    public SelectionAdapter(Activity a) {
        activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get all the paths
        //paths = PathUtils.createPathList(a.getApplicationContext(), R.raw.track_list);

        // Read data from network and notify the list when done
        paths = new ArrayList<Path>();
        PathUtils.readPathListFromNetwork(a, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    paths = PathUtils.createPathListFromJSON(response);
                }catch(Exception e){
                    Log.e("PATH LIST ERROR","Loading the paths list failed");
                }
                notifyDataSetChanged();
            }
        });
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

    /*
    * Sets the individual list item values
    * */
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
        distanceText.setText(p.totalMeters + " m");
        durationText.setText(p.estimatedTime + " min");
        caloriesText.setText(p.estimatedCalories + " cal");

        // Get the image from the network
        NetworkImageView imageView = (NetworkImageView) vi.findViewById(R.id.path_thumbnail);
        String url = "http://trails2education.eu/img/pathways/" + p.ID + ".jpg";
        ImageLoader loader = RequestManager.getInstance(activity.getApplicationContext()).getImageLoader();
        loader.get(url, loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        imageView.setImageUrl(url, loader);

        return vi;
    }
}