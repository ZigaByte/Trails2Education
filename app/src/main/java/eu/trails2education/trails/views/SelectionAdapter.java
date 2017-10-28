package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import eu.trails2education.trails.R;

/**
 * Created by Žiga on 29. 09. 2017.
 */

public class SelectionAdapter extends BaseAdapter {

    private Activity activity;

    private static LayoutInflater inflater = null;

    public SelectionAdapter(Activity a) {
        activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        //return data.size();
        return 10;
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

        TextView title = (TextView)vi.findViewById(R.id.path_name); // title
        title.setText("Krško");

        /*TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image*/

        /*HashMap&lt;String, String&gt; song = new HashMap&lt;String, String&gt;();
        song = data.get(position);

        // Setting all values in listview
        artist.setText(song.get(CustomizedListView.KEY_ARTIST));
        duration.setText(song.get(CustomizedListView.KEY_DURATION));
        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);*/
        return vi;
    }
}