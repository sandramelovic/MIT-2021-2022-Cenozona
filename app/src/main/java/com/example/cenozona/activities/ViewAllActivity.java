package com.example.cenozona.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cenozona.R;
import com.example.cenozona.adapters.ViewAllAdapter;
import com.example.cenozona.models.MyCartModel;
import com.example.cenozona.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {


    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    List<ViewAllModel> viewAllModelList;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    Toolbar toolbar;
    ProgressBar progressBar;
    ImageView add_new_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        add_new_product = findViewById(R.id.add_new_product);
        add_new_product.setVisibility(View.INVISIBLE);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);


        String uid = firebaseAuth.getUid();
        Log.i("uid", uid);
        if (String.valueOf(uid).equals("mdnV1ZSpzibSujxENrF3vUFoBTY2")){
            add_new_product.setVisibility(View.VISIBLE);
        }
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);

        //Getting Fruits
        if (type != null && type.equalsIgnoreCase("fruits")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fruits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        String documentId = documentSnapshot.getId();
                        viewAllModel.setDocumentId(documentId);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

            //Getting Vegetables
            if (type != null && type.equalsIgnoreCase("vegetables")) {
                firestore.collection("AllProducts").whereEqualTo("type", "vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                            String documentId = documentSnapshot.getId();
                            viewAllModel.setDocumentId(documentId);
                            viewAllModelList.add(viewAllModel);
                            viewAllAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

        //Getting Fish
        if (type != null && type.equalsIgnoreCase("fish")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        String documentId = documentSnapshot.getId();
                        viewAllModel.setDocumentId(documentId);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        //Getting Eggs
        if (type != null && type.equalsIgnoreCase("eggs")) {
            firestore.collection("AllProducts").whereEqualTo("type", "eggs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        String documentId = documentSnapshot.getId();
                        viewAllModel.setDocumentId(documentId);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        //Getting Milk
        if (type != null && type.equalsIgnoreCase("milk")) {
            firestore.collection("AllProducts").whereEqualTo("type", "milk").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        String documentId = documentSnapshot.getId();
                        viewAllModel.setDocumentId(documentId);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                }
            });
        }

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder addDialog = new AlertDialog.Builder(ViewAllActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View addDialogView = inflater.inflate(R.layout.add_product_dialog, null);

                addDialog.setView(addDialogView);
                addDialog.setTitle("Dodavanje novog proizvoda");

                EditText addName = addDialogView.findViewById(R.id.addName_dialog);
                EditText addType = addDialogView.findViewById(R.id.addType_dialog);
                EditText addPhoto = addDialogView.findViewById(R.id.addPhoto_dialog);
                addPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 33);
                    }
                });
                EditText addRating = addDialogView.findViewById(R.id.addRating_dialog);
                EditText addText = addDialogView.findViewById(R.id.addText_dialog);
                EditText addDis = addDialogView.findViewById(R.id.dis_add_dialog);
                EditText addMaxi = addDialogView.findViewById(R.id.maxi_add_dialog);
                EditText addRoda = addDialogView.findViewById(R.id.roda_add_dialog);
                EditText addTempo = addDialogView.findViewById(R.id.tempo_add_dialog);
                EditText addLidl = addDialogView.findViewById(R.id.lidl_add_dialog);
                Button addButton = addDialogView.findViewById(R.id.add_new_product_dialog_button);

                addDialog.show();


                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final HashMap<String,Object> cartMap = new HashMap<>();

                        if (addName.length() != 0
                                && addType.length() != 0
                                && addRoda.length() != 0
                                && addTempo.length() != 0
                                && addLidl.length() != 0
                                && addMaxi.length() != 0
                                && addDis.length() != 0
                                && addText.length() != 0
                                && addRating.length() != 0) {
                            cartMap.put("name", addName.getText().toString());
                            cartMap.put("type", addType.getText().toString());
                            cartMap.put("img_url", addPhoto.getText().toString());
                            cartMap.put("rating", addRating.getText().toString());
                            cartMap.put("description", addText.getText().toString());
                            cartMap.put("disPrice", Integer.parseInt(addDis.getText().toString()));
                            cartMap.put("maxiPrice", Integer.parseInt(addMaxi.getText().toString()));
                            cartMap.put("rodaPrice", Integer.parseInt(addRoda.getText().toString()));
                            cartMap.put("tempoPrice", Integer.parseInt(addTempo.getText().toString()));
                            cartMap.put("lidlPrice", Integer.parseInt(addLidl.getText().toString()));

                            firestore.collection("AllProducts").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    Toast.makeText(ViewAllActivity.this, "Porizvod dodat", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                        if (addName.length() == 0) {
                            addName.setError("Ovo polje je obavezno");
                        }
                        if (addType.length() == 0) {
                            addType.setError("Ovo polje je obavezno");
                        }
                        if (addRating.length() == 0) {
                            addRating.setError("Ovo polje je obavezno");
                        }
                        if (addText.length() == 0) {
                            addText.setError("Ovo polje je obavezno");
                        }
                        if (addDis.length() == 0) {
                            addDis.setError("Ovo polje je obavezno");
                        }
                        if (addMaxi.length() == 0) {
                            addMaxi.setError("Ovo polje je obavezno");
                        }
                        if (addLidl.length() == 0) {
                            addLidl.setError("Ovo polje je obavezno");
                        }
                        if (addRoda.length() == 0) {
                            addRoda.setError("Ovo polje je obavezno");
                        }
                        if (addTempo.length() == 0) {
                            addTempo.setError("Ovo polje je obavezno");
                        }

                    }
                });
            }

        });

        }

    }