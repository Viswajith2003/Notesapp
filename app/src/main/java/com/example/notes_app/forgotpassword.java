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
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        EditText mforgotpwd =findViewById(R.id.forgotpwd);
        Button mrecoverbtn =findViewById(R.id.recoverbtn);
        TextView mgobacktologin=findViewById(R.id.gobacktologin);
        firebaseAuth=FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(forgotpassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mrecoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=mforgotpwd.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter your mail first", Toast.LENGTH_SHORT).show();
                }
                else {
                    //We have to send pwd recover mail.
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Show registration success message
                                Toast.makeText(getApplicationContext(), "Mail send, You can recover your password using mail", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotpassword.this,MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Email is wrong or Account not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}