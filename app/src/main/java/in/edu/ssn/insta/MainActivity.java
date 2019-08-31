package in.edu.ssn.insta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");

    ListView posts;
    ImageView upload;
    ImageView userpic;
    ArrayList<post_details> arr_list = new ArrayList<>();
    feedadapter arr_adp;
    SwipeRefreshLayout swipeRefreshLayout;

    final static String sname = "name";
    final static String sdesc = "desc";
    final static String staken = "taken";
    final static String spost_img = "post_img";
    final static String suser_img = "user_img";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = (ListView) findViewById(R.id.post_listv);
        upload = (ImageView) findViewById(R.id.upload);
        userpic = (ImageView) findViewById(R.id.userpic) ;
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postcolref.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    final String documentid = document.getId();
                                    final String name = document.get(sname).toString();
                                    final String desc = document.get(sdesc).toString();
                                    final String post_img = document.get(spost_img).toString();
                                    final Boolean taken = (Boolean) document.get(staken);
                                    final String user_img = document.get(suser_img).toString();
                                    if(!user_img.isEmpty() && !post_img.isEmpty()  && !taken){
                                            arr_list.add(new post_details(name, desc,user_img,post_img,documentid));
                                            arr_adp = new feedadapter(getApplicationContext(), arr_list);
                                            posts.setAdapter(arr_adp);
                                    }

                                }

                            }
                        });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        Picasso.get().load(SharedPref.getString(getApplicationContext(), "sp_image_url")).into(userpic);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(MainActivity.this,new upload().getClass());
                    startActivity(intent);
                }catch(Exception e){
                    Log.i("app_test", e.toString());
                }

            }
        });

        postcolref.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            final String documentid = document.getId();
                            final String name = document.get(sname).toString();
                            final String desc = document.get(sdesc).toString();
                            final Boolean taken = (Boolean) document.get(staken);
                            final String post_img = document.get(spost_img).toString();
                            final String user_img = document.get(suser_img).toString();
                            if(!user_img.isEmpty() && !post_img.isEmpty() && taken){
                                arr_list.add(new post_details(name, desc,user_img,post_img,documentid));
                                arr_adp = new feedadapter(getApplicationContext(), arr_list);
                                posts.setAdapter(arr_adp);
                            }

                        }

                    }
                });

    }
}
