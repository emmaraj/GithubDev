package com.example.emmanuelbaldwin.githubdev;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final String REQUEST_URL = "https://api.github.com/search/users?q=location:lagos+language:java";
    private List<Developer> developerList;
    private RecyclerView mRecyclerView;
    private DeveloperViewAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        developerList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.devList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        new DevelopersAsyncTask().execute(REQUEST_URL);
    }


    private class DevelopersAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }//end doInBackground

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);
            if (result == 1) {
                adapter = new DeveloperViewAdapter(MainActivity.this, developerList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }

        }
    }//end doPostExecute

    private void parseResult(String developerLists) {
        try {
            JSONObject jsonObject = new JSONObject(developerLists);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentJSONObject = jsonArray.getJSONObject(i);
                String devUserName = currentJSONObject.getString("login");
                String devUserImage = currentJSONObject.getString("avatar_url");
                String devUserURL = currentJSONObject.getString("html_url");
                Developer developer = new Developer(devUserName, devUserImage, devUserURL);

                developerList.add(developer);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error extracting from JSON");
        }

    }//end ParseResult
}
