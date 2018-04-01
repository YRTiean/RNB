package com.ydev.rnbapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
        //holder.tvAddress.setText(places.get(position).get("vicinity"));

        if (places.get(position).get("open_now") == "true") {
            //holder.open_now.setText("OPEN");
            //holder.open_now.setTextColor(Color.GREEN);
            holder.view.setBackgroundColor(Color.GREEN);
        }else{
            //holder.open_now.setText("CLOSED");
            //holder.open_now.setTextColor(Color.RED);
            holder.view.setBackgroundColor(Color.RED);
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
        //public final TextView tvAddress ;
        public final RatingBar ratingBar;
        public final ImageView imageView;
        public View view;
        //public final TextView open_now;
        //public final RelativeLayout relLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNames = itemView.findViewById(R.id.tv_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            //tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            imageView = itemView.findViewById(R.id.iv_res);
            //open_now = (TextView) itemView.findViewById(R.id.tv_open_now);
            //relLayout = (RelativeLayout) itemView.findViewById(R.id.relLayout);
            view = itemView.findViewById(R.id.view);

        }
    }
}
