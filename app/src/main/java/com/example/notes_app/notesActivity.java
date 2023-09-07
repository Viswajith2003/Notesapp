package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class notesActivity extends AppCompatActivity {

    FloatingActionButton mcreatenotsfab;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mcreatenotsfab =findViewById(R.id.createnotefab);
        firebaseAuth=FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("All Notes");


        mcreatenotsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(notesActivity.this,createnotes.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId(); // Get the item ID once

        if (itemId == R.id.logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(notesActivity.this, MainActivity.class)); // Assuming MainActivity is your login activity
            return true;
        }
        return true;
    }
}