package com.example.healthylives.Social;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthylives.Adapter.Workout;
import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friendProfileActivity extends AppCompatActivity {
    private String name;
    private String email;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    //private ListView mWorkoutListView;
    private ArrayList<Workout> listOfWorkouts;

    //TODO we need the email
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        setTitle(name + " profile");
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference();
        mDatabaseReference=mDatabase.getReference().child("Users").child(email);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //List<ArrayList<Workout>> workoutList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String name = ds.child("username").getValue(String.class);
                    //String ab = ds.getKey();
                    ArrayList<Workout> workouts= ds.child("workouts").getValue(ArrayList.class);
                    listOfWorkouts=workouts;

                    //String name = String.valueOf(ds.getValue());
                    //workoutList.add(0, workouts);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);
    //TODO set up a list view adapter to view all workout data
    }

    /*public ArrayList<Workout> getWorkouts()
    {
        //ArrayList<Workout> workoutList=new ArrayList<>();

    }*/


    public void prepareTestData()
    {

    }
}
