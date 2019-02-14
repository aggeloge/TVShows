package com.example.admin.tvshows;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.tvshows.pogo.PopularTvShows;

import java.util.List;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    Context context;
    private List<PopularTvShows> showsList;

    // data is passed into the constructor
    ShowsAdapter(Context context, List<PopularTvShows> showsList) {
        this.mInflater = LayoutInflater.from(context);
        this.showsList = showsList;
    }

    public void setShowsList(List<PopularTvShows> showsList) {
        this.showsList = showsList;
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tv_show_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(showsList.get(position).getShow().getName() != null){
            String getNameMovie = showsList.get(position).getShow().getName();
            holder.nameShow.setText(getNameMovie);
        }else{
            holder.nameShow.setText("-");
        }

        if(showsList.get(position).getShow().getRating().getAverage() != null){
            String getRateMovie = showsList.get(position).getShow().getRating().getAverage();
            holder.rateShow.setText(getRateMovie);
        }else{
            holder.rateShow.setText("-");
        }

        if (showsList.get(position).getShow() != null && showsList.get(position).getShow().getImage() != null) {
            String imgUrl = showsList.get(position).getShow().getImage().getMedium();
            if(imgUrl!=null){
                String replacedImgUrl = imgUrl.replace("http", "https");
                Glide.with (holder.itemView.getContext())
                        .load (replacedImgUrl)
                        .into (holder.image);
            }
        }else{
            Glide.with (holder.itemView.getContext())
                    .load (R.drawable.movie)
                    .into (holder.image);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), position + ":" , Toast.LENGTH_SHORT).show();
            }
        });

        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), position + ":" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (showsList != null) {
            return showsList.size();
        }
        return 0;

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView nameShow;
        TextView rateShow;
        ImageView favorite;
        ImageView rate;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.tvShowItem);
            nameShow = itemView.findViewById(R.id.placeholderShow);
            rateShow = itemView.findViewById(R.id.rateShow);
            favorite = itemView.findViewById(R.id.favorite);
            rate = itemView.findViewById(R.id.star);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ShowName.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("showMovieName", showsList.get(getLayoutPosition()).getShow().getName());
            i.putExtra("showEpisodeName", showsList.get(getLayoutPosition()).getName());
            i.putExtra("showImage", showsList.get(getLayoutPosition()).getShow().getImage().getMedium());
            i.putExtra("numSession", showsList.get(getLayoutPosition()).getSeason());
            i.putExtra("numEp", showsList.get(getLayoutPosition()).getNumber());
            i.putExtra("showType", showsList.get(getLayoutPosition()).getShow().getType());
            i.putExtra("summary", showsList.get(getLayoutPosition()).getShow().getSummary());
            i.putExtra("idShow", showsList.get(getLayoutPosition()).getShow().getId());
            i.putExtra("averageShow", showsList.get(getLayoutPosition()).getShow().getRating().getAverage());
            view.getContext().startActivity(i);
        }
    }
}
