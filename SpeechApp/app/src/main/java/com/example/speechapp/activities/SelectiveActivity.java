package com.example.speechapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.speechapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SelectiveActivity extends AppCompatActivity {
    private Button btnReg, btnLog;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective);

        btnReg = (Button) findViewById(R.id.start_reg_btn);
        btnLog = (Button) findViewById(R.id.start_log_btn);

        fAuth = FirebaseAuth.getInstance();

        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register(){
        Intent regIntent = new Intent(SelectiveActivity.this, SignUpActivity.class);
        startActivity(regIntent);
    }

    private void login(){
        Intent logIntent = new Intent(SelectiveActivity.this, LogInActivity.class);
        startActivity(logIntent);
    }

    private void updateUI(){
        if (fAuth.getCurrentUser() != null){
            Log.i("StartActivity", "fAuth != null");
            Intent startIntent = new Intent(SelectiveActivity.this, Homepage.class);
            startActivity(startIntent);
            finish();
        } else {

            Log.i("StartActivity", "fAuth == null");
        }
    }

}