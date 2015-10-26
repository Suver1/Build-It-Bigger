package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import no.ahoi.gradle.builditbigger.jokebackend.myApi.MyApi;
import no.ahoi.gradle.jokewiz.JokeShow;

/**
 * Created by Andreas on 24.10.2015.
 * Connects the app to the Cloud Endpoints backend API and sends a request.
 * Source: https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 */
public class JokeEndpointsTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static final String LOG_TAG = JokeEndpointsTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private static Context mContext;
    private static String mJoke;

    // Unit test related
    private JokeEndpointsTaskListener mListener;
    private Exception mError;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        mContext = params[0].first;
        String projectId = params[0].second;
        if(myApiService == null) {  // Only do this once
            Log.d(LOG_TAG, "connecting...");
            /*
            // Before module was deployed (Works only with emulator)
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            */
            // After module was deployed (Works with both emulator and connected devices)
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://" + projectId + ".appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Log.d(LOG_TAG, projectId);

        try {
            return myApiService.fetchAndSetJoke().execute().getData();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            mError = e;
            return null;
        }
    }

    @Override
    protected void onCancelled() {
        if (mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            mListener.onComplete(null, mError);
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (mListener == null) {
            if (result != null) {
                mJoke = result;
                if (!MainActivity.mInterstitialAdIsShowing) {
                    startJokeActivity();
                }
            } else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.get_joke_failed),
                        Toast.LENGTH_LONG).show();
            }
            MainActivity.hideSpinner();
        } else { // Test case
            mListener.onComplete(result, mError);
        }
    }

    public static void startJokeActivity() {
        if (mJoke != null && mContext != null) {
            Log.d(LOG_TAG, "Starting android library activity: JokeShow");
            Intent intent = new Intent(mContext, JokeShow.class);
            Bundle extras = new Bundle();
            extras.putString("joke", mJoke);
            intent.putExtras(extras);
            mContext.startActivity(intent);
        }
    }

    // Unit test related
    public JokeEndpointsTask setListener(JokeEndpointsTaskListener listener) {
        mListener = listener;
        return this;
    }

    // Unit test related
    public static interface JokeEndpointsTaskListener {
        public void onComplete(String joke, Exception e);
    }
}