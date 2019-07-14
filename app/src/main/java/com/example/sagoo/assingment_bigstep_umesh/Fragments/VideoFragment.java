package com.example.sagoo.assingment_bigstep_umesh.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sagoo.assingment_bigstep_umesh.Adapters.MusicAdapter;
import com.example.sagoo.assingment_bigstep_umesh.Adapters.VideoAdapter;
import com.example.sagoo.assingment_bigstep_umesh.Models.Video;
import com.example.sagoo.assingment_bigstep_umesh.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class VideoFragment extends Fragment {

    private VideoAdapter VideoAdapter;
    List<Video> videosList = new ArrayList<>();

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isConnectionAvailable(getActivity()))
            getMusicDataFromApi();
        else
            Toast.makeText(getActivity(),"No Network Connection",Toast.LENGTH_SHORT).show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_video, container, false);

        RecyclerView rvVideos = myFragmentView.findViewById(R.id.rv_videos);
        VideoAdapter = new VideoAdapter(getActivity(),videosList);
        rvVideos.setAdapter(VideoAdapter);
        rvVideos.setLayoutManager(new LinearLayoutManager(getActivity()));
        return myFragmentView;
    }



    private void getMusicDataFromApi() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("term", "Michael jackson");
        params.put("media", "musicVideo");

        String url = "https://itunes.apple.com/search";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    final String response = new String(responseBody, "UTF-8");
                    Log.d("videoData", "  " +response);
                    parseVideosData(response);
                    VideoAdapter.notifyDataSetChanged();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    private void parseVideosData(String response) {
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
                    Video video = new Video();
                    JSONObject singleResultObject = resultJson.getJSONObject(i);

                    if(isValid(singleResultObject,"artistName")){
                        String artistName = singleResultObject.getString("artistName");
                        video.setArtistName(artistName);
                    }

                    if(isValid(singleResultObject,"trackName")){
                        String trackName = singleResultObject.getString("trackName");
                        video.setTrackName(trackName);
                    }

                    if(isValid(singleResultObject,"trackPrice")){
                        Double trackPrice = singleResultObject.getDouble("trackPrice");
                        video.setTrackPrice(trackPrice);
                    }

                    if(isValid(singleResultObject,"artworkUrl100")){
                        String artistViewUrl = singleResultObject.getString("artworkUrl100");
                        video.setArtistImgUrl(artistViewUrl);
                    }

                    if(isValid(singleResultObject,"previewUrl")){
                        String songPreviewUrl = singleResultObject.getString("previewUrl");
                        video.setPreviewUrl(songPreviewUrl);
                    }

                    videosList.add(video);
                }
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
