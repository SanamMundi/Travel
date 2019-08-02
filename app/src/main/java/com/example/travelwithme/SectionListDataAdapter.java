package com.example.travelwithme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder>{

    private ArrayList<Destination> itemsList;

    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<Destination> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Float rate;

        Destination singleItem = itemsList.get(i);

       holder.tvTitle.setText(singleItem.getData().getName());


        if(singleItem.getData().getPhoto().equals("Not available"))
        {}
        else{

        Glide.with(mContext)
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+singleItem.getData().getPhoto()+"&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w")

                .into(holder.itemImage);}

        rate = Float.parseFloat(singleItem.getData().getRatings());
        holder.ratingBar.setRating(rate);

        if(singleItem.getData().getVicinity().equals("Not available"))
            holder.vicinity.setText(singleItem.getData().getVicinity());
        else
            holder.vicinity.setText("Near " +singleItem.getData().getVicinity());


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle,vicinity;

        protected ImageView itemImage;
        protected RatingBar ratingBar;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.ratingBar = (RatingBar)view.findViewById(R.id.rating_bar);
            this.vicinity = (TextView)view.findViewById(R.id.vicinity);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }
}
