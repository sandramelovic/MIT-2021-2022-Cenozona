package com.example.cenozona.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cenozona.MainActivity;
import com.example.cenozona.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText email, password;
    TextView signUp;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firestore=FirebaseFirestore.getInstance();

        signIn=findViewById(R.id.login_btn);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        signUp=findViewById(R.id.sing_up);
        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }


    private void loginUser() {

        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();


        if (TextUtils.isEmpty(userEmail)){

            Toast.makeText(this, "Unesi email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            Toast.makeText(this, "Unesi validan email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)){

            Toast.makeText(this, "Unesi lozinku!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length()<6){

            Toast.makeText(this, "Lozinka mora imati vise od 6 karaktera",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Uspesno ste se prijavili", Toast.LENGTH_SHORT).show();


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Neispravno unet email ili pozinka",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}