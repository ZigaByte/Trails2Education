package eu.trails2education.trails.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import eu.trails2education.trails.R;

import eu.trails2education.trails.database.Content;
import eu.trails2education.trails.database.Multimedia;
import eu.trails2education.trails.network.RequestManager;

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
                    Content content = contentList.get(id);
                    Activity activity = (Activity) view.getContext();

                    ((TextView) activity.findViewById(R.id.subject_title)).setText(content.gettitEN());
                    ((TextView) activity.findViewById(R.id.subject_content)).setText(Html.fromHtml(content.getdesEN()));
                    //((TextView) activity.findViewById(R.id.subject_title)).setText(content.gettitSL());
                    //((TextView) activity.findViewById(R.id.subject_content)).setText(Html.fromHtml(content.getdesSL()));

                    LayoutInflater inflater = activity.getLayoutInflater();
                    LinearLayout linearLayout = ((LinearLayout)activity.findViewById(R.id.content_container));
                    linearLayout.removeAllViewsInLayout();
                    for(Multimedia m : content.getMultimedia()){
                        NetworkImageView imageView = (NetworkImageView) inflater.inflate(R.layout.content_image, null);
                        String url = "http://" + m.geteURL();
                        ImageLoader loader = RequestManager.getInstance(activity.getApplicationContext()).getImageLoader();
                        loader.get(url, loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
                        imageView.setImageUrl(url, loader);

                        linearLayout.addView(imageView);
                    }
                }
            });
        }

        /**
         * Selects the appropriate subject icon
         * */
        public void changeIcon(final int id){
            int subjectType = (int)contentList.get(id).getctype();
            // Select the drawable that should be on the marker.
            int drawable = 0;
            switch(subjectType){
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
            }

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