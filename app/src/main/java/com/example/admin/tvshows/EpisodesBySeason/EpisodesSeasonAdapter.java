package com.example.admin.tvshows.EpisodesBySeason;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.tvshows.R;
import com.example.admin.tvshows.pogo.EpisodesSeasonList;

import java.util.List;

public class EpisodesSeasonAdapter extends RecyclerView.Adapter<EpisodesSeasonAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    Context context;
    private List<EpisodesSeasonList> episodesSeasonList;

    // data is passed into the constructor
    EpisodesSeasonAdapter(Context context, List<EpisodesSeasonList> episodesSeasonList) {
        this.mInflater = LayoutInflater.from(context);
        this.episodesSeasonList = episodesSeasonList;
    }

    public void setEpisodesList(List<EpisodesSeasonList> episodesSeasonList) {
        this.episodesSeasonList = episodesSeasonList;
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.episode_season_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(episodesSeasonList.get(position).getSummary() != null) {
            String summary = stripHtml(episodesSeasonList.get(position).getSummary());
            holder.summary.setText(summary);
        }else{
            String summary = "Not available";
            holder.summary.setText(summary);
        }

        if(episodesSeasonList.get(position).getNumber() != null) {
            String numEpisode = "Episode : " + episodesSeasonList.get(position).getNumber();
            holder.numEp.setText(numEpisode);
        }

        if(episodesSeasonList.get(position).getName() != null) {
            String nameEpisode = episodesSeasonList.get(position).getName();
            holder.nameEp.setText(nameEpisode);
        }

        if (episodesSeasonList.get(position).getImage() != null && episodesSeasonList.get(position).getImage().getMedium() != null) {
            String imgUrl = episodesSeasonList.get(position).getImage().getMedium();
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
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    @Override
    public int getItemCount() {
        if (episodesSeasonList != null) {
            return episodesSeasonList.size();
        }
        return 0;

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView summary;
        ImageView image;
        TextView numEp;
        TextView nameEp;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            summary = (TextView)itemView.findViewById(R.id.epSummary);
            image = itemView.findViewById(R.id.avatarEpisodeImage);
            numEp = (TextView)itemView.findViewById(R.id.episodeNumber);
            nameEp = (TextView)itemView.findViewById(R.id.episodeName);
        }

        @Override
        public void onClick(View view) {
            //Intent i = new Intent(view.getContext(), Cast.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.putExtra("showMovieName", showMovieName);
            //i.putExtra("sessionNumber", episodesList.get(getLayoutPosition()).getNumber());
            //i.putExtra("showID", showID);
//            i.putExtra("showImage", showsList.get(getLayoutPosition()).getShow().getImage().getMedium());
//            i.putExtra("numSession", showsList.get(getLayoutPosition()).getSeason());
//            i.putExtra("numEp", showsList.get(getLayoutPosition()).getNumber());
//            i.putExtra("showType", showsList.get(getLayoutPosition()).getShow().getType());
//            i.putExtra("summary", showsList.get(getLayoutPosition()).getShow().getSummary());
            //view.getContext().startActivity(i);
        }
    }

}
