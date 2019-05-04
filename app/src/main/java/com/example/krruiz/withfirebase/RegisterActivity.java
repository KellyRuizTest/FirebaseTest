package com.example.krruiz.withfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccount;
    private EditText InputName;
    private EditText InputNumber;
    private EditText InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccount = findViewById(R.id.createAccount);
        InputName = findViewById(R.id.editName);
        InputNumber = findViewById(R.id.editPhone);
        InputPassword = findViewById(R.id.editPassword);
        loadingBar = new ProgressDialog(this);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAccount();

            }
        });

    }

    private void CreateAccount() {

        String name = InputName.getText().toString();
        String number = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (name.equals("")){

            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT);
        }else if (number.equals("")){

            Toast.makeText(this, "Please write your Phone number", Toast.LENGTH_SHORT);
        } else if (password.equals("")){

            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT);
        }else {

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validatePhoneNumber(name, number, password);
        }
    }

    private void validatePhoneNumber(final String name, final String number, final String password) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(number).exists())){

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", number);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    rootRef.child("Users").child(number).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Congratulation your account was created sucessfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();

                                Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, " Network Error: Please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{

                    Toast.makeText(getApplicationContext(), "This phone number already exists", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
