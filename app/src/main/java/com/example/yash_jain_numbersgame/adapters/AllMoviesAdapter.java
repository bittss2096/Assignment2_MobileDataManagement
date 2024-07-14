package com.example.yash_jain_numbersgame.adapters;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.yash_jain_numbersgame.R;
import com.example.yash_jain_numbersgame.finalcode.Movies_Model;

import com.example.yash_jain_numbersgame.interfaces.OnMovieClicked;

import java.util.ArrayList;
import java.util.List;


public class AllMoviesAdapter extends RecyclerView.Adapter<AllMoviesAdapter.ViewHolder>
{

    public Context context;
    public List<Movies_Model> movie_list;
    public Uri uri;
    public String TAG = getClass().getName();
    public int duration;
    public double timeInMillisec;
    public int keytype;
    int width, height;
    public static ArrayList<String> add_compression_list;
    public MediaMetadataRetriever retriever;
    OnMovieClicked on_movie_clicked;


    public AllMoviesAdapter(Context context, List<Movies_Model> movie_list, OnMovieClicked on_movie_clicked)
    {
        this.context = context;
        this.movie_list = movie_list;
        this.on_movie_clicked=on_movie_clicked;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allmovies, parent, false);
        ViewHolder card = new ViewHolder(v);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {

//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width / 2, width / 2);
//        holder.fl_main.setLayoutParams(params);

        final Movies_Model movie_details = movie_list.get(position);

        int id=movie_details.id;
        String title=movie_details.title;
        String studio=movie_details.studio;
        String cric_rating=movie_details.criticsRating;
        String imagepath=movie_details.image;

        holder.tv_movie_title.setSelected(true);
        holder.tv_movie_title.setText(title);
        holder.tv_cric_rating.setText(cric_rating);
        holder.tv_studio_rating.setText(studio);
       Glide.with(context).load(Uri.parse(imagepath)).placeholder(R.drawable.clapperboard).into(holder.iv_thumbnail);

        holder.fl_category.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                on_movie_clicked.on_movie_clicked(title,id,movie_details);
            }
        });


        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_movie_clicked.on_edit_clicked(id,movie_details);
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_movie_clicked.on_delete_clicked(id,movie_details);
            }
        });

    }

    public void unselect_all() {
        if (add_compression_list != null) {
            add_compression_list.clear();
        }

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return movie_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_movie_title,tv_cric_rating,tv_studio_rating;
       ImageView iv_thumbnail,iv_edit,iv_delete;

       LinearLayout fl_category;

        public ViewHolder(View itemView) {
            super(itemView);

            fl_category = itemView.findViewById(R.id.fl_category);
            iv_thumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tv_movie_title = itemView.findViewById(R.id.tv_movie_title);
            tv_cric_rating = itemView.findViewById(R.id.tv_cric_rating);
            tv_studio_rating = itemView.findViewById(R.id.tv_studio_rating);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_delete = itemView.findViewById(R.id.iv_delete);

        }
    }

}
