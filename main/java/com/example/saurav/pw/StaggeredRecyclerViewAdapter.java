package com.example.saurav.pw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
public class StaggeredRecyclerViewAdapter extends  RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder>{

    private final  String TAG = "StaggeredRecyclerViewAdapter---->";

    private ArrayList<MyObject> mObj;

    private Context mContext;

    StaggeredRecyclerViewAdapter(Context mContext, ArrayList<MyObject> mObj) {

        this.mObj = mObj;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Log.d(TAG, "onBindViewHolder: ");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
            Glide.with(mContext)
                    .load(mObj.get(position).getImgUrl())
                    .apply(requestOptions) //
                    .into(holder.image);
        holder.name.setText(mObj.get(position).getImgTitle());
       holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+mObj.get(position).getImgTitle());
               // Toast.makeText(mContext, ""+mObj.get(position).getImgDest(), Toast.LENGTH_SHORT).show();
                // conditions

                switch (mObj.get(position).getData_Type()) {

                    case "web": {
                        Intent i = new Intent(mContext, WebActivity.class);
                        i.putExtra("url", mObj.get(position).getMainUrl());
                        mContext.startActivity(i);
                        break;
                    }

                    case "top": {
                        Intent i = new Intent(mContext, WebActivity.class);
                        i.putExtra("url", mObj.get(position).getMainUrl());
                        mContext.startActivity(i);
                        break;
                    }

                    case "update": {
                        Intent k = new Intent(Intent.ACTION_VIEW);
                        k.setData(Uri.parse("http://prrivateapps69.blogspot.com/2018/08/download-pw-my-private-world-1.html"));
                        mContext.startActivity(k);
                        break;
                    }

                    default:
                        Toast.makeText(mContext, "Data is not Available !", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mObj.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image_widge);
            this.name = itemView.findViewById(R.id.name_widge);
        }
    }

}
