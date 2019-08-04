package com.example.travelwithme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

    private ArrayList<Reviews> reviews;
    private Context context;

    public MyListAdapter(Context context, ArrayList<Reviews> reviews)
    {
        this.context = context;
        this.reviews=reviews;
    }


    @Override
    public MyListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyListAdapter.MyViewHolder holder, int position) {

        Reviews rv = reviews.get(position);


            holder.username.setText(rv.getName());
            holder.reviews.setText(rv.getReview());



    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView username,reviews;

        public MyViewHolder(View view)
        {
            super(view);

            this.username = (TextView)view.findViewById(R.id.UserName);
            this.reviews = (TextView)view.findViewById(R.id.Review);

        }


    }

}
