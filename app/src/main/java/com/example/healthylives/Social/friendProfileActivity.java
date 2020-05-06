package com.example.healthylives.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.healthylives.R;

public class friendProfileActivity extends AppCompatActivity {
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        setTitle(name + " profile");
    //TODO set up a list view adapter to view all workout data
    }

    public void prepareTestData()
    {

    }
}
