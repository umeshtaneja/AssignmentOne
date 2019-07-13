package com.example.sagoo.assingment_bigstep_umesh.Activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagoo.assingment_bigstep_umesh.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MusicDetailsActivity extends AppCompatActivity {

    String audioUrl = "";
    String artistImgUri = "";
    String songName,ArtistName;
    Double price;
    MediaPlayer mPlayer;
    private ImageView imgArtist;
    private TextView tvSongName,tvArtistName,tvPrice;
    ImageView imgPlay,imgPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);

        Bundle bundle = getIntent().getExtras();
        audioUrl = bundle.getString("audio_url");
        artistImgUri = bundle.getString("profile_url");
        songName = bundle.getString("song_name");
        ArtistName = bundle.getString("artist_name");
        price = bundle.getDouble("price");

        showActionBar();

        imgArtist = findViewById(R.id.img_artist);
        tvSongName = findViewById(R.id.tv_track_name);
        tvArtistName = findViewById(R.id.tv_artist_name);
        tvPrice = findViewById(R.id.tv_price);
        imgPlay = findViewById(R.id.img_play);
        imgPause = findViewById(R.id.img_pause);

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayMusic();
            }
        });

        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseMusic();
            }
        });

        tvSongName.setText("Song Name : "+ songName);
        tvArtistName.setText("Artist Name : "+ ArtistName);
        tvPrice.setText("Price : "+ price);

        LoadProfile();

    }


    private void PlayMusic() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(audioUrl);
            mPlayer.prepare();
            mPlayer.start();
            imgPlay.setVisibility(View.GONE);
            imgPause.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(),"End",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PauseMusic() {
        if (mPlayer!=null)
            if(mPlayer.isPlaying()){
                mPlayer.stop();
                imgPause.setVisibility(View.GONE);
                imgPlay.setVisibility(View.VISIBLE);
            }

    }

    private void LoadProfile() {
        Picasso.with(this)
                .load(artistImgUri)
                .into(imgArtist);
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Music Details");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPlayer!=null)
            if(mPlayer.isPlaying())
                mPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer!=null)
            if(mPlayer.isPlaying())
                mPlayer.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        imgPause.setVisibility(View.GONE);
        imgPlay.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
