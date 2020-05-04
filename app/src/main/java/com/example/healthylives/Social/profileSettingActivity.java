package com.example.healthylives.Social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthylives.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class profileSettingActivity extends AppCompatActivity {
    private  String userName = new String();
    private String userHeight = new String();
    private String userWeight = new String();
    public static final String NAME = "User name";
    public static final String HEIGHT = "User height";
    public static final String WEIGHT = "User weight";
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setTitle("Profile Settings");
        updateView();
        Toast.makeText(this, "You are Signed In",Toast.LENGTH_SHORT).show();
        user = FirebaseAuth.getInstance().getCurrentUser();
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

    public void updateView()
    {
        TextView name = findViewById(R.id.nameView);
        TextView height = findViewById(R.id.heightView);
        TextView weight = findViewById(R.id.weightView);

        name.setText(userName);
        height.setText(userHeight);
        weight.setText(userWeight);
    }

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

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Localuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(NAME,userName);
        editor.putString(HEIGHT, userHeight);
        editor.putString(WEIGHT, userWeight);
        editor.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("Localuser", Context.MODE_PRIVATE);
        userWeight = sp.getString(WEIGHT,"");
        userHeight = sp.getString(HEIGHT, "1");
        userName = sp.getString(NAME, "2");
        updateView();
    }

    //TODO send data from database to firebase

}
