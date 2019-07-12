package com.example.sagoo.assingment_bigstep_umesh.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagoo.assingment_bigstep_umesh.Fragments.MusicFragment;
import com.example.sagoo.assingment_bigstep_umesh.Models.Music;
import com.example.sagoo.assingment_bigstep_umesh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Music> musicList;
    private static LayoutInflater inflater = null;

    public MusicAdapter(FragmentActivity context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.single_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder itemHolder = (ViewHolder) viewHolder;
        showImage(position,itemHolder);
        itemHolder.names.setText(musicList.get(position).getTrackName());
    }

    private void showImage(int position, ViewHolder itemHolder) {
        Picasso.with(context)
                .load(musicList.get(position).getArtistImgUrl())
                .into(itemHolder.imgArtist);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView names;
        public ImageView imgArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = (TextView) itemView.findViewById(R.id.tv_name);
            imgArtist = itemView.findViewById(R.id.img_artist);
        }
    }
}
