package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import no.ahoi.gradle.builditbigger.jokebackend.myApi.MyApi;

/**
 * Created by Andreas on 24.10.2015.
 * Connects the app to the Cloud Endpoints backend API and sends a request.
 * Source: https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 */
public class JokeEndpointsTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static final String LOG_TAG = JokeEndpointsTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private Context context;
    private String mProjectId = MainActivity.getProjectId();

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
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
                    .setRootUrl("https://" + mProjectId + ".appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Log.d(LOG_TAG, mProjectId);

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.fetchAndSetJoke().execute().getData();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(LOG_TAG, "Toasting");
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}