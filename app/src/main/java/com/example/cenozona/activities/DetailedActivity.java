package com.example.cenozona.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cenozona.MainActivity;
import com.example.cenozona.PlacedOrderActivity;
import com.example.cenozona.R;
import com.example.cenozona.models.NavCategoryDetailedModel;
import com.example.cenozona.models.UserModel;
import com.example.cenozona.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity =1;
    int totalPrice = 0;
    int totalPriceRoda = 0;
    int totalPriceMaxi = 0;
    int totalPriceLidl = 0;
    int totalPriceTempo = 0;
    int totalPriceDis = 0;
    int newPrice;

    List<Integer> list = new ArrayList<Integer>();
    ImageView detailedImg, minPriceImg;
    TextView price, rating, description,rodaPrice,maxiPrice,disPrice,lidlPrice,tempoPrice;
    Button addToCart;
    ImageView addItem,removeItem, notifyDis, notifyTempo, notifyRoda, notifyLidl, notifyMaxi, updateDetailedText;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseDatabase database;

    ViewAllModel viewAllModel = null;
    private List<ViewAllModel> viewAllModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("AllProducts");


        Log.d(firebaseAuth.getUid(), firebaseAuth.getUid());



        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel)
        {
            viewAllModel = (ViewAllModel) object;
        }

        quantity = findViewById(R.id.quantity);

        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_desc);
        rodaPrice = findViewById(R.id.roda_price);
        maxiPrice = findViewById(R.id.maxi_price);
        lidlPrice = findViewById(R.id.lidl_price);
        tempoPrice = findViewById(R.id.tempo_price);
        disPrice = findViewById(R.id.dis_price);
        minPriceImg= findViewById(R.id.min_price_img);
        updateDetailedText = findViewById(R.id.updateDetailedText);
        updateDetailedText.setVisibility(View.INVISIBLE);

        String uid = reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
        Log.i("uid", uid);
        if (String.valueOf(uid).equals("mdnV1ZSpzibSujxENrF3vUFoBTY2")){
            updateDetailedText.setVisibility(View.VISIBLE);
        }

        if (viewAllModel != null)
        {


            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            rodaPrice.setText(viewAllModel.getRodaPrice() +"RSD " + "/kg");
            maxiPrice.setText(viewAllModel.getMaxiPrice() +"RSD " + "/kg");
            lidlPrice.setText(viewAllModel.getLidlPrice() +"RSD " + "/kg");
            tempoPrice.setText(viewAllModel.getTempoPrice() +"RSD " + "/kg");
            disPrice.setText(viewAllModel.getDisPrice() +"RSD " + "/kg");


            list.add(viewAllModel.getRodaPrice());
            list.add(viewAllModel.getMaxiPrice());
            list.add(viewAllModel.getLidlPrice());
            list.add(viewAllModel.getTempoPrice());
            list.add(viewAllModel.getDisPrice());
            Collections.sort(list);

            viewAllModel.setPrice(list.get(0));
            final Map<String, Object> setPriceDatabase = new HashMap<>();
            setPriceDatabase.put("price", list.get(0));

            String path = viewAllModel.getDocumentId();
            firestore.collection("AllProducts").document(path).update(setPriceDatabase);
            price.setText(viewAllModel.getPrice() +"RSD " + "/kg");

            if (list.get(0)==viewAllModel.getDisPrice()){

                minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.dis));

            } else if (list.get(0)==viewAllModel.getRodaPrice()){

                minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.roda));

            } else if (list.get(0)==viewAllModel.getTempoPrice()){

                minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.tempo));

            } else if (list.get(0)==viewAllModel.getLidlPrice()){

                minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.lidl));

            } else if (list.get(0)==viewAllModel.getMaxiPrice()){

                minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.maxi));

            }

            totalPrice = viewAllModel.getPrice() * totalQuantity;
            totalPriceRoda = viewAllModel.getRodaPrice() * totalQuantity;
            totalPriceDis = viewAllModel.getDisPrice() * totalQuantity;
            totalPriceTempo = viewAllModel.getTempoPrice() * totalQuantity;
            totalPriceMaxi = viewAllModel.getMaxiPrice() * totalQuantity;
            totalPriceLidl = viewAllModel.getLidlPrice() * totalQuantity;

            if (viewAllModel.getType().equals("eggs")){
                price.setText("Cena: " + viewAllModel.getPrice() + "RSD " +"/komad");
                rodaPrice.setText(viewAllModel.getRodaPrice() +"RSD " + "/komad");
                maxiPrice.setText(viewAllModel.getMaxiPrice() +"RSD " + "/komad");
                lidlPrice.setText(viewAllModel.getLidlPrice() +"RSD " + "/komad");
                tempoPrice.setText(viewAllModel.getTempoPrice() +"RSD " + "/komad");
                disPrice.setText(viewAllModel.getDisPrice() +"RSD " + "/komad");
                totalPrice = viewAllModel.getPrice() * totalQuantity;

            }
            if (viewAllModel.getType().equals("milk")){
                price.setText("Cena: " + viewAllModel.getPrice() + "RSD " +"/l");
                rodaPrice.setText(viewAllModel.getRodaPrice() +"RSD " + "/l");
                maxiPrice.setText(viewAllModel.getMaxiPrice() +"RSD " + "/l");
                lidlPrice.setText(viewAllModel.getLidlPrice() +"RSD " + "/l");
                tempoPrice.setText(viewAllModel.getTempoPrice() +"RSD " + "/l");
                disPrice.setText(viewAllModel.getDisPrice() +"RSD " + "/l");
                totalPrice = viewAllModel.getPrice() * totalQuantity;

            }
        }

        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                    totalPriceRoda = viewAllModel.getRodaPrice() * totalQuantity;
                    totalPriceDis = viewAllModel.getDisPrice() * totalQuantity;
                    totalPriceTempo = viewAllModel.getTempoPrice() * totalQuantity;
                    totalPriceMaxi = viewAllModel.getMaxiPrice() * totalQuantity;
                    totalPriceLidl = viewAllModel.getLidlPrice() * totalQuantity;
                }

            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                    totalPriceRoda = viewAllModel.getRodaPrice() * totalQuantity;
                    totalPriceDis = viewAllModel.getDisPrice() * totalQuantity;
                    totalPriceTempo = viewAllModel.getTempoPrice() * totalQuantity;
                    totalPriceMaxi = viewAllModel.getMaxiPrice() * totalQuantity;
                    totalPriceLidl = viewAllModel.getLidlPrice() * totalQuantity;

                }

            }
        });

        updateDetailedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String path = viewAllModel.getDocumentId();

                AlertDialog.Builder updateDialog = new AlertDialog.Builder(DetailedActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View updateDialogView = inflater.inflate(R.layout.update_dialog_detailed_text, null);

                updateDialog.setView(updateDialogView);
                updateDialog.setTitle("Izmena proizvoda");
                EditText updateText = updateDialogView.findViewById(R.id.updateDetailedText_dialog);
                EditText updateRating = updateDialogView.findViewById(R.id.updateRating_dialog);
                EditText updateDis = updateDialogView.findViewById(R.id.dis_update_dialog);
                EditText updateMaxi = updateDialogView.findViewById(R.id.maxi_update_dialog);
                EditText updateRoda = updateDialogView.findViewById(R.id.roda_update_dialog);
                EditText updateTempo = updateDialogView.findViewById(R.id.tempo_update_dialog);
                EditText updateLidl = updateDialogView.findViewById(R.id.lidl_update_dialog);
                Button btnUpdate=updateDialogView.findViewById(R.id.updateDetailedText_dialog_button);

                updateText.setText(description.getText().toString());
                updateRating.setText(viewAllModel.getRating());
                updateDis.setText(String.valueOf(viewAllModel.getDisPrice()));
                updateMaxi.setText(String.valueOf(viewAllModel.getMaxiPrice()));
                updateRoda.setText(String.valueOf(viewAllModel.getRodaPrice()));
                updateTempo.setText(String.valueOf(viewAllModel.getTempoPrice()));
                updateLidl.setText(String.valueOf(viewAllModel.getLidlPrice()));

                updateDialog.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            String text=updateText.getText().toString();
                            int dis=Integer.parseInt(updateDis.getText().toString());
                            int roda=Integer.parseInt(updateRoda.getText().toString());
                            int tempo=Integer.parseInt(updateTempo.getText().toString());
                            int lidl=Integer.parseInt(updateLidl.getText().toString());
                            int maxi=Integer.parseInt(updateMaxi.getText().toString());
                            String rate=updateRating.getText().toString();

                            viewAllModel.setDescription(text);
                            viewAllModel.setRating(rate);
                            viewAllModel.setLidlPrice(lidl);
                            viewAllModel.setTempoPrice(tempo);
                            viewAllModel.setDisPrice(dis);
                            viewAllModel.setMaxiPrice(maxi);
                            viewAllModel.setRodaPrice(roda);


                            final Map<String, Object> updateProduct = new HashMap<>();
                            updateProduct.put("description", text);
                            updateProduct.put("rating", rate);
                            updateProduct.put("disPrice", dis);
                            updateProduct.put("maxiPrice", maxi);
                            updateProduct.put("rodaPrice", roda);
                            updateProduct.put("tempoPrice", tempo);
                            updateProduct.put("lidlPrice", lidl);

                            firestore.collection("AllProducts").document(path).update(updateProduct)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(DetailedActivity.this, "Proizvod izmenjen", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                    });

                    }

                });

            }

        });


        notifyDis = findViewById(R.id.notify_dis);
        notifyDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOldPrice("disPrice");
                Toast.makeText(DetailedActivity.this, "Obavesticemo Vas kada " + viewAllModel.getName() + " bude na akciji u prodavnici DIS", Toast.LENGTH_SHORT).show();

            }
        });

        notifyTempo = findViewById(R.id.notify_tempo);
        notifyTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOldPrice("tempoPrice");
                Toast.makeText(DetailedActivity.this, "Obavesticemo Vas kada " + viewAllModel.getName() + " bude na akciji u prodavnici TEMPO", Toast.LENGTH_SHORT).show();


            }
        });
        notifyMaxi = findViewById(R.id.notify_maxi);
        notifyMaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOldPrice("maxiPrice");
                Toast.makeText(DetailedActivity.this, "Obavesticemo Vas kada " + viewAllModel.getName() + " bude na akciji u prodavnici MAXI", Toast.LENGTH_SHORT).show();


            }
        });
        notifyRoda = findViewById(R.id.notify_roda);
        notifyRoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOldPrice("rodaPrice");
                Toast.makeText(DetailedActivity.this, "Obavesticemo Vas kada " + viewAllModel.getName() + " bude na akciji u prodavnici RODA", Toast.LENGTH_SHORT).show();


            }
        });
        notifyLidl = findViewById(R.id.notify_lidl);
        notifyLidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOldPrice("lidlPrice");
                Toast.makeText(DetailedActivity.this, "Obavesticemo Vas kada " + viewAllModel.getName() + " bude na akciji u prodavnici LIDL", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void getOldPrice(String store){
        DocumentReference docRef = firestore.collection("AllProducts").document(viewAllModel.getDocumentId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (store != null) {
                        DocumentSnapshot document = task.getResult();
                        if (document.get(store) != null) {
                            Log.d("TAG", String.valueOf(document.get(store)));
                            listenUpdates(Integer.parseInt(String.valueOf(document.get(store))), store);

                            Log.d(store, "DocumentSnapshot data: " + document.getData() + store + viewAllModel.getLidlPrice());
                        }
                    }
                }
            }
        });
    }


    private void listenUpdates(int oldPrice, String namePrice) {

        String path= viewAllModel.getDocumentId();

        final DocumentReference docRef = firestore.collection("AllProducts").document(path);
        firestore.collection("AllProducts").document(path)
                .addSnapshotListener(
                        new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                              if (value.get(namePrice) != null) {
                                      if (oldPrice > Integer.parseInt(String.valueOf(value.get(namePrice)))) {
                                          if (namePrice.equals("disPrice")) {
                                              sendNotification("Dis");
                                          } else if (namePrice.equals("rodaPrice")) {
                                              sendNotification("Roda");
                                          } else if (namePrice.equals("tempoPrice")) {
                                              sendNotification("Tempo");
                                          } else if (namePrice.equals("lidlPrice")) {
                                              sendNotification("Lidl");
                                          } else if (namePrice.equals("maxiPrice")) {
                                              sendNotification("Maxi");
                                          }

                                      }
                              }
                            }
                        });
    }


    private void sendNotification(String store) {

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle("Akcija!");
        builder.setContentText(viewAllModel.getName() + " je sada na akciji u prodavnici " + store + ". Otvori aplikaciju i pronadji jos proizvoda koji ti odgovaraju");
        builder.setSmallIcon(R.drawable.notification);
        builder.setAutoCancel(true);

        Notification ntf = builder.build();

        Intent openNotification = new Intent(getApplicationContext(), MainActivity.class);

        openNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intentBack = PendingIntent.getActivity(getApplicationContext(), 0, openNotification, 0);
        builder.setContentIntent(intentBack);

        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(1, ntf);

    }

    private void addedToCart() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        list.add(viewAllModel.getRodaPrice());
        list.add(viewAllModel.getMaxiPrice());
        list.add(viewAllModel.getLidlPrice());
        list.add(viewAllModel.getTempoPrice());
        list.add(viewAllModel.getDisPrice());
        Collections.sort(list);

        viewAllModel.setPrice(list.get(0));

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName", viewAllModel.getName());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);
        cartMap.put("totalPriceRoda", totalPriceRoda);
        cartMap.put("totalPriceMaxi", totalPriceMaxi);
        cartMap.put("totalPriceDis", totalPriceDis);
        cartMap.put("totalPriceTempo", totalPriceTempo);
        cartMap.put("totalPriceLidl", totalPriceLidl);


        firestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                Toast.makeText(DetailedActivity.this, "Porizvod dodat u korpu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }








}