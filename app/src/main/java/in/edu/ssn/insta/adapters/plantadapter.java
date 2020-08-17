package in.edu.ssn.insta.adapters;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.edu.ssn.insta.Activities.web;
import in.edu.ssn.insta.R;
import in.edu.ssn.insta.classes.plant_details;

public class plantadapter extends RecyclerView.Adapter<plantadapter.PlantViewHolder> {

    private static final String TAG = "plantadapter";
    ArrayList<plant_details> plantDetails;
    Context mContext;

    public plantadapter(ArrayList<plant_details> plantDetails, Context mContext) {
        this.plantDetails = plantDetails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.plant_list_items, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        final plant_details object = plantDetails.get(position);
        Picasso.get().load(object.getPic1()).into(holder.Img1);
        Picasso.get().load(object.getPic2()).into(holder.Img2);

        holder.text1.setText(object.getProduct_name1());
        holder.text2.setText(object.getProduct_name2());
        Log.i(TAG, "getView: " + object.getKey1());
        Log.i(TAG, "getView: " + object.getKey2());

        holder.Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(object.getKey1(), mContext);
            }
        });
        holder.Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(object.getKey2(), mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return plantDetails.size();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView Img1, Img2;
        TextView text1, text2;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            Img1 = itemView.findViewById(R.id.plant_1);
            Img2 = itemView.findViewById(R.id.plant_2);
            text1 = itemView.findViewById(R.id.text_plant_1);
            text2 = itemView.findViewById(R.id.text_plant_2);
        }
    }

    public void popup(final String key, final Context context) {

        final String[] year_list = {"WIKIHOW", "YOUTUBE"};
        AlertDialog.Builder options = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        options.setItems(year_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (year_list[which].equals("WIKIHOW")) {

                    String uri = "https://www.wikihow.com/wikiHowTo?search=" + key;
                    Intent intent = new Intent(mContext, web.class);
                    intent.putExtra("url", uri);
                    intent.putExtra("title", "WIKIHOW");
                    context.startActivity(intent);


                } else if (year_list[which].equals("YOUTUBE")) {
                    String uri = "https://www.youtube.com/results?search_query=" + key;
                    Intent intent = new Intent(mContext, web.class);
                    intent.putExtra("url", uri);
                    intent.putExtra("title", "YOUTUBE");
                    context.startActivity(intent);

                }
            }
        });
        options.show();
    }
}