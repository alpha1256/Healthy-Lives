package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthylives.Social.profileSettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class socialActivity extends AppCompatActivity {
    private EditText mailInput;
    private EditText passwordInput;

    private Button login;
    public FirebaseAuth authSign;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        setTitle("Social");

        mailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        authSign = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null)
                {
                    //Todo after sign in go to next activity
                    Intent intent = new Intent(getApplicationContext(), profileSettingActivity.class);
                    //FirebaseUser user = firebaseAuth.getCurrentUser();
                    //intent.putExtra("Data", user);
                    startActivity(intent);
                }
            }
        };


    }

    /**
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
            Intent intent = new Intent(this, profileSettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }**/

    public void onClickLogin(View v)
    {
        signIn();
    }

    public void onClickSignup(View v)
    {
        String email = mailInput.getText().toString();
        String password = passwordInput.getText().toString();
        authSign.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Created Account", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Not Successful hint password length", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn()
    {
        String email = mailInput.getText().toString();
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
            //intent.putExtra("Data", user);
            startActivity(intent);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

}
