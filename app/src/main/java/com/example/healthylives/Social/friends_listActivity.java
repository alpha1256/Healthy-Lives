package com.example.healthylives.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthylives.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class friends_listActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private ListView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setTitle("All Users");
        mUserListView=(ListView) findViewById(R.id.userList);
    }

    //TODO this page should display all users and each user should be clickable. That then takes you to a page to view that users stats
    public void updateUI()
    {
        ArrayList<String> userList=new ArrayList<>();
        ArrayList<FirebaseUser> users=getUsers();
        for(int c=0; c<users.size(); c++)
        {
            userList.add(users.get(c).getDisplayName());
        }
        if(mAdapter==null)
        {
            mAdapter=new ArrayAdapter<>(this, R.layout.user_list, R.id.user, userList);
            mUserListView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.clear();
            mAdapter.addAll(userList);
            mAdapter.notifyDataSetChanged();
        }


    }

    //TODO write a method to get a list of users from the database
    public ArrayList<FirebaseUser> getUsers()
    {
        ArrayList<FirebaseUser> users=new ArrayList<>();
        return users;
    }
    //TODO write a method to handle button click for each user
    public void onClickUser(View view) {

    }
}
