package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_In extends AppCompatActivity {
    private EditText emailET, passwordET1, passwordET2;
    private Button SignInBtn;
    private TextView SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);
        fAuth =FirebaseAuth.getInstance();
        emailET = findViewById(R.id.email);
        passwordET1 = findViewById(R.id.password1);
        passwordET2 = findViewById(R.id.password2);
        SignInBtn = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        SignUp =  findViewById(R.id.signup);
        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_In.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email = emailET.getText().toString();
        String password = passwordET1.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError("Enter your Email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            passwordET1.setError("Enter your password");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Sign_In.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Sign_In.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Sign_In.this,"Sign In failed!!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
