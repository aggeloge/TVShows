package com.example.admin.tvshows.Cast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.tvshows.R;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    Context context;
    private List<CastList> castList;

    // data is passed into the constructor
    CastAdapter(Context context, List<CastList> castList) {
        this.mInflater = LayoutInflater.from(context);
        this.castList = castList;
    }

    public void setCastList(List<CastList> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(castList.get(position).getPerson() != null || castList.get(position).getPerson().getName() != null) {
            String name = castList.get(position).getPerson().getName();
            holder.nameTxt.setText(name);
        }

        if(castList.get(position).getCharacter() != null || castList.get(position).getCharacter().getName() != null) {
            String nameCh = "as  " + castList.get(position).getCharacter().getName();
            holder.nameCharacter.setText(nameCh);
        }

        if (castList.get(position).getPerson().getImage() != null && castList.get(position).getPerson().getImage().getMedium() != null) {
            String imgUrl = castList.get(position).getPerson().getImage().getMedium();
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

    @Override
    public int getItemCount() {
        if (castList != null) {
            return castList.size();
        }
        return 0;

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTxt;
        ImageView image;
        TextView nameCharacter;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nameTxt = (TextView)itemView.findViewById(R.id.actorName);
            image = itemView.findViewById(R.id.avatarActor);
            nameCharacter = (TextView)itemView.findViewById(R.id.characterName);
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
