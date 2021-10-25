package com.example.lab5_milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    public static ArrayList<Note> notes = new ArrayList<>();
    TextView welcome_text;
    String usernameStr;
    //String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Grab the textview for the new activity and populate it with the user input.
        welcome_text = (TextView) findViewById(R.id.welcome_text);
        //Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        usernameStr = sharedPreferences.getString("username", null);
        welcome_text.setText("Welcome " + usernameStr + "!");

        // Get the SQLite instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // Load the notes.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(usernameStr);

        // Read the notes text.
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {

            Log.d("NOTE-DISPLAY", "Note is being displayed");


            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));


        }

        // Display the notes text using the ListView.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notes_list);
        listView.setAdapter(adapter);

        // Add onClick listener for each ListView item.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Initialize intent to take user to notes activity.
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                // Add the position of the item that was clicked.
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Get clicked menu item id.
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        int itemId = item.getItemId();
        if(itemId == R.id.addNote) {
            Intent intent = new Intent(this, NoteActivity.class);
            String username = sharedPreferences.getString("username", "");
            startActivity(intent);
        } else if(itemId == R.id.logout) {
            sharedPreferences.edit().remove("username").apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}