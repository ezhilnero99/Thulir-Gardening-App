package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

import in.edu.ssn.insta.R;
import in.edu.ssn.insta.adapters.eventadapter;
import in.edu.ssn.insta.classes.event_item_details;

public class Events extends AppCompatActivity {

    ListView event_lv;
    ArrayList<event_item_details> arr_list = new ArrayList<>();
    eventadapter arr_adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        event_lv=(ListView)findViewById(R.id.events_LV);
        arr_list.add(new event_item_details("Agri 2k19","04-08-2019","Plant 200 saplings","\nThis Event is to motivate people to save the degrading environment\nWe together plant 200 samplings near padur\n\nLocation: HITS,padur."));
        arr_list.add(new event_item_details("Save Amazon","25-08-2019","Contribution for Amazons","\nContribution for Amazons save the degrading environment\nWe together support people to face this current diastrous situation\n\nLocation: SSN,Kelambakkam."));
        arr_list.add(new event_item_details("Go Organic","25-08-2019","Awarness on Organic farming","\nThis Event is to motivate people to save the degrading environment\nWe together plant 200 samplings near padur\n\nLocation: HITS,padur."));
        arr_adp = new eventadapter(getApplicationContext(), arr_list);
        event_lv.setAdapter(arr_adp);

    }
}
