package com.example.mymovies.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovies.Activity.DetailActivity;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.R;

import java.util.List;

public class ListFilmAdapter extends  RecyclerView.Adapter<ListFilmAdapter.ListFilmHolder>{
    private List<Film> listFilm;
    public ListFilmAdapter(List<Film> listFilm){
        this.listFilm = listFilm;
    }

    @NonNull
    @Override
    public ListFilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent,false);
        return new ListFilmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFilmHolder holder, int position) {
        Film film = listFilm.get(position);
        if(film == null){
            return;
        }
        holder.scoreTxt.setText(film.score);
        holder.titleTxt.setText(film.title);
        Glide.with(holder.itemView.getContext()).load(film.imageUrl).into(holder.pic);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("id",film.id);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(listFilm != null){
            return listFilm.size();
        }
        return 0;
    }

    class ListFilmHolder extends RecyclerView.ViewHolder{

        TextView titleTxt, scoreTxt;
        ImageView pic;
        public ListFilmHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            scoreTxt=itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }

}
