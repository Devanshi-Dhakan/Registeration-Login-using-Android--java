package com.example.registeration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,emaill,pass,cmpass;
    Button Signin;
    TextView txt;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.Fname);
        emaill=(EditText)findViewById(R.id.Emailid);
        pass=(EditText)findViewById(R.id.Password);
        cmpass=(EditText)findViewById(R.id.confirmpass);
        Signin=(Button)findViewById(R.id.button);
        txt=(TextView)findViewById(R.id.textView);

        databaseReference=firebaseDatabase.getInstance().getReference("Student");

        FAuth=FirebaseAuth.getInstance();

        if(FAuth.getCurrentUser() != null)
        {
            Intent go=new Intent(MainActivity.this,Register.class);
            startActivity(go);
            finish();
        }

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emaill.getText().toString().trim();
                final String password=pass.getText().toString().trim();
                final String fullname=name.getText().toString().trim();
                final String confirmpass=cmpass.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    emaill.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    pass.setError("password is required");
                    return;
                }
                if (email.length() < 6)
                {
                    pass.setError("password should be more than 6 letters");
                    return;
                }



                FAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            student information=new student(

                                    fullname,
                                    email,
                                    password,
                                    confirmpass

                            );

                            FirebaseDatabase.getInstance().getReference("Student")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(MainActivity.this, "registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Register.class));


                                }
                            });
                        }
                        else {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent login=new Intent(MainActivity.this,Login.class);
                    startActivity(login);
                    finish();
                }
            });

    }
}
