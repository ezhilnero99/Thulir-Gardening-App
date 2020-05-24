package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.edu.ssn.insta.R;
import in.edu.ssn.insta.adapters.plant_sugg_adapter;
import in.edu.ssn.insta.classes.plant_sugg_details;

public class map extends AppCompatActivity {

    private static final String TAG = "app_test";
    WebView map;
    ListView plant_sugg_lv;
    ArrayList<plant_sugg_details> arr_list = new ArrayList<>();
    plant_sugg_adapter arr_adp;
    String longitude;
    String latitude;
    String loc;
    String month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        map = (WebView) findViewById(R.id.map_webview);
        plant_sugg_lv = (ListView) findViewById(R.id.plant_suggestion_lv);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            @SuppressLint("MissingPermission")
            Location location = (Location) lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            Log.i("app_test", "longitude: " + longitude);
            Log.i("app_test", "latitude: " + latitude);

            map.setWebViewClient(new WebViewClient());
            map.getSettings().setJavaScriptEnabled(true);
            map.loadUrl("https://www.google.com/maps/place/" + latitude + "+" + longitude);
            Log.i(TAG, "https://www.google.com/maps/place/" + latitude + "+" + longitude);

            if (Double.parseDouble(latitude) > 23.43674) {
                loc = "North";
            } else
                loc = "South";

            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            Date date = new Date();
            month = formatter.format(date);
            if (loc.equals("North")) {
                switch (month) {
                    case "01":
                    case "02":
                    case "03":
                    case "04":
                    case "05":
                    case "06":
                    case "07":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("guards" ,R.drawable.guard));
                        arr_list.add(new plant_sugg_details("cucumber" ,R.drawable.cucumber));
                        arr_list.add(new plant_sugg_details("okra" ,R.drawable.okra));
                        arr_list.add(new plant_sugg_details("tomato" ,R.drawable.tomato));
                        break;

                    case "08":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("carrot" ,R.drawable.carrot));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("radish" ,R.drawable.radish));
                        arr_list.add(new plant_sugg_details("tomato" ,R.drawable.tomato));
                        break;
                    case "09":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("cabbage" ,R.drawable.cabbage));
                        arr_list.add(new plant_sugg_details("carrot" ,R.drawable.carrot));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("radish" ,R.drawable.radish));
                        arr_list.add(new plant_sugg_details("tomato" ,R.drawable.tomato));
                        arr_list.add(new plant_sugg_details("lettuce" ,R.drawable.lettuce));
                        break;
                    case "10":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("brinjal" ,R.drawable.brinjal));
                        arr_list.add(new plant_sugg_details("cabbage" ,R.drawable.cabbage));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("lettuce" ,R.drawable.lettuce));
                        arr_list.add(new plant_sugg_details("radish" ,R.drawable.radish));
                        arr_list.add(new plant_sugg_details("spinach" ,R.drawable.spinach));
                        arr_list.add(new plant_sugg_details("turnip" ,R.drawable.turnip));
                        break;
                    case "11":
                    case "12":

                }
            } else if (loc.equals("South")) {
                switch (month) {
                    case "01":
                    case "02":
                    case "03":
                    case "04":
                    case "05":
                    case "06":
                    case "07":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("guards" ,R.drawable.guard));
                        arr_list.add(new plant_sugg_details("solanaeceae" ,R.drawable.solanaeae));
                        arr_list.add(new plant_sugg_details("carrot" ,R.drawable.carrot));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("radish" ,R.drawable.radish));
                        arr_list.add(new plant_sugg_details("tomato" ,R.drawable.tomato));
                        break;

                    case "08":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("carrot" ,R.drawable.carrot));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("green beans" ,R.drawable.greenbeans));
                        break;

                    case "09":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("cucumber" ,R.drawable.cucumber));
                        arr_list.add(new plant_sugg_details("onion" ,R.drawable.onion));
                        arr_list.add(new plant_sugg_details("spinach" ,R.drawable.spinach));
                        break;

                    case "10":
                        Log.i(TAG, "onCreate: "+month);
                        arr_list.add(new plant_sugg_details("brinjal" ,R.drawable.brinjal));
                        arr_list.add(new plant_sugg_details("cabbage" ,R.drawable.cabbage));
                        arr_list.add(new plant_sugg_details("cauliflower" ,R.drawable.cauliflower));
                        arr_list.add(new plant_sugg_details("lettuce" ,R.drawable.lettuce));
                        arr_list.add(new plant_sugg_details("radish" ,R.drawable.radish));
                        arr_list.add(new plant_sugg_details("spinach" ,R.drawable.spinach));
                        arr_list.add(new plant_sugg_details("turnip" ,R.drawable.turnip));
                        break;

                    case "11":
                    case "12":
                }
            }
            arr_adp = new plant_sugg_adapter(getApplicationContext(), arr_list);
            plant_sugg_lv.setAdapter(arr_adp);
        }
    }
}
