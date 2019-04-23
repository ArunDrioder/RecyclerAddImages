package com.example.recycleraddimages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> picturesList;

    public ImageRecyclerViewAdapter(ArrayList<String> picturesList, Context context)
    {
        this.mContext = context;
        this.picturesList = picturesList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ImageRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter, parent,false);

        return new ImageRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageRecyclerViewAdapter.MyViewHolder holder, int position)
    {
        try {

            File file = new File(picturesList.get(position));

            Glide.with(mContext).load(file).into(holder.picture);

        } catch (Exception e)
        {
            Log.i(ImageRecyclerViewAdapter.class.getSimpleName(),"Exception : "+e );
        }


    }

    @Override
    public int getItemCount() {
        return picturesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.picture);
        }
    }
}
