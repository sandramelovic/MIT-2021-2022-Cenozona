package com.example.cenozona.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cenozona.R;
import com.example.cenozona.activities.DetailedActivity;
import com.example.cenozona.activities.ViewAllActivity;
import com.example.cenozona.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder>{

    private Context context;
    private List<ViewAllModel> viewAllModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;



    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
        firestore= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.rating.setText(viewAllModelList.get(position).getRating());
        holder.price.setText(viewAllModelList.get(position).getPrice() + "/kg");

        if (viewAllModelList.get(position).getType().equals("eggs")){
            holder.price.setText(viewAllModelList.get(position).getPrice() + "/komad");
        }
        if (viewAllModelList.get(position).getType().equals("milk")){
            holder.price.setText(viewAllModelList.get(position).getPrice() + "/l");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", viewAllModelList.get(position));
                context.startActivity(intent);
            }
        });

        holder.deleteProduct.setVisibility(View.INVISIBLE);
        String uid = auth.getUid();
        Log.i("uid", uid);
        if (String.valueOf(uid).equals("mdnV1ZSpzibSujxENrF3vUFoBTY2")){
            holder.deleteProduct.setVisibility(View.VISIBLE);
        }
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog deleteDialog = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Da li ste sigurni da želite da obrišete ovaj proizvod?")
                        .setIcon(R.drawable.delete)

                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                firestore.collection("AllProducts").document(viewAllModelList.get(position).getDocumentId()).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    viewAllModelList.remove(viewAllModelList.get(position));
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "Proizvod obrisan", Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, deleteProduct;
        TextView name,description,rating, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_des);
            rating = itemView.findViewById(R.id.view_rating);
            price = itemView.findViewById(R.id.view_price);
            deleteProduct=itemView.findViewById(R.id.delete_product);

        }
    }
}
