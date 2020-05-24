package in.edu.ssn.insta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.edu.ssn.insta.R;

public class commentsubmit extends AppCompatActivity {

    private static final String TAG = "app_test";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");
    EditText comment;
    Button submit;
    final static String sname = "name";
    final static String sdesc = "desc";
    final static String slike = "like";
    final static String sdocid = "docid";
    final static String scomments = "comment";
    final static String spost_img = "post_img";
    final static String suser_img = "user_img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentsubmit);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        comment = (EditText) findViewById(R.id.comment_submit_text);
        submit = (Button) findViewById(R.id.submit_comment);
        final String docid = getIntent().getExtras().getString("docid");
        Log.i(TAG, docid);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(commentsubmit.this, "Processing !!!", Toast.LENGTH_SHORT).show();
                postcolref.whereEqualTo(sdocid, docid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            final String documentid = document.getId();
                            ArrayList<String> com = (ArrayList<String>) document.get(scomments);
                            com.add(comment.getText().toString());
                            final Map<String, Object> comment_details = new HashMap<>();
                            comment_details.put(scomments, com);
                            postcolref.document(docid).set(comment_details, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(commentsubmit.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            });

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(commentsubmit.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
