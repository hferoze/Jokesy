package com.nanodegree.android.jokesyandroidlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by locker on 2/6/2016.
 */
public class JokesyLibActivity extends AppCompatActivity {

    private static final String JOKE_KEY = "joke_key";

    private TextView mJokesTextView;
    private String mJoke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jokesy_lib_view);
        mJoke = "seriously, there's no joke";
        mJokesTextView = (TextView) findViewById(R.id.joke_textView);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            mJoke = extras.getString(JOKE_KEY, mJoke);
        }

        mJokesTextView.setText(mJoke);
    }
}
