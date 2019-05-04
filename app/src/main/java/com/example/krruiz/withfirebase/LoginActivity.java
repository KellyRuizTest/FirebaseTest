package com.example.krruiz.withfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krruiz.withfirebase.Model.Users;
import com.example.krruiz.withfirebase.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputNumber;
    private EditText InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private CheckBox checkBoxRememberme;

    private TextView Admin, NotAdming;

    private String parentDBname = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputNumber = findViewById(R.id.editNumber);
        InputPassword = findViewById(R.id.editPassword);
        LoginButton = findViewById(R.id.button);

        Admin = (TextView) findViewById(R.id.adminText);
        NotAdming = (TextView) findViewById(R.id.notAdminText);

        checkBoxRememberme = (CheckBox) findViewById(R.id.rememberMe);
        Paper.init(this);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginButton.setText("Login Admin");
                Admin.setVisibility(View.INVISIBLE);
                NotAdming.setVisibility(View.VISIBLE);
                parentDBname = "Admins";
            }
        });

        NotAdming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginButton.setText("Login");
                Admin.setVisibility(View.VISIBLE);
                NotAdming.setVisibility(View.INVISIBLE);
                parentDBname = "Users";
            }
        });
    }

    private void LoginUser() {

        String number = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (number.equals("")){

            Toast.makeText(this, "Please write your Phone number", Toast.LENGTH_SHORT);
        } else if (password.equals("")){

            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT);
        }else {

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(number, password);

        }


    }

    // change name parametrs
    private void AllowAccessToAccount(final String numberTT, final String passwordTT) {


        if (checkBoxRememberme.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey, numberTT);
            Paper.book().write(Prevalent.UserPasswordKey, passwordTT);

        }

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDBname).child(numberTT).exists()){

                    Users userData = dataSnapshot.child(parentDBname).child(numberTT).getValue(Users.class);
                    String phoneAux = dataSnapshot.child(parentDBname).child(numberTT).getKey();

                    //String phoneAux = userData.getPhonenumber();
                    String passwordAux = userData.getPassword();

                    System.out.println("=========================================");
                    System.out.println(phoneAux);
                    System.out.println(passwordAux);
                    System.out.println("=========================================");

                    if (phoneAux.equals(numberTT)){

                        if(passwordAux.equals(passwordTT)){

                            if (parentDBname.equals("Admins")){


                                Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent  = new Intent(getApplicationContext(), AdminCategoryActivity.class);
                                startActivity(intent);

                            }else if (parentDBname.equals("Users")){

                                Toast.makeText(getApplicationContext(), "Login IN sucessfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);

                                Prevalent.currentOnlineUser = userData;
                                startActivity(intent);

                            }

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
