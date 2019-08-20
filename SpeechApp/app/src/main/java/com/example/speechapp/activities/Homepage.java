package com.example.speechapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.speechapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {
    CardView chatCrdVw, callsCrdVw, alarmCrdVw, savenoteCrdVw;
   //Toolbar mToolbaar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    mAuth =FirebaseAuth.getInstance();


      //  mToolbaar=(Toolbar) findViewById(R.id.dots_toolbar);
      //  setSupportActionBar(mToolbaar);



        chatCrdVw= findViewById(R.id.chat_crdVw);
        chatCrdVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat= new Intent(Homepage.this,ChatActivity.class);
                startActivity(chat);
            }
        });

        callsCrdVw= findViewById(R.id.calls_crdVw);
        callsCrdVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calls= new Intent(Homepage.this,CallsActivity.class);
                startActivity(calls);
            }
        });

        alarmCrdVw= findViewById(R.id.alarm_crdVw);
        alarmCrdVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reminder= new Intent(Homepage.this, AlarmActivity.class);
                startActivity(reminder);
            }
        });

        savenoteCrdVw= findViewById(R.id.save_note_crdVw);
        savenoteCrdVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savenotes= new Intent(Homepage.this, SaveNoteActivity.class);
                startActivity(savenotes);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         if ((item.getItemId())== R.id.logout_button) {
             user_logout();



         }
         return true;
    }

    private void user_logout(){

        FirebaseAuth.getInstance().signOut();

     Intent intent = new Intent(Homepage.this,SelectiveActivity.class);
        startActivity(intent);
        finish();


    }
}
