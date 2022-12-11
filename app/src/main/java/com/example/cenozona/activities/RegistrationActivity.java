package com.example.cenozona.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cenozona.R;
import com.example.cenozona.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    Button signUp;
    EditText name, email, password;
    TextView signIn;
    ProgressBar progressBar;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firestore = FirebaseFirestore.getInstance();

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        signUp=findViewById(R.id.login_btn);
        name=findViewById(R.id.name_reg);
        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);
        signIn=findViewById(R.id.sing_in);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createUser() {

        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if (TextUtils.isEmpty(userName)){

            Toast.makeText(this, "Unesi ime!",Toast.LENGTH_SHORT).show();
            return;
        }

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

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isComplete()) {

                    UserModel userModel = new UserModel(userName, userEmail, userPassword);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .setPhotoUri(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.userprofilephoto))
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", userName);
                                    }
                                }
                            });



                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Uspesno ste se registrovali!",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Greska pri registraciji!" + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}