package in.edu.ssn.insta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.edu.ssn.insta.R;

public class upload extends AppCompatActivity {

    private static final int IMAGE_CAPTURE = 2;
    private static final String TAG = "app_test";
    //Fire base storage instance to store Photo
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Firestore collection instance to store the datas including URL to the image
    CollectionReference postcolref = db.collection("posts");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //referance to ur firebase Storage the " URL here changes according to your account "
    StorageReference storageRef = storage.getReferenceFromUrl("gs://insta-fa46a.appspot.com/");

    Button uploadbtn;
    Button uploadimg_btn;
    EditText desc;
    ImageView img_preview;
    //to store the captured image
    Bitmap imageBitmap;

    final static String sname = "name";
    final static String sdesc = "desc";
    final static String slike = "like";
    final static String staken = "taken";
    final static String sdocid = "docid";
    final static String scomments = "comment";
    final static String spost_img = "post_img";
    final static String suser_img = "user_img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        uploadbtn = (Button) findViewById(R.id.upload_btn_up);
        uploadimg_btn = (Button) findViewById(R.id.upload_img_btn);
        desc = (EditText) findViewById(R.id.desc_up);
        img_preview = (ImageView) findViewById(R.id.image_preview);

        uploadbtn.setOnClickListener(uploading_data);
        uploadimg_btn.setOnClickListener((pic_image));


    }

    //Starting camera intent
    //needs CAMERA Permissions
    View.OnClickListener pic_image = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent Camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Camera_intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(Camera_intent, IMAGE_CAPTURE);
            }
        }
    };

    //Used to Save the image captured in a bitmap
    //and also to display the image in a image view as a preview.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            img_preview.setImageBitmap(imageBitmap);
        }
    }

    View.OnClickListener uploading_data = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            uploadbtn.setEnabled(false);
            ArrayList<String> comments = new ArrayList<>();


            //Map with details of the insta users
            final Map<String, Object> user_details = new HashMap<>();
            user_details.put(sname, SharedPref.getString(getApplicationContext(),"sp_Username"));
            user_details.put(sdesc, desc.getText().toString());
            user_details.put(slike,0);
            user_details.put(staken,false);
            user_details.put(scomments,comments);

            //Converting the bitmap format to jpeg
            //the image quality can be affected
            //you can change this part if needed
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if(imageBitmap!=null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            final byte[] data = baos.toByteArray();


            //Adding User data to the firestore database
            postcolref.add(user_details)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            if (documentReference != null) {
                                Toast.makeText(upload.this, "Processing !!!", Toast.LENGTH_LONG).show();
                                final String document_id = documentReference.getId();

                                //ACTUALL CODE FOR FIREBASE STORAGE IS FROM HERE
                                //******************************************************************************

                                StorageReference sto_ref = storageRef.child(document_id);
                                UploadTask uploadTask = sto_ref.putBytes(data);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //after successfully uploading the image to Storage the url is obtained and is updated in the DATABASE
                                                String download_url = uri.toString();
                                                Map<String, Object> user_img_details = new HashMap<>();
                                                user_img_details.put(sdocid,document_id);
                                                user_img_details.put(spost_img, download_url);
                                                user_img_details.put(suser_img, SharedPref.getString(getApplicationContext(),"sp_image_url"));
                                                postcolref.document(document_id).set(user_img_details, SetOptions.merge());
                                                Toast.makeText(upload.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        });
                                    }
                                });

                                //*****************************************************************************
                                //TILL HERE
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(upload.this, "Upload failure...Pls try Again", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
