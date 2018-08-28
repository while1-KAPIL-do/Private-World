package com.fundu.kapil.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    private Context mContext;
    private String tag;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public LinearLayout parent_layout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            parent_layout = (LinearLayout)view.findViewById(R.id.parent_layout);
        }
    }


    public MoviesAdapter(Context context ,String tag , List<Movie> moviesList) {

        this.moviesList = moviesList;
        this.mContext = context;
        this.tag = tag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,View_Activity.class);
                i.putExtra("tag",tag);
                i.putExtra("title",movie.getTitle());
                i.putExtra("data",movie.getGenre());
                i.putExtra("id",movie.getYear());

                mContext.startActivity(i);
               // Toast.makeText(mContext,"Title : "+movie.getTitle()+"\nData : "+movie.getGenre()+"\nid : "+movie.getYear(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}