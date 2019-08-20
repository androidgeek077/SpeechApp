package com.example.speechapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SignUpActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    String firstname, entermail, secondname, enterpassword, enterconfirmpassword, enterphonenumber, enteraddress;

    EditText Firstname, Secondname, EnterPassword, ConfirmPassword, EnterAddress, EnterPhoneNumber, EnterMail;
    Button signUpBtn;
    private LinearLayout mRootView;
    RadioGroup Gendersignup;
    RadioButton SelectRB;
    TextView alreadylogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Registeration");
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        mRootView = findViewById(R.id.my_root);
        Firstname = findViewById(R.id.Firstname_ET);
        Secondname = findViewById(R.id.Secondname_ET);
        EnterMail = findViewById(R.id.Mail_ET);
        EnterPassword = findViewById(R.id.Password_ET);
        ConfirmPassword = findViewById(R.id.ConfirmPassword_ET);
        EnterPhoneNumber = findViewById(R.id.Phonenumber_ET);
        EnterAddress = findViewById(R.id.Address_ET);signUpBtn = findViewById(R.id.signup_btn);
        Gendersignup = findViewById(R.id.genderSignup);

        alreadylogin = findViewById(R.id.AlreadyLogin);
        alreadylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getStrings();
                registerUser();

//                if (firstname.isEmpty()) {
//                    Firstname.setError("Enter First name");
//                    Firstname.requestFocus();
//                } else if (secondname.isEmpty()) {
//                    Secondname.setError("Enter second name");
//                    Secondname.requestFocus();
//                } else if (entermail.isEmpty()) {
//                    EnterMail.setError("Enter Mail");
//                    EnterMail.requestFocus();
//                } else if (enterpassword.isEmpty()) {
//                    EnterPassword.setError("Enter Password");
//                    EnterPassword.requestFocus();
//                } else if (enterconfirmpassword.isEmpty()) {
//                    ConfirmPassword.setError("Enter Confirm Password");
//                    ConfirmPassword.requestFocus();
//                } else if (enterphonenumber.isEmpty() ) {
//                    EnterPhoneNumber.setError("Enter Phone Number");
//                    EnterPhoneNumber.requestFocus();
//                }
//             else if (enterphonenumber.length()<11) {
//                EnterPhoneNumber.setError("Enter Valid Phone Number");
//                EnterPhoneNumber.requestFocus();
//            }
//                else if (enteraddress.isEmpty()) {
//                    EnterAddress.setError("Enter Address");
//                    EnterAddress.requestFocus();
//                } else {
//                    loading.setVisibility(View.VISIBLE);
//                    loading.start();
//                    registerUser();
//                }
            }
        });
    }

    private void registerUser() {
        String enterconfirmpassword = ConfirmPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int id = Gendersignup.getCheckedRadioButtonId();


        if (enterpassword.equals(enterconfirmpassword)) {
            if (entermail.matches(emailPattern)) {

                firebaseAuth.createUserWithEmailAndPassword(entermail, enterpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            User model = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), firstname, "", entermail, null, null);
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(model)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                                            startActivity(i);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            //startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                EnterMail.setError("Enter Valid Email ");
                EnterMail.requestFocus();
            }
        } else {
            Toast.makeText(SignUpActivity.this, "Password does't Match", Toast.LENGTH_SHORT).show();
            ConfirmPassword.requestFocus();
        }

    }


    private void getStrings() {
        firstname = Firstname.getText().toString();
        secondname = Secondname.getText().toString();
        entermail = EnterMail.getText().toString().trim();
        enterpassword = EnterPassword.getText().toString();
        enterconfirmpassword = ConfirmPassword.getText().toString().trim();
        enterphonenumber = EnterPhoneNumber.getText().toString().trim();
        enteraddress = EnterAddress.getText().toString().trim();
    }
}
