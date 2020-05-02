package com.example.healthylives.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthylives.R;

public class profileSettingActivity extends AppCompatActivity {
    private  String userName = new String();
    private String userHeight = new String();
    private String userWeight = new String();
    public static final String NAME = "User name";
    public static final String HEIGHT = "User height";
    public static final String WEIGHT = "User weight";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setTitle("Profile Settings");
        updateView();
        Toast.makeText(this, "You are Signed In",Toast.LENGTH_SHORT).show();
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

    //TODO click database also
    public void onclickReset(View v)
    {
        userName = "";
        userHeight = "";
        userWeight="";
        updateView();
    }
}
