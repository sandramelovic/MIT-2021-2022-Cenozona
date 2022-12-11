package com.example.cenozona.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cenozona.MainActivity;
import com.example.cenozona.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        if (auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class ));
            Toast.makeText(WelcomeActivity.this, "Molimo sacekajte, vec ste se prijavili!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void login(View view) {

        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }

    public void registration(View view) {

        startActivity(new Intent(WelcomeActivity.this, RegistrationActivity.class));
    }

    public void enter(View view) {

        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
    }
}