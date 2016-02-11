package com.nanodegree.android.jokesy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nanodegree.android.jokesy.backend.myApi.MyApi;

import java.io.IOException;

public class FetchJokeAsyncTask  extends AsyncTask<String, Void, String> {
    private static MyApi myApiService = null;
    private static final String LOCAL_DEV_SERVER_IP = "192.168.2.7";
    private static final String LOCAL_DEV_SERVER_PORT = "8080";

    private Context mContext;
    private OnJokeReceivedListener mOnJokeReceivedListener;

    interface OnJokeReceivedListener{
        public void onJokeReceived(String joke);
    }

    public FetchJokeAsyncTask(Context context, OnJokeReceivedListener l){
        mContext = context;
        mOnJokeReceivedListener = l;
    }

    @Override
    protected String doInBackground(String... params) {

        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(" http://"+LOCAL_DEV_SERVER_IP+":"+LOCAL_DEV_SERVER_PORT+"/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.sayHi("hi").execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("TAG", " joke: " + result);
        mOnJokeReceivedListener.onJokeReceived(result);
    }
}
