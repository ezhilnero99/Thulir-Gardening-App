package in.edu.ssn.insta.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.edu.ssn.insta.R;
import in.edu.ssn.insta.adapters.schemesadapter;
import in.edu.ssn.insta.classes.scheme_details;

public class Extensive_farming_tamil extends AppCompatActivity {

    ArrayList<scheme_details> arr_list = new ArrayList<>();
    schemesadapter arr_adp;
    ListView scheme_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extensive_farming);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        scheme_listView = (ListView) findViewById(R.id.Ext_farm_lv);


        arr_list.add(new scheme_details(R.string.sch_t1, R.string.sch_t1_d, "http://www.pmkisan.gov.in/"));
        arr_list.add(new scheme_details(R.string.sch_t2, R.string.sch_t2_d,"https://pmksy.gov.in/"));
        arr_list.add(new scheme_details(R.string.sch_t3, R.string.sch_t3_d,"https://pgsindia-ncof.gov.in/pkvy/index.aspx" ));
        arr_list.add(new scheme_details(R.string.sch_t4, R.string.sch_t4_d,"https://pib.gov.in/newsite/mbErel.aspx?relid=96201"));
        arr_adp = new schemesadapter(getApplicationContext(), arr_list);
        scheme_listView.setAdapter(arr_adp);
    }


}
