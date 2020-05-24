package in.edu.ssn.insta.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.edu.ssn.insta.R;

public class home extends AppCompatActivity{

    RelativeLayout shop;
    RelativeLayout insta;
    RelativeLayout plants;
    RelativeLayout gps;
    TextView events;
    ImageView detailsIV,logoutIV;

    Intent Insta_intent;
    Intent shop_intent;
    Intent gps_intent;
    Intent plant_intent;
    Intent event_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        shop = findViewById(R.id.shopRL);
        insta = findViewById(R.id.arenaRL);
        plants =  findViewById(R.id.howtoRL);
        gps =  findViewById(R.id.suggentionRL);
        detailsIV = findViewById(R.id.detailsIV);
        logoutIV = findViewById(R.id.logoutIV);

        shop.setOnClickListener(shop_redirect);
        insta.setOnClickListener(insta_redirect);
        gps.setOnClickListener(gps_redirect);
        plants.setOnClickListener(plant_redirect);


        gps_intent = new Intent(getApplicationContext(), map.class);
        Insta_intent = new Intent(getApplicationContext(), insta_home.class);
        shop_intent = new Intent(getApplicationContext(), shop.class);
        plant_intent = new Intent(getApplicationContext(), Plants.class);
        event_intent = new Intent(getApplicationContext(), Events.class);

        detailsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(home.this);
//            builder1.setTitle("DEVELOPERS");
//            builder1.setMessage(R.string.Developers);
//            builder1.setCancelable(true);
//
//            builder1.setPositiveButton(
//                    "Ok",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog alert11 = builder1.create();
//            alert11.show();
                startActivity(event_intent);

            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            SharedPref.putString(getApplicationContext(), "sp_Username", null);
            SharedPref.putString(getApplicationContext(), "sp_image_url", null);
            SharedPref.putString(getApplicationContext(), "sp_email", null);
            SharedPref.putBoolean(getApplicationContext(), "sp_loggedin", false);

            Intent startMain = new Intent(getApplicationContext(),login_options.class);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(startMain);
        finishAffinity();
        finish();
    }






    View.OnClickListener insta_redirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(Insta_intent);

        }
    };
    View.OnClickListener shop_redirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(shop_intent);

        }
    };
    View.OnClickListener gps_redirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(gps_intent);

        }
    };
    View.OnClickListener plant_redirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(plant_intent);

        }
    };


}
