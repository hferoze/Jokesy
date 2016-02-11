package com.nanodegree.android.jokesy;

import android.os.AsyncTask;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nanodegree.android.jokesy.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by locker on 2/6/2016.
 */
public class JokesyFullMainActivityTest extends InstrumentationTestCase {

    private static String joke;

    protected void setUp() throws Exception {
        super.setUp();
        joke = "";
    }

    public void testOnJokeReceived() throws Throwable {
        Log.d("TAGTest", "launch test: ");
        final CountDownLatch signal = new CountDownLatch(1);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        MyApi myApiService = null;
                        if(myApiService == null) {  // Only do this once
                            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                                    new AndroidJsonFactory(), null)
                                     .setRootUrl(" http://192.168.2.2:8080/_ah/api/")
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
                        super.onPostExecute(result);
                        joke = result;
                        Log.d("TAGTest", "joke received: " + joke);
                        signal.countDown();
                    }
                }.execute();
            }
        });
        signal.await(10, TimeUnit.SECONDS);
        assertTrue(!joke.contains("404 Not Found"));
        assertTrue(!joke.contains("EHOSTUNREACH"));
        assertTrue(!joke.equals(""));
        assertTrue(!joke.equals(null));
    }
}