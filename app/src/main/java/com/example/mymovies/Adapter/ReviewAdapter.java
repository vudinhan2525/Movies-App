package com.example.mymovies.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.Domain.Review;
import com.example.mymovies.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterHolder> {
    private List<Review> reviews;
    public ReviewAdapter(List<Review> _reviews){
        this.reviews = _reviews;
    }
    @NonNull
    @Override
    public ReviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ratingholder_item, parent,false);
        return new ReviewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterHolder holder, int position) {
        Review review = reviews.get(position);
        if(review == null){
            return;
        }
        holder.name.setText(review.name);
        String tmp = review.rating + "/5";
        holder.rating.setText(tmp);
        holder.content.setText(review.content);
        Glide.with(holder.itemView.getContext())
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8QpKHeBbrELrNRa-63gDAsBM2TQR3GzSxCYwMw73LVw&s")
                .transform(new CircleCrop())
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        if(reviews != null){
            return reviews.size();
        }
        return 0;
    }

    class ReviewAdapterHolder extends RecyclerView.ViewHolder {

        TextView name, rating,content;
        ImageView pic;

        public ReviewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            content = itemView.findViewById(R.id.content);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
