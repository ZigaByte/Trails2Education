package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.trails2education.trails.R;
import eu.trails2education.trails.network.RequestManager;
import eu.trails2education.trails.path.Content;
import eu.trails2education.trails.path.Path;
import eu.trails2education.trails.path.PathUtils;
import eu.trails2education.trails.path.Subject;

import static android.R.id.list;
import static java.security.AccessController.getContext;

/**
 * Created by Ziga on 02-Dec-17.
 */

public class ContentSelectionAdapter extends RecyclerView.Adapter<ContentSelectionAdapter.MyViewHolder> {

    private List<Subject> contentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setOnClickListener(final int id){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.e("Title" , contentList.get(id).title);
                    //Log.e("Descrpiption" , contentList.get(id).description);

                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_title)).setText(contentList.get(id).title);
                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_content)).setText(contentList.get(id).description);
                }
            });
        }
    }
    public ContentSelectionAdapter(Subject[] subjects) {
        contentList = new ArrayList<Subject>();
        for(Subject s : subjects){
            Log.e("SUBJECT IN ADAPTER", s + "");
            contentList.add(s);
        }
    }

    public ContentSelectionAdapter(List<Subject> contentList) {
        this.contentList = contentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_option, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setOnClickListener(position);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }
}