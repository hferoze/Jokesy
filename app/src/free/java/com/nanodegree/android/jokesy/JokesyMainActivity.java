package com.nanodegree.android.jokesy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.nanodegree.android.jokesy.FetchJokeAsyncTask.OnJokeReceivedListener;
import com.nanodegree.android.jokesyandroidlib.JokesyLibActivity;

public class JokesyMainActivity extends AppCompatActivity implements OnJokeReceivedListener{

    private static final int JOKE_LOADING = 0;
    private static final int JOKE_LOADING_FINISHED=1;
    private static final int JOKE_LOADING_FAILED=2;

    private static final String JOKE_KEY = "joke_key";
    private FrameLayout mFragmentContainer;
    private ProgressBar mLoadingProgressBar;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokesy_main);

        mFragmentContainer = (FrameLayout) findViewById(R.id.container);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.joke_loading_ProgressBar);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                setLoadingState(JOKE_LOADING);
                requestNewInterstitial();
                showJoke();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void tellJoke(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else{
            setLoadingState(JOKE_LOADING);
            showJoke();
        }
    }

    public void showJoke(){
        FetchJokeAsyncTask fetchJokeAsyncTask = new FetchJokeAsyncTask(getApplicationContext(), this);
        fetchJokeAsyncTask.execute();
    }

    @Override
    public void onJokeReceived(String joke) {
        if (joke!=null && joke!=""
                && !joke.contains("EHOSTUNREACH") && !joke.contains("404")) {
            Intent intent = new Intent(this, JokesyLibActivity.class);
            intent.putExtra(JOKE_KEY, joke);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Failed to fetch joke :( ", Toast.LENGTH_SHORT).show();
            setLoadingState(JOKE_LOADING_FAILED);
        }
    }

    public void setLoadingState(int state){
        switch (state){
            case JOKE_LOADING:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                mFragmentContainer.setVisibility(View.INVISIBLE);
                break;
            case JOKE_LOADING_FINISHED:
                mFragmentContainer.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                break;
            case JOKE_LOADING_FAILED:
                mFragmentContainer.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        setLoadingState(JOKE_LOADING_FINISHED);
    }

}
