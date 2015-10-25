package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Pair;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Check that Async task successfully retrieves a non-empty string.
 * Source: http://marksunghunpark.blogspot.no/2015/05/how-to-test-asynctask-in-android.html
 */

public class JokeEndpointsTaskTest extends ApplicationTestCase<Application> {
    final String mProjectId = "build-it-bigger-1108";
    CountDownLatch mSignal;
    Context mContext;
    String mJoke;
    Exception mError;

    public JokeEndpointsTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        mSignal = new CountDownLatch(1);
        mContext = getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        mSignal.countDown();
    }

    public void testExecute() throws InterruptedException {
        JokeEndpointsTask jokeEndpointsTask = new JokeEndpointsTask();
        Pair pair = new Pair<>(mContext, mProjectId);
        jokeEndpointsTask.setListener(new JokeEndpointsTask.JokeEndpointsTaskListener() {
            @Override
            public void onComplete(String joke, Exception e) {
                mJoke = joke;
                mError = e;
                mSignal.countDown();
            }
        }).execute(pair);
        assertTrue(mSignal.await(10, TimeUnit.SECONDS));
        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));
    }
}