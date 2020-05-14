package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthylives.Social.Users;
import com.example.healthylives.Social.profileSettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class allows login and account creation
 */
public class socialActivity extends AppCompatActivity {
    private EditText mailInput;
    private EditText passwordInput;

    private Button login;
    public FirebaseAuth authSign;
    private FirebaseAuth.AuthStateListener authListener;
    private Users newUser;
    private FirebaseDatabase database;
    private DatabaseReference dataReference;
    private String email = new String();

    /**
     * Initialization of firebase variables and checks if the firebase state has been changed which then sends the email and takes user to profile setting activity class 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        setTitle("Login");

        mailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        authSign = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dataReference = database.getReference();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null)
                {
                    Intent intent = new Intent(getApplicationContext(), profileSettingActivity.class);
                    //FirebaseUser user = firebaseAuth.getCurrentUser();
                    intent.putExtra("Username", email);
                    startActivity(intent);
                }
            }
        };
    }

    /**
     * Button click which calls signIn()
     * @param v
     */
    public void onClickLogin(View v)
    {
        signIn();
    }

    /**
     * Button click which allows users to sign up for firebase with email and password
     * @param v
     */
    public void onClickSignup(View v)
    {
        email = mailInput.getText().toString();
        String password = passwordInput.getText().toString();

        dataReference = database.getReference().child("Users");
        authSign.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    newUser = new Users(email);
                    dataReference.push().setValue(newUser);
                    Toast.makeText(getApplicationContext(), "Created Account", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This allows users to sign in, if not successful displays message
     */
    private void signIn()
    {
        email = mailInput.getText().toString();
        String password = passwordInput.getText().toString();
        authSign.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(socialActivity.this, "Not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * When activity is started firebase is checked. If the user is currently signed in, the email is sent to the profile settings activity which is also then started
     */
    @Override
    public void onStart()
    {
        super.onStart();
        authSign.addAuthStateListener(authListener);
        //authListener.onAuthStateChanged(authListener);
        FirebaseUser user = authSign.getCurrentUser();
        if (user != null)
        {
            Intent intent = new Intent(this, profileSettingActivity.class);
            intent.putExtra("Username", email);
            startActivity(intent);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    /**
     * When activity is paused the email get put into a shared preference
     */
    @Override
    public void onPause()
    {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Social", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("EMAIL", email);
        editor.commit();
    }

    /**
     * When activity is resumed the email is retrieved from the shared preference
     */
    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("Social", Context.MODE_PRIVATE);
        email=sp.getString("EMAIL", "");
    }

}
