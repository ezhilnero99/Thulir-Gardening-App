package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

import in.edu.ssn.insta.R;
import in.edu.ssn.insta.adapters.plantadapter;
import in.edu.ssn.insta.classes.plant_details;

public class Plants extends AppCompatActivity {

    ArrayList<plant_details> arr_list = new ArrayList<>();
    plantadapter arr_adp;
    RecyclerView plant_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        plant_listView = findViewById(R.id.plant_listView);
        plant_listView.setLayoutManager(new LinearLayoutManager(this));
        arr_list.add(new plant_details("spinach" , "chilli","how+to+grow+spinach","how+to+grow+chilli ",R.drawable.spinach,R.drawable.chilli));
        arr_list.add(new plant_details("green beans" , "bitterguard","how+to+grow+green+beans","how+to+grow+bitter+guard ",R.drawable.greenbeans,R.drawable.bitterguard));
        arr_list.add(new plant_details("onion" , "pepper","how+to+grow+onion","how+to+grow+pepper",R.drawable.onion,R.drawable.pepper));
        arr_list.add(new plant_details("carrot" , "cabbage","how+to+grow+carrot","how+to+grow+cabbage",R.drawable.carrot,R.drawable.cabbage));
        arr_list.add(new plant_details("peas" , "brinjal","how+to+grow+peas","how+to+grow+brinjal",R.drawable.peas,R.drawable.brinjal));
        arr_list.add(new plant_details("okra" , "cucumber","how+to+grow+okra","how+to+grow+cucumber ",R.drawable.okra,R.drawable.cucumber));
        arr_list.add(new plant_details("lettuce" , "radish","how+to+grow+lettuce","how+to+grow+radish",R.drawable.lettuce,R.drawable.radish));
        arr_list.add(new plant_details("watermelon" , "corrianda","how+to+grow+watermelon","how+to+grow+corrianda ",R.drawable.watermelon,R.drawable.corrianda));
        arr_list.add(new plant_details("turnip" , "tomato","how+to+grow+turnip","how+to+grow+tomato ",R.drawable.turnip,R.drawable.tomato));
        arr_list.add(new plant_details("amaranthus" , "solanaeae","how+to+grow+amaranthus","how+to+grow+solanaeae",R.drawable.amaranthus,R.drawable.solanaeae));
        arr_adp = new plantadapter(arr_list,getApplicationContext());
        plant_listView.setAdapter(arr_adp);
    }
}
