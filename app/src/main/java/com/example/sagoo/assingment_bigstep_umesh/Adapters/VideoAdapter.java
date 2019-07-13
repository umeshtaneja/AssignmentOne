package com.example.sagoo.assingment_bigstep_umesh.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagoo.assingment_bigstep_umesh.Activities.VideoDetailActivity;
import com.example.sagoo.assingment_bigstep_umesh.Models.Music;
import com.example.sagoo.assingment_bigstep_umesh.Models.Video;
import com.example.sagoo.assingment_bigstep_umesh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Video> videosList;
    private static LayoutInflater inflater = null;

    public VideoAdapter(FragmentActivity context, List<Video> videosList) {
        this.context = context;
        this.videosList = videosList;
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
        itemHolder.names.setText(videosList.get(position).getTrackName());
    }

    private void showImage(int position, ViewHolder itemHolder) {
        Picasso.with(context)
                .load(videosList.get(position).getArtistImgUrl())
                .into(itemHolder.imgArtist);
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView names;
        ImageView imgArtist;
        RelativeLayout rvItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = (TextView) itemView.findViewById(R.id.tv_name);
            imgArtist = itemView.findViewById(R.id.img_artist);
            rvItem = itemView.findViewById(R.id.rv_item);

            rvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,VideoDetailActivity.class);
                    intent.putExtra("url",videosList.get(getLayoutPosition()).getPreviewUrl());
                    context.startActivity(intent);

                }
            });

        }
    }
}
