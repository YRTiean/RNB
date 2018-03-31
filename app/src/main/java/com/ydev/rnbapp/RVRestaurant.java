package com.ydev.rnbapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YRT on 30/03/2018.
 */

public class RVRestaurant extends RecyclerView.Adapter<RVRestaurant.ViewHolder> {

    ArrayList<HashMap<String, String>> places;
    Context context;

    public RVRestaurant(ArrayList<HashMap<String, String>> names){
        this.places = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        int layout = R.layout.rv_restaurant_row;
        boolean shouldAttachToParentImmediately =false;
        ViewHolder holder = new ViewHolder(inflater.inflate(layout, parent, shouldAttachToParentImmediately));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvNames.setText(places.get(position).get("name"));
        holder.ratingBar.setRating(Float.parseFloat(places.get(position).get("rating")));
        holder.tvAddress.setText(places.get(position).get("vicinity"));

        if (places.get(position).get("open_now") == "true") {
            holder.open_now.setText("OPEN");
            holder.open_now.setTextColor(Color.GREEN);
        }else{
            holder.open_now.setText("CLOSED");
            holder.open_now.setTextColor(Color.RED);
        }

        if (places.get(position).containsKey("photos")){
            Picasso.get().load(places.get(position).get("photos")).fit().centerInside().into(holder.imageView);
        }

    }



    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView tvNames ;
        public final TextView tvAddress ;
        public final RatingBar ratingBar;
        public final ImageView imageView;
        public final TextView open_now;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNames = (TextView) itemView.findViewById(R.id.tv_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            imageView = (ImageView) itemView.findViewById(R.id.iv_res);
            open_now = (TextView) itemView.findViewById(R.id.tv_open_now);

        }
    }
}
