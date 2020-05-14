package com.example.healthylives.Social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthylives.Adapter.Workout;
import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.WorkoutsDbHelper;
import com.example.healthylives.MainActivity;
import com.example.healthylives.R;
import com.example.healthylives.Services.WorkoutService;
import com.example.healthylives.workoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has to deal with the user profile
 */
public class profileSettingActivity extends AppCompatActivity {
    private  String userName = new String();
    private String userHeight = new String();
    private String userWeight = new String();
    public static final String NAME = "User name";
    public static final String HEIGHT = "User height";
    public static final String WEIGHT = "User weight";
    public static final String EMAIL = "Email";
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    //private WorkoutsDbHelper mHelper;
    private String email = new String ();


    /**
     * Initialization of the firebase variables and get email from the previous activity (social activity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setTitle("Profile Settings");
        updateView();
        Intent intent = getIntent();
        if (getIntent() != null)
        {
            email = intent.getStringExtra("Username");
            //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, "You are Signed In",Toast.LENGTH_SHORT).show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference();
        //mHelper=new WorkoutsDbHelper(this);
    }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.social_menu, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting)
        {
            Toast.makeText(this, "You are already on this page", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.friend)
        {
            Intent intent = new Intent(this, friends_listActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
     }

    /**
     * Update the view with the current name, height and weight
     */
    public void updateView()
    {
        TextView name = findViewById(R.id.nameView);
        TextView height = findViewById(R.id.heightView);
        TextView weight = findViewById(R.id.weightView);

        name.setText(email);
        height.setText(userHeight);
        weight.setText(userWeight);
    }

    /**
     * This update the local user profile and update the username of the user on firebase
     * @param v
     */
    public void onClickUpdate(View v)
    {
        EditText name = findViewById(R.id.nameEntry);
        EditText height = findViewById(R.id.heightEntry);
        EditText weight = findViewById(R.id.weightEntry);

        userName = String.valueOf(name.getText());
        userHeight = String.valueOf(height.getText());
        userWeight = String.valueOf(weight.getText());
        updateView();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName).build();
        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * On pause saves user data into a shared preference
     */
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Localuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(NAME,userName);
        editor.putString(HEIGHT, userHeight);
        editor.putString(WEIGHT, userWeight);
        //editor.putString(EMAIL, email);
        editor.commit();
    }

    /**
     * On resume retrieves user data from a shared preference
     */
    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("Localuser", Context.MODE_PRIVATE);
        userWeight = sp.getString(WEIGHT,"");
        userHeight = sp.getString(HEIGHT, "1");
        userName = sp.getString(NAME, "");
        //email = sp.getString(EMAIL, "");
        updateView();
    }

    /*public void sendDataToFirebase()
    {
        List<Workout> workoutList=new ArrayList<>();
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.query(DaysContract.DayEntry.TABLE2, new String[]{DaysContract.DayEntry._ID, DaysContract.DayEntry.COL_WORKOUT_TIME, DaysContract.DayEntry.COL_WORKOUT_NAME, DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_WORKOUT_DURATION, DaysContract.DayEntry.COL_WORKOUT_DISTANCE}, null, null, null, null, null);
        if(cursor.getCount()<1)
        {
            return;
        }
        while (cursor.moveToNext())
        {
            int idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_TIME);
            String time=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_NAME);
            String name=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_DATE);
            String date=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_DURATION);
            String duration=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_DISTANCE);
            float distance=cursor.getFloat(idx);
            workoutList.add(new Workout(time, name, date, duration, distance));
        }
        mDatabaseReference=mDatabase.getReference().child("Users").child(email).child("workouts");
        mDatabaseReference.setValue(workoutList);
    }*/

    /**
     * Button click that signs ours out
     * @param v
     */
    public void onClickSignOut(View v)
    {
        FirebaseAuth authUser = FirebaseAuth.getInstance();
        authUser.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    /**
     * Button click that update firebase with the most recent workout node
     * @param v
     */
    public void onClickSendFire(View v)
    {
        Intent intent=new Intent(this, WorkoutService.class);
        String temp=email;
        intent.putExtra("email", temp);
        startService(intent);
        Toast.makeText(this, "Workout sent to Firebase", Toast.LENGTH_SHORT).show();
    }
}
