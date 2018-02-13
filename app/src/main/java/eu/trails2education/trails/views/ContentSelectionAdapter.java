package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eu.trails2education.trails.R;

import eu.trails2education.trails.database.Content;

/**
 * Created by Ziga on 02-Dec-17.
 */

public class ContentSelectionAdapter extends RecyclerView.Adapter<ContentSelectionAdapter.MyViewHolder> {

    private Context context;
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
                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_title)).setText(contentList.get(id).gettitEN());
                    ((TextView)((Activity)view.getContext()).findViewById(R.id.subject_content)).setText(contentList.get(id).getdesEN());
                }
            });
        }

        /**
         * Selects the appropriate subject icon
         * */
        public void changeIcon(final int id){
            int subjectType = (int)contentList.get(id).getctype();
            // Select the drawable that should be on the marker.
           // Log.e("Setting image", "position: " + id +  "    type: "+ subjectType);
            int drawable = 0;
            // This does not work currently. But it will when the JSON gets fixed
            /*switch(subjectType){
                case 1: drawable = R.drawable.subject_geography;break;
                case 2: drawable = R.drawable.subject_history;break;
                case 3: drawable = R.drawable.subject_art;break;
                case 4: drawable = R.drawable.subject_economics;break;
                case 5: drawable = R.drawable.subject_math;break;
                case 6: drawable = R.drawable.subject_literature;break;
                case 7: drawable = R.drawable.subject_physics;break;
                case 8: drawable = R.drawable.subject_chemistry;break;
                case 9: drawable = R.drawable.subject_biology;break;
                case 10: drawable = R.drawable.subject_geology;break;
                case 11: drawable = R.drawable.subject_architecture;break;

                default: drawable = R.drawable.subject_history;break;
            }*/
            String subject = contentList.get(id).getsubEN();
            Log.e("test", subject);
            if(subject.equals("Geography"))
                drawable = R.drawable.subject_geography;
            else if(subject.equals("History"))
                drawable = R.drawable.subject_history;
            else if(subject.equals("Arts"))
                drawable = R.drawable.subject_art;
            else if(subject.equals("Economics"))
                drawable = R.drawable.subject_economics;
            else if(subject.equals("Math"))
                drawable = R.drawable.subject_math;
            else if(subject.equals("Literature"))
                drawable = R.drawable.subject_literature;
            else if(subject.equals("Physics"))
                drawable = R.drawable.subject_physics;
            else if(subject.equals("Chemistry"))
                drawable = R.drawable.subject_chemistry;
            else if(subject.equals("Biology"))
                drawable = R.drawable.subject_biology;
            else if(subject.equals("Geology"))
                drawable = R.drawable.subject_geology;
            else if(subject.equals("Architecture"))
                drawable = R.drawable.subject_architecture;
            else
                drawable = R.drawable.subject_history;

            view.findViewById(R.id.path_thumbnail).setBackgroundResource(drawable);
        }
    }

    public ContentSelectionAdapter(Context context, List<Content> contentList) {
        this.contentList = contentList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_option, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setOnClickListener(position);

        holder.changeIcon(position);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }
}