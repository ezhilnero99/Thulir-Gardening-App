package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import in.edu.ssn.insta.R;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(SharedPref.getBoolean(getApplicationContext(),"sp_loggedin") == true)
                {
                    Intent intent = new Intent(Splash_Screen.this,home.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Splash_Screen.this,login_options.class);
                    startActivity(intent);
                }

            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
