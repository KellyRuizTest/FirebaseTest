package com.example.krruiz.withfirebase;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krruiz.withfirebase.Model.Users;
import com.example.krruiz.withfirebase.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    private Button SignUp;
    private Button LogIn;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignUp = (Button) findViewById(R.id.signupButton);
        LogIn = (Button) findViewById(R.id.loginButton);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });


        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);


        if (UserPhoneKey != "" && UserPasswordKey != "") {

            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                System.out.println("USER: " + UserPhoneKey);
                System.out.println("PW: " + UserPasswordKey);

                AllowAccess(UserPhoneKey, UserPasswordKey);

            } else {
                System.out.println("USER: " + UserPhoneKey);
                System.out.println("PW: " + UserPasswordKey);

            }

        }
    }

    private void AllowAccess(final String number, final String password) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(number).exists()){

                    Users userData = dataSnapshot.child("Users").child(number).getValue(Users.class);
                    String phoneAux = dataSnapshot.child("Users").child(number).getKey();

                    String passwordAux = userData.getPassword();

                    if (phoneAux.equals(number)){

                        if(passwordAux.equals(password)){

                            Toast.makeText(MainActivity.this, "Login IN sucessfully", Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                            Intent intent  = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = userData;
                            startActivity(intent);

                        }else {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Password Incorrect", Toast.LENGTH_LONG).show();
                        }
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Incorrect ID", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
