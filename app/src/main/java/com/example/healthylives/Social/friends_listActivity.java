package com.example.healthylives.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthylives.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class friends_listActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setTitle("All Users");
    }

    //TODO this page should display all users and each user should be clickable. That then takes you to a page to view that users stats
    public void updateUI()
    {

    }

    //TODO write a method to get a list of users from the database
    public ArrayList<FirebaseUser> getUsers()
    {
        ArrayList<FirebaseUser> users=new ArrayList<>();
        return users;
    }
}
