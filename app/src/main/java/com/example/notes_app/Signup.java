package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class Signup extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText mail=findViewById(R.id.signupmail);
        EditText pass=findViewById(R.id.signuppwd);
        Button signup=findViewById(R.id.signupBtn);
        TextView mgotologin=findViewById(R.id.gotologin);
        firebaseAuth=FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                String pwd = pass.getText().toString().trim();

                if (email.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(Signup.this, "All Field are Required", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 7) {
                    Toast.makeText(Signup.this, "Password should be greater than 7 digits", Toast.LENGTH_SHORT).show();
                } else {
                    // Registered the user to firebase;
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Show registration success message
                                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is send,Verify and Log In Again",Toast.LENGTH_SHORT).show();
                     firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Signup.this,MainActivity.class));
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Failed To Send Verification Email",Toast.LENGTH_SHORT).show();
        }

    }
}