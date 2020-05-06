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
import android.widget.Toast;

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
                        Intent intent = new Intent(getApplicationContext(), friendProfileActivity.class);
                        //TODO get adapter position
                        String temp = String.valueOf(parent.getItemAtPosition(position));
                        intent.putExtra("Name", temp);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);
    }

}
