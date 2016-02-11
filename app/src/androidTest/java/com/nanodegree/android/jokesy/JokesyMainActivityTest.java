package com.nanodegree.android.jokesy;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by locker on 2/6/2016.
 */
public class JokesyMainActivityTest extends InstrumentationTestCase {

    private static String joke;

    protected void setUp() throws Exception {
        super.setUp();
        joke = "";
    }

    public void testOnJokeReceived() throws Throwable {
        Log.d("TAGTest", "free test: ");
    }
}