package com.example.registeration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email,pass;
    Button Signout;
    TextView txt;
    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.Lemail);
        pass=(EditText)findViewById(R.id.Lpassword);
        Signout=(Button)findViewById(R.id.button4);
        txt=(TextView)findViewById(R.id.textView3);

        FAuth=FirebaseAuth.getInstance();

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em = email.getText().toString().trim();
                String pwd = pass.getText().toString().trim();

                if (TextUtils.isEmpty(em)) {
                    email.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    pass.setError("password is required");
                    return;
                }
                if (pwd.length() < 6) {
                    pass.setError("password should be more than 6 letters");
                    return;

                }


                FAuth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "user Logged in", Toast.LENGTH_SHORT).show();
                            Intent z=new Intent(Login.this,Register.class);
                            startActivity(z);
                        }
                        else
                            {
                            Toast.makeText(Login.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Register=new Intent(Login.this,MainActivity.class);
                startActivity(Register);
            }
        });



    }
}
