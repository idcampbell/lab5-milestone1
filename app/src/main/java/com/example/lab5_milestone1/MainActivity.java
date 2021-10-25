package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        // If user is already logged in, shift to the correct view.
        String usernameStr = sharedPreferences.getString(usernameKey, "");
        if (!usernameStr.equals("")) {
            goToWelcomeActivity(usernameStr, "");
        }
        // Otherwise prompt the user for login.
        else {
            setContentView(R.layout.activity_main);
        }
    }

    public void clickFunction(View view) {
        EditText usernameText = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String usernameStr = usernameText.getText().toString();
        String passwordStr = passwordText.getText().toString();
        sharedPreferences.edit().putString("username", usernameStr).apply();
        sharedPreferences.edit().putString("password", passwordStr).apply();
        goToWelcomeActivity(usernameStr, passwordStr);
    }

    public void goToWelcomeActivity(String usernameStr, String passwordStr) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("username",usernameStr);
        //intent.putExtra("password",passwordStr);
        startActivity(intent);
    }
}