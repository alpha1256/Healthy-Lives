package com.example.healthylives.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthylives.R;

public class friends_listActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setTitle("All Users");
    }

    //TODO this page should display all users and each user should be clickable. That then takes you to a page to view that users stats
}
