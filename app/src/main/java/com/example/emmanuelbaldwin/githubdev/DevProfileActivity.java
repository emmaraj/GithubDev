package com.example.emmanuelbaldwin.githubdev;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DevProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_profile);

        ImageView developerProfileImage = (ImageView) findViewById(R.id.devProfileImage);

        TextView developerProfileURL = (TextView) findViewById(R.id.devProfileUserURL);

        TextView developerProfileUserName = (TextView) findViewById(R.id.devProfileUserName);

        ImageButton shareButton = (ImageButton) findViewById(R.id.share_button);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        final String url = intent.getStringExtra("url");
        String image = intent.getStringExtra("image");
        developerProfileURL.setText(url);

        developerProfileUserName.setText(username);

        Picasso.with(getApplicationContext())
                .load(image)
                .into(developerProfileImage);
        final String message = "Check out this awesome developer @" + username + "," + url;
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        developerProfileURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
