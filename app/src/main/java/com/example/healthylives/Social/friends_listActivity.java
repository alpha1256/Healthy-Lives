package com.example.healthylives.Social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthylives.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friends_listActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private ListView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setTitle("All Users");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = reference.child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> users = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String name = ds.child("username").getValue(String.class);
                    String ab = ds.getKey();
                    String name = ds.child("username").getValue(String.class);

                    //String name = String.valueOf(ds.getValue());
                    users.add(name);
                }
                mUserListView = (ListView) findViewById(R.id.userList);
                mAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.user_list, users);
                mUserListView.setAdapter(mAdapter);
                mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Intent intent = new Intent(getApplicationContext(), friendProfileActivity.class);
                        //TODO get adapter position 
                        //intent.putExtra("Name", mAdapter.getPosition());
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);
    }

    //TODO this page should display all users and each user should be clickable. That then takes you to a page to view that users stats
    public void updateUI()
    {

        /**
        ArrayList<String> userList=new ArrayList<>();
        ArrayList<String> users=getUsers();
        for(int c=0; c<users.size(); c++)
        {
            //userList.add(users.get(c).getDisplayName());
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
        }**/


    }

    /**
    public ArrayList<String> getUsers()
    {
        final ArrayList<String> users=new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = reference.child("Users");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    //String name = ds.child("username").getValue(String.class);
                    String ab = ds.getKey();
                    String name = ds.child("username").getValue(String.class);

                    //String name = String.valueOf(ds.getValue());
                    users.add(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        userRef.addListenerForSingleValueEvent(eventListener);
        Log.d("AllName", String.valueOf(users.size()));
        /**for (int i = 0; i < users.size(); i++)
        {
            Log.d("AllName", users.get(i));
        }
        return users;
    }**/

    //TODO write a method to handle button click for each user
    public void onClickUser(View view) {

    }
}
