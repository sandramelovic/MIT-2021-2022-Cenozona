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
import com.example.cenozona.activities.ViewAllActivity;
import com.example.cenozona.models.HomeCategory;
import com.example.cenozona.models.UserModel;
import com.example.cenozona.ui.profile.ProfileFragment;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    Context context;
    List<UserModel> userModelList;

    public ProfileAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(userModelList.get(position).getProfileImg()).into(holder.profileImg);
        holder.profileName.setText(userModelList.get(position).getName());
        holder.profileEmail.setText(userModelList.get(position).getEmail());
        holder.profileNumber.setText(userModelList.get(position).getNumber());
        holder.profileAddress.setText(userModelList.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImg;
        TextView profileName;
        TextView profileEmail;
        TextView profileNumber;
        TextView profileAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImg = itemView.findViewById(R.id.profile_img);
            profileName = itemView.findViewById(R.id.profile_name);
            profileEmail = itemView.findViewById(R.id.profile_email);
            profileNumber = itemView.findViewById(R.id.profile_number);
            profileAddress = itemView.findViewById(R.id.profile_adress);
        }
    }
}
