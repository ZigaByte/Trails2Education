package eu.trails2education.trails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import eu.trails2education.trails.database.InterestPoint;
import eu.trails2education.trails.path.Subject;
import eu.trails2education.trails.views.ContentSelectionAdapter;

public class ContentActivity extends AppCompatActivity {

    RecyclerView contentList;
    ContentSelectionAdapter adapter;

    private InterestPoint interestPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Set the back button to exit the activity and return to the MapActivity
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Fill the title and description
        interestPoint = (InterestPoint) getIntent().getSerializableExtra("InterestPoint");

        Log.e("Will load ", "test");
        /*if(interestPoint.getSubjectCount() > 0){
            contentList = (RecyclerView)findViewById(R.id.recyclerView);
            contentList.setAdapter(new ContentSelectionAdapter(interestPoint.subjects)); // Pass the ids for the icons
            contentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Get first subject to populate views
            Subject first = interestPoint.subjects[0];
            Log.e("Loading", first.description);

            ((TextView)findViewById(R.id.subject_title)).setText(first.title);
            ((TextView)findViewById(R.id.subject_content)).setText(first.description);
        }else{
            // There is no content!!!!
        }*/
    }

}
