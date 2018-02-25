package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import eu.trails2education.trails.R;
import eu.trails2education.trails.database.Pathway;
import eu.trails2education.trails.database.PathwaysDAO;
import eu.trails2education.trails.json.PathwayJSON;
import eu.trails2education.trails.network.RequestManager;
import eu.trails2education.trails.network.PathUtils;

/**
 * Created by Žiga on 29. 09. 2017.
 */

public class SelectionAdapter extends BaseAdapter {

    private final Activity activity;
    private final PathwaysDAO pathwaysDAO;

    private static LayoutInflater inflater = null;

    private ArrayList<Pathway> paths;

    public SelectionAdapter(final Activity a, final PathwaysDAO pathwaysDAO) {
        activity = a;
        this.pathwaysDAO = pathwaysDAO;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        readPathwaysFromDatabase();
        readPathwaysFromNetwork();
    }

    private void readPathwaysFromDatabase(){
        paths = pathwaysDAO.getAllPathways();

        // Sort and push Krsko first :D
        Collections.sort(paths, new Comparator<Pathway>() {
            @Override
            public int compare(Pathway pathway, Pathway t1) {
                return (pathway.getId() == 30) ? -1 : 1;
            }
        });
    }

    /**
     * Reads all the pathways from network, updates the database and updates the list from
     * the updated database.
     * */
    private void readPathwaysFromNetwork(){
        PathUtils.readPathListFromNetwork(activity, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                   // Load the pathways into the database
                ArrayList<Pathway> networkPaths = null;
                try {
                    networkPaths = PathwayJSON.createPathListFromJSON(activity, response);
                } catch (Exception e) {
                    Log.e("PATH LIST ERROR", "Loading the paths list failed");
                }
                    for(Pathway p : networkPaths){
                        pathwaysDAO.createPathway(p);
                    }
                    // Load paths from database again.
                    readPathwaysFromDatabase();

                notifyDataSetChanged();
            }
        });
    }

    public int getCount() {
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

        Pathway p = paths.get(position);
        titleText.setText(p.getNameEN());
        countryText.setText(p.getcouEN());
        areaText.setText(p.getar());
        vehicleText.setText(p.getvehEN());

        ratingText.setText("3.5");
        distanceText.setText(p.gettotM() + " m");
        durationText.setText(p.getestTime() + " min");
        caloriesText.setText(p.getestCal() + " cal");

        // Get the image from the network
        NetworkImageView imageView = (NetworkImageView) vi.findViewById(R.id.path_thumbnail);
        String url = "http://trails2education.eu/img/pathways/" + p.getId() + ".jpg";
        ImageLoader loader = RequestManager.getInstance(activity.getApplicationContext()).getImageLoader();
        loader.get(url, loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        imageView.setImageUrl(url, loader);

        return vi;
    }

    public Pathway getPath(int position){
        return paths.get(position);
    }

}