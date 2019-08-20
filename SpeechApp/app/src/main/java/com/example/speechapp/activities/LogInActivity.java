package com.example.speechapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



import com.example.speechapp.R;

public class LogInActivity extends AppCompatActivity {

 //   TextInputLayout inputEmail, inputPass;

    Button btnLogIn;
    TextInputLayout editText1, editText2;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
         btnLogIn = (Button) findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();




       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);



       btnLogIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String lEmail = editText1.getEditText().getText().toString().trim();
               String lPass = editText2.getEditText().getText().toString().trim();

              if (!TextUtils.isEmpty(lEmail) && !TextUtils.isEmpty(lPass)) {
                   logIn(lEmail, lPass);
              }

          }
       });

   }

    private void logIn(String email, String password){

       final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait...");
     progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                       progressDialog.dismiss();

                       if (task.isSuccessful()) {

                          Intent mainIntent = new Intent(LogInActivity.this,Homepage.class);
                           startActivity(mainIntent);
                       finish();

                          Toast.makeText(LogInActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                     } else {
                          Toast.makeText(LogInActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }

                 }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      super.onOptionsItemSelected(item);

       switch (item.getItemId()){
           case android.R.id.home:
             finish();
               break;
        }

     return true;
  }
}