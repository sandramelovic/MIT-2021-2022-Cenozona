package com.example.cenozona.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cenozona.R;
import com.example.cenozona.adapters.NavCategoryAdapter;
import com.example.cenozona.adapters.NavCategoryDetailedAdapter;
import com.example.cenozona.models.NavCategoryDetailedModel;
import com.example.cenozona.models.NavCategoryModel;
import com.example.cenozona.models.PopularModel;
import com.example.cenozona.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        db= FirebaseFirestore.getInstance();

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.nav_cat_det_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);

        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);

        //Getting Fruit
        if (type != null && type.equalsIgnoreCase("fruit")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        //Getting Frozen
        if (type != null && type.equalsIgnoreCase("frozen")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "frozen").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        //Getting Meat
        if (type != null && type.equalsIgnoreCase("meat")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "meat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                }
            });
        }

        //Getting Bakery
        if (type != null && type.equalsIgnoreCase("bakery")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "bakery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        //Getting Drinks
        if (type != null && type.equalsIgnoreCase("drinks")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "drinks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
    }
}