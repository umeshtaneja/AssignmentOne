package com.example.sagoo.assingment_bigstep_umesh.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sagoo.assingment_bigstep_umesh.Adapters.MusicAdapter;
import com.example.sagoo.assingment_bigstep_umesh.Models.Music;
import com.example.sagoo.assingment_bigstep_umesh.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MusicFragment extends Fragment {

    String url = "https://itunes.apple.com/search";
    private RecyclerView rvMusic;
    private MusicAdapter musicAdapter;
    List<Music> musicList = new ArrayList<>();

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isConnectionAvailable(getActivity()))
        getMusicDataFromApi();
        else Toast.makeText(getActivity(),"No Newtwork Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.fragment_music, container, false);

        rvMusic = myFragmentView.findViewById(R.id.rvMusic);
        musicAdapter = new MusicAdapter(getActivity(),musicList);
        rvMusic.setAdapter(musicAdapter);
        rvMusic.setLayoutManager(new LinearLayoutManager(getActivity()));

        return myFragmentView;
    }

    private void getMusicDataFromApi() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("term", "Michael jackson");
        params.put("media", "music");

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    final String response = new String(responseBody, "UTF-8");
                    Log.d("value"," step 0 " + response);
                    parseMusicData(response);
                    musicAdapter.notifyDataSetChanged();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("data failure","  ");
            }
      });
    }

    private void parseMusicData(String response) {
        int noOfResults = 0;
        JSONObject jsonObject = null;
        JSONArray resultJson = null;

        try {
            jsonObject = new JSONObject(response);

            if(isValid(jsonObject,"resultCount")){
                noOfResults = jsonObject.getInt("resultCount");
            }

            if(isValid(jsonObject,"results")){
                resultJson = jsonObject.getJSONArray("results");
            }

            if(noOfResults > 0){

                for(int i = 0; i < resultJson.length(); i++){
                    Music music = new Music();
                    JSONObject singleResultObject = resultJson.getJSONObject(i);

                    if(isValid(singleResultObject,"artistName")){
                        String artistName = singleResultObject.getString("artistName");
                        music.setArtistName(artistName);
                    }

                    if(isValid(singleResultObject,"trackName")){
                        String trackName = singleResultObject.getString("trackName");
                        music.setTrackName(trackName);
                    }

                    if(isValid(singleResultObject,"trackPrice")){
                        Double trackPrice = singleResultObject.getDouble("trackPrice");
                        music.setTrackPrice(trackPrice);
                    }


                    if(isValid(singleResultObject,"artworkUrl100")){
                        String artistViewUrl = singleResultObject.getString("artworkUrl100");
                        music.setArtistImgUrl(artistViewUrl);
                    }

                    if(isValid(singleResultObject,"previewUrl")){
                        String songPreviewUrl = singleResultObject.getString("previewUrl");
                        music.setPreviewUrl(songPreviewUrl);
                    }

                    musicList.add(music);

                }
                Log.d("values","step 4  trackable names" + musicList.get(1).getTrackName());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject == null || key == null)
            return false;
        return (jsonObject.has(key) && !jsonObject.isNull(key));
    }

    public boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

}
