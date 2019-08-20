package com.example.speechapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.List;
import com.example.speechapp.R;

public class CallsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    SinchClient sinchClient;
    Call call;

    ArrayList<UserModel> userModelArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);



        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        userModelArrayList= new ArrayList<>();

        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();

        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(firebaseUser.getUid())
                .applicationKey("ad2070be-af44-449d-842e-ad5c24fa3934")
                .applicationSecret("Ps/mib3XREi1qvE59r4CeA==")
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();


        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener()
        {

        });

        sinchClient.start();

        fetchAllUsers();
    }

    private void fetchAllUsers() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModelArrayList.clear();

                for (DataSnapshot dss:dataSnapshot.getChildren()){
                    UserModel userModel=dss.getValue(UserModel.class);

                    userModelArrayList.add(userModel);

                }

                AllUsersAdapter adapter = new AllUsersAdapter(CallsActivity.this,userModelArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "error: " +databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private class SinchCallListener implements CallListener{

        @Override
        public void onCallProgressing(Call call) {
            Toast.makeText(getApplicationContext(), "Ringing....", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEstablished(Call call) {
            Toast.makeText(getApplicationContext(), "Call Established", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCallEnded(Call endedCall) {
            Toast.makeText(getApplicationContext(), "Call Ended", Toast.LENGTH_SHORT).show();
            call = null;
            endedCall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, final Call incomingcall) {
            // later  open dialog for new incoming call

            AlertDialog alertDialog =new AlertDialog.Builder(CallsActivity.this).create();
            alertDialog.setTitle("CALLING");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "REJECT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    call.hangup();
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "PICK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    call= incomingcall;
                    call.answer();
                    call.addCallListener(new SinchCallListener());
                    Toast.makeText(getApplicationContext(), "Call Started", Toast.LENGTH_SHORT).show();

                }
            });

            alertDialog.show();

        }
    }

    public  void  callUser(UserModel userModel){

        if (call == null){
            call = sinchClient.getCallClient().callUser(userModel.getUserid());
            call.addCallListener(new SinchCallListener());

            openCallerDialog(call);
        }

    }

    private void openCallerDialog(final Call call) {

        AlertDialog alertDialogCall =new AlertDialog.Builder(CallsActivity.this).create();
        alertDialogCall.setTitle("Alert");
        alertDialogCall.setMessage("CALLING");
        alertDialogCall.setButton(AlertDialog.BUTTON_NEUTRAL, "HANG UP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                call.hangup();

            }
        });

        alertDialogCall.show();
    }
}
