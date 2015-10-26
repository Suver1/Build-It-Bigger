package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static ProgressBar mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (ProgressBar)findViewById(R.id.loadJokeProgressBar);
        hideSpinner();

        // Settings for ActionBar - add icon and color
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red900)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if (isOnline()) {
            showSpinner();
            // Execute a AsyncTask which gets a joke from a server and launches a new activity
            JokeEndpointsTask jokeEndpointsTask = new JokeEndpointsTask();
            Pair pair = new Pair<Context, String>(this,
                    getResources().getString(R.string.project_id));
            jokeEndpointsTask.execute(pair);
        } else {
            onlineToast();
        }
    }

    // Check the Network Connection. (permission needed)
    // Source: http://developer.android.com/intl/ko/training/basics/network-ops/connecting.html
    private Boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void onlineToast() {
        Toast.makeText(this, getResources().getString(R.string.network_lost),
                Toast.LENGTH_LONG).show();
    }

    public static void showSpinner() {
        mSpinner.setVisibility(View.VISIBLE);
    }

    public static void hideSpinner() {
        mSpinner.setVisibility(View.GONE);
    }
}
