package in.edu.ssn.insta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.ssn.insta.Activities.SharedPref;
import in.edu.ssn.insta.R;
import in.edu.ssn.insta.classes.plant_sugg_details;

public class plant_sugg_adapter extends ArrayAdapter<plant_sugg_details> {
    private static final String TAG = "app_test";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");
    int likeflag = 0;
    String key_pass;

    public plant_sugg_adapter(Context context, ArrayList<plant_sugg_details> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final plant_sugg_details object = (plant_sugg_details) getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.plant_suggestion, parent, false);

        CircleImageView Img1 = convertView.findViewById(R.id.plant_sug_img);
        TextView tex1 = convertView.findViewById(R.id.plant_sugg_text);
        Picasso.get().load(object.getImage()).into(Img1);
        tex1.setText(object.getName());

        return convertView;
    }


}
