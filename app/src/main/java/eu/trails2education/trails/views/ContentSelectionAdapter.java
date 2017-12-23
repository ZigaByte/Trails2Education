package eu.trails2education.trails.views;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eu.trails2education.trails.R;

import eu.trails2education.trails.database.Content;

/**
 * Created by Ziga on 02-Dec-17.
 */

public class ContentSelectionAdapter extends RecyclerView.Adapter<ContentSelectionAdapter.MyViewHolder> {

    private List<Content> contentList;

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

                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_title)).setText(contentList.get(id).gettitEN());
                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_content)).setText(contentList.get(id).getdesEN());
                }
            });
        }
    }

    public ContentSelectionAdapter(List<Content> contentList) {
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