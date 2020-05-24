package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import in.edu.ssn.insta.R;
import in.edu.ssn.insta.adapters.commentsadapter;
import in.edu.ssn.insta.classes.comment_details;

public class view_comments extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");

    ListView comment_lv;
    ArrayList<comment_details> arr_list = new ArrayList<>();
    commentsadapter arr_adp;
    String document_id;

    final static String sname = "name";
    final static String sdesc = "desc";
    final static String spost_img = "post_img";
    final static String suser_img = "user_img";
    final static String slike = "like";
    final static String sdocid = "docid";
    final static String scomments = "comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        comment_lv = (ListView) findViewById(R.id.comment_LV);
        document_id = getIntent().getExtras().getString("docid");

        postcolref.whereEqualTo(sdocid, document_id).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ArrayList<String> com = (ArrayList<String>) document.get(scomments);
                            for (String value :com) {
                                arr_list.add(new comment_details(value));
                            }
                            arr_adp = new commentsadapter(getApplicationContext(), arr_list);
                            comment_lv.setAdapter(arr_adp);
                        }

                    }
                });
    }
}
