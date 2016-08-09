package com.epicodus.weatherpit.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.epicodus.weatherpit.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.githubLinkTextView) TextView mGitHubLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        ButterKnife.bind(this);

        mGitHubLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mGitHubLink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://developer.forecast.io/"));
            startActivity(webIntent);
        }
    }

}
