package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import no.ahoi.builditbigger.JokeProvider;
import no.ahoi.gradle.jokewiz.JokeShow;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static Context mAppContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppContext = getApplicationContext();

        // Settings for ActionBar - add icon and color
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red900)));
        }

        JokeEndpointsTask jokeEndpointsTask = new JokeEndpointsTask();
        jokeEndpointsTask.execute(new Pair<Context, String>(this, "Andy"));
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
        Intent intent = new Intent(getApplicationContext(), JokeShow.class);
        JokeProvider jokeProvider = new JokeProvider();
        Bundle extras = new Bundle();
        extras.putString("joke", jokeProvider.getJoke());
        intent.putExtras(extras);
        startActivity(intent);
    }

    public static String getProjectId() {
        return mAppContext.getResources().getString(R.string.project_id);
    }
}
