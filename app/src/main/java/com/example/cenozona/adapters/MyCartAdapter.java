package com.example.cenozona.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.security.keystore.UserNotAuthenticatedException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cenozona.R;
import com.example.cenozona.models.MyCartModel;
import com.example.cenozona.models.NavCategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{


    Context context;
    List<MyCartModel> myCartModelsList;
    int totalPrice = 0;
    int totalPriceRoda = 0;
    int totalPriceMaxi = 0;
    int totalPriceLidl = 0;
    int totalPriceTempo = 0;
    int totalPriceDis = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public MyCartAdapter(Context context, List<MyCartModel> myCartModelsList) {
        this.context = context;
        this.myCartModelsList = myCartModelsList;
        firestore= FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(myCartModelsList.get(position).getProductName());
        holder.price.setText(myCartModelsList.get(position).getProductPrice());
        holder.date.setText(myCartModelsList.get(position).getCurrentDate());
        holder.time.setText(myCartModelsList.get(position).getCurrentTime());
        holder.quantity.setText(myCartModelsList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(myCartModelsList.get(position).getTotalPrice()));
        holder.totalPriceRoda.setText(String.valueOf(myCartModelsList.get(position).getTotalPriceRoda()));
        holder.totalPriceTempo.setText(String.valueOf(myCartModelsList.get(position).getTotalPriceTempo()));
        holder.totalPriceDis.setText(String.valueOf(myCartModelsList.get(position).getTotalPriceDis()));
        holder.totalPriceLidl.setText(String.valueOf(myCartModelsList.get(position).getTotalPriceLidl()));
        holder.totalPriceMaxi.setText(String.valueOf(myCartModelsList.get(position).getTotalPriceMaxi()));


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    AlertDialog deleteItemDialog = new AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("Da li ste sigurni da želite da uklonite ovaj proizvod iz korpe?")
                            .setIcon(R.drawable.delete)

                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                            .collection("AddToCart")
                                            .document(myCartModelsList.get(position).getDocumentId())
                                            .delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        myCartModelsList.remove(myCartModelsList.get(position));
                                                        notifyDataSetChanged();
                                                        Toast.makeText(context, "Proizvod obrisan iz korpe", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "Greska" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                    }

                                                }
                                            });
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("Otkaži", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            })
                            .show();
            }
        });

        //pass total amount to My Cart Fragment

         }



    @Override
    public int getItemCount() {
        return myCartModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

    TextView name,price,date,time,quantity,totalPrice,totalPriceMaxi,totalPriceLidl,totalPriceTempo,totalPriceDis,totalPriceRoda;
    ImageView deleteItem;

    public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            date=itemView.findViewById(R.id.current_date);
            time=itemView.findViewById(R.id.current_time);
            quantity=itemView.findViewById(R.id.total_quantity);
            totalPrice=itemView.findViewById(R.id.total_price);
            deleteItem=itemView.findViewById(R.id.delete);
            totalPriceRoda=itemView.findViewById(R.id.roda_total_price);
            totalPriceDis=itemView.findViewById(R.id.dis_total_price);
            totalPriceMaxi=itemView.findViewById(R.id.maxi_total_price);
            totalPriceLidl=itemView.findViewById(R.id.lidl_total_price);
            totalPriceTempo=itemView.findViewById(R.id.tempo_total_price);


    }
    }
}
