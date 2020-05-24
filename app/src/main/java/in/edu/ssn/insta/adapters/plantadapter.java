package in.edu.ssn.insta.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.edu.ssn.insta.Activities.web;
import in.edu.ssn.insta.R;
import in.edu.ssn.insta.classes.plant_details;

public class plantadapter extends ArrayAdapter<plant_details> {
    private static final String TAG = "app_test";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postcolref = db.collection("posts");
    int likeflag = 0;
    String key_pass;

    public plantadapter(Context context, ArrayList<plant_details> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final plant_details object = (plant_details) getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.plant_list_items, parent, false);

        ImageView Img1 = convertView.findViewById(R.id.plant_1);
        ImageView Img2 = convertView.findViewById(R.id.plant_2);
        TextView text1 = convertView.findViewById(R.id.text_plant_1);
        TextView text2 = convertView.findViewById(R.id.text_plant_2);


        Picasso.get().load(object.getPic1()).into(Img1);
        Picasso.get().load(object.getPic2()).into(Img2);

        text1.setText(object.getProduct_name1());
        text2.setText(object.getProduct_name2());
        Log.i(TAG, "getView: "+object.getKey1());
        Log.i(TAG, "getView: "+object.getKey2());

        Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(object.getKey1(),parent);
            }
        });
        Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(object.getKey2(),parent);
            }
        });


        return convertView;
    }

    public void popup(final String key, final ViewGroup par) {

        final String[] year_list = {"WIKIHOW","YOUTUBE"};
        AlertDialog.Builder options = new AlertDialog.Builder(par.getRootView().getContext(),R.style.MyDialogTheme);
        options.setItems(year_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (year_list[which].equals("WIKIHOW")){

                    String uri = "https://www.wikihow.com/wikiHowTo?search="+key;
                    Intent intent = new Intent(getContext(), web.class);
                    intent.putExtra("url", uri);
                    intent.putExtra("title", "WIKIHOW");
                    par.getContext().startActivity(intent);


                }
                else if (year_list[which].equals("YOUTUBE")){
                    String uri = "https://www.youtube.com/results?search_query="+key;
                    Intent intent = new Intent(getContext(), web.class);
                    intent.putExtra("url", uri);
                    intent.putExtra("title", "YOUTUBE");
                    par.getContext().startActivity(intent);

                }
            }
        });
        options.show();
    }
}
