package com.example.android.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 31-03-2018.
 */
public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.userNameDetail)
    TextView mUsername;
    @BindView(R.id.emailDetail)
    TextView mEmail;
    @BindView(R.id.aboutMeDetail)
    TextView mAbout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        SharedPreferences mSharedPrefs = this.getBaseContext().getSharedPreferences(MainActivity.SAVED_FILE_KEY, MODE_PRIVATE);
        String username = mSharedPrefs.getString(MainActivity.USER_KEY,"PomPom");
        String email = mSharedPrefs.getString(MainActivity.EMAIL_KEY,"email@example.com");
        String about = mSharedPrefs.getString(MainActivity.ABOUT_KEY,"About me");

        mUsername.setText(username);
        mEmail.setText(email);
        mAbout.setText(about);
    }
}
