package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Get EditTextView.
        EditText noteText = (EditText) findViewById(R.id.editNote);

        // Get intent.
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = WelcomeActivity.notes.get(noteid);
            String noteContent = note.getContent();
            noteText.setText(noteContent);
        }
    }

    public void clickSave(View view) {
        // Get editText content.
        EditText noteText = (EditText) findViewById(R.id.editNote);
        String noteStr = noteText.getText().toString();
        noteText.setText(noteStr);
        // Initialize SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        // Initialize DBHelper.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        // Get the username from the shared preferences.
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        // Save the note to the database.
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());



        if (noteid == -1) { //add note.

            //Log.d("newNOTE", "Note is being added");

            title = "NOTE_" + (WelcomeActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, noteStr, date);
        } else { // Update note.

            //Log.d("newNOTE", "Note is being added");

            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(username, title, noteStr, date);
        }
        // Go back to the second activity.
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
    }
}