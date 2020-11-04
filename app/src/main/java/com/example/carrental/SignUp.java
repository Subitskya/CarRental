package com.example.carrental;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private EditText emailET, passwordET1, passwordET2;
    private Button SignUpBtn;
    private TextView SignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siggnup);
        fAuth =FirebaseAuth.getInstance();
        emailET = findViewById(R.id.email);
        passwordET1 = findViewById(R.id.password1);
        passwordET2 = findViewById(R.id.password2);
        SignUpBtn = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        SignIn =  findViewById(R.id.sign);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Sign_In.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register(){
        String email = emailET.getText().toString();
        String password1 = passwordET1.getText().toString();
        String password2 = passwordET2.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError("Enter your Email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordET1.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordET2.setError("Confirm your password");
            return;
        }
        else if(!password1.equals(password2)){
            passwordET2.setError("Password didn't matched");
            return;
        }
        else if(password1.length()<8){
            passwordET1.setError("Your password must be more than 8 characters.");
            return;
        }
        else if(!isValidEmail(email)){
            emailET.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        fAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUp.this,"SignUp failed!!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
