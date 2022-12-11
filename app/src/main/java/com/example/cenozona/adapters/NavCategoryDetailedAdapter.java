package com.example.cenozona.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cenozona.R;
import com.example.cenozona.activities.NavCategoryActivity;
import com.example.cenozona.models.NavCategoryDetailedModel;
import com.example.cenozona.models.NavCategoryModel;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {

    private Context context;
    private List<NavCategoryDetailedModel> navCategoryModelList;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> navCategoryModelList) {
        this.context = context;
        this.navCategoryModelList = navCategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(navCategoryModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(navCategoryModelList.get(position).getName());
        holder.price.setText(navCategoryModelList.get(position).getPrice());

    }



    @Override
    public int getItemCount() {
        return navCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, type, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_nav_img);
            name = itemView.findViewById(R.id.nav_cat_name);
            price = itemView.findViewById(R.id.price);


        }
    }
}
