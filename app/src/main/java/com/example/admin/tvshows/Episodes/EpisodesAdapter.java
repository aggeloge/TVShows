package com.example.admin.tvshows.Episodes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.tvshows.EpisodesBySeason.EpisodesBySeason;
import com.example.admin.tvshows.R;
import com.example.admin.tvshows.pogo.EpisodesList;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    Context context;
    private List<EpisodesList> episodesList;
    private String showMovieName;
    private String showID;

    // data is passed into the constructor
    EpisodesAdapter(Context context, List<EpisodesList> episodesList, String showMovieName, String showID) {
        this.mInflater = LayoutInflater.from(context);
        this.episodesList = episodesList;
        this.showMovieName = showMovieName;
        this.showID = showID;
    }

    public void setEpisodesList(List<EpisodesList> episodesList) {
        this.episodesList = episodesList;
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.episode_show_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fullText = "Season : " + episodesList.get(position).getNumber();
        holder.myShowSeason.setText(fullText);
//        if (episodesList.get(position).getImage() != null) {
//            String imgUrl = episodesList.get(position).getImage();
//            String replacedImgUrl = imgUrl.replace("http", "https");
//            Glide.with (holder.itemView.getContext())
//                    .load (replacedImgUrl)
//                    .into (holder.image);
//        }else{
//            Glide.with (holder.itemView.getContext())
//                    .load ("https://via.placeholder.com/300.png")
//                    .into (holder.image);
//        }
    }

    @Override
    public int getItemCount() {
        if (episodesList != null) {
            return episodesList.size();
        }
        return 0;

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myShowSeason;
        //ImageView image;
        TextView b;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            myShowSeason = (TextView)itemView.findViewById(R.id.epShowItem);
            //image = itemView.findViewById(R.id.seasonImageView);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), EpisodesBySeason.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("showMovieName", showMovieName);
            i.putExtra("sessionNumber", episodesList.get(getLayoutPosition()).getNumber());
            i.putExtra("showID", showID);
//            i.putExtra("showImage", showsList.get(getLayoutPosition()).getShow().getImage().getMedium());
//            i.putExtra("numSession", showsList.get(getLayoutPosition()).getSeason());
//            i.putExtra("numEp", showsList.get(getLayoutPosition()).getNumber());
//            i.putExtra("showType", showsList.get(getLayoutPosition()).getShow().getType());
//            i.putExtra("summary", showsList.get(getLayoutPosition()).getShow().getSummary());
            view.getContext().startActivity(i);
        }
    }

}
