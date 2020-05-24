package in.edu.ssn.insta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.ssn.insta.R;
import in.edu.ssn.insta.classes.post_details;

public class insta_home extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");

    RecyclerView posts;
    ImageView upload;
    ImageView userpic;
    int likeflag = 0;
    int like_f = 0;
    ArrayList<post_details> arr_list = new ArrayList<>();
    FirestoreRecyclerAdapter adapter;


    final static String sname = "name";
    final static String sdesc = "desc";
    final static String staken = "taken";
    final static String spost_img = "post_img";
    final static String suser_img = "user_img";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        posts = (RecyclerView) findViewById(R.id.post_listv);
        upload = (ImageView) findViewById(R.id.upload);
        userpic = (ImageView) findViewById(R.id.userpic) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        posts.setLayoutManager(layoutManager);
        posts.setHasFixedSize(true);
        
        setupFireStore();
        Picasso.get().load(SharedPref.getString(getApplicationContext(), "sp_image_url")).into(userpic);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(insta_home.this,new upload().getClass());
                    startActivity(intent);
                }catch(Exception e){
                    Log.i("app_test", e.toString());
                }

            }
        });

    }

    void setupFireStore(){

        FirestoreRecyclerOptions<post_details> options = new FirestoreRecyclerOptions.Builder<post_details>().setQuery(postcolref, new SnapshotParser<post_details>() {
            @NonNull
            @Override
            public post_details parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                final post_details post = new post_details();
                post.setUsername(snapshot.getString(sname));
                post.setDesc(snapshot.getString(sdesc));
                post.setDocument_id(snapshot.getString("docid"));
                post.setPost_img(snapshot.getString(spost_img));
                post.setUser_img(snapshot.getString(suser_img));
                return post;
            }
        }).build();

        adapter = new FirestoreRecyclerAdapter<post_details, FeedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FeedViewHolder holder, int i, @NonNull final post_details post_det) {
                holder.tv_name.setText(post_det.getUsername());
                holder.tv_name_below.setText(post_det.getUsername());
                holder.tv_desc.setText(post_det.getDesc());
                Picasso.get().load(post_det.getUser_img()).into(holder.user_img);
                Picasso.get().load(post_det.getPost_img()).into(holder.post_img);

                holder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (like_f == 0) {
                            holder.like.setImageResource(R.drawable.heart);
                            like_f = 1;
                        } else {
                            holder.like.setImageResource(R.drawable.like);
                            like_f = 0;
                        }
                        postcolref.document(post_det.getDocument_id()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Long likes = (Long) task.getResult().get("like");

                                if (likeflag == 0) {
                                    holder.like.setImageResource(R.drawable.heart);
                                    likes++;
                                    likeflag = 1;
                                } else {
                                    holder.like.setImageResource(R.drawable.like);
                                    likeflag = 0;
                                    if (likes > 0) {
                                        likes--;
                                    }
                                }
                                Map<String, Object> likes_details = new HashMap<>();
                                likes_details.put("like", likes);
                                postcolref.document(post_det.getDocument_id()).set(likes_details, SetOptions.merge());
                            }
                        });


                    }
                });

                holder.comment_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), view_comments.class);
                        intent.putExtra("docid",post_det.getDocument_id());
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                });

                holder.comment_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), commentsubmit.class);
                        intent.putExtra("docid",post_det.getDocument_id());
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                       getApplicationContext().startActivity(intent);
                    }
                });

            }

            @Override
            public FeedViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.insta_layout, group, false);
                return new FeedViewHolder(view);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

        posts.setAdapter(adapter);
    }

    /*********************************************************/

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_desc,tv_name_below,comment_view;
        public View view;
        public ImageView post_img,like,comment_sub;
        CircleImageView user_img;

        public FeedViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.username);
            tv_name_below = itemView.findViewById(R.id.username_below);
            tv_desc=itemView.findViewById(R.id.description);
            user_img=itemView.findViewById(R.id.user_img);
            post_img=itemView.findViewById(R.id.post_pic);
            like=itemView.findViewById(R.id.like);
            comment_sub=itemView.findViewById(R.id.comment_submit);
            comment_view=itemView.findViewById(R.id.comment_view);

        }
    }

    /*********************************************************/

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onStop() {
        super.onStop();
    }





}
