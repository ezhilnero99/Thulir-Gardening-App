package in.edu.ssn.insta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import in.edu.ssn.insta.R;

public class login_activity extends AppCompatActivity {

    private static final String TAG = "app_test";
    Button submit_but;

    Intent intent;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 111;

    private View.OnClickListener submit_pressed = new View.OnClickListener() {
        public void onClick(View v) {
            mAuth.signOut();
            mGoogleSignInClient.signOut();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        submit_but = (Button) findViewById(R.id.submit_login);
        submit_but.setOnClickListener(submit_pressed);
        intent = new Intent(login_activity.this, home.class);
        initGoogleSignIn();

    }

    public void initGoogleSignIn() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(login_activity.this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount acct = task.getResult(ApiException.class);
                SharedPref.putString(getApplicationContext(), "sp_Username", acct.getDisplayName());
                SharedPref.putString(getApplicationContext(), "sp_image_url", acct.getPhotoUrl().toString());
                SharedPref.putString(getApplicationContext(), "sp_email", acct.getEmail());


                Log.i(TAG, "onActivityResult: "+SharedPref.getString(getApplicationContext(),"image_url"));


                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

                mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPref.putBoolean(getApplicationContext(), "sp_loggedin", true);
                            Intent intent = new Intent(getApplicationContext(),home.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "signInWithCredential:failure  "+task.getException().toString());
                        }
                    }
                });
                //else
                //Toast.makeText(this, "Please use SSN mail ID", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "error: " + e.toString());


            }
        }
    }

}
