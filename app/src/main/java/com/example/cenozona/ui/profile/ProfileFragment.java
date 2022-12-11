package com.example.cenozona.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cenozona.MainActivity;
import com.example.cenozona.R;
import com.example.cenozona.activities.DetailedActivity;
import com.example.cenozona.activities.LoginActivity;
import com.example.cenozona.activities.RegistrationActivity;
import com.example.cenozona.adapters.PopularAdapters;
import com.example.cenozona.adapters.ProfileAdapter;
import com.example.cenozona.databinding.FragmentProfileBinding;
import com.example.cenozona.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    CircleImageView profileImg;
    EditText name,email,number,address;
    Button update, signOut;

    FirebaseFirestore fstore;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase firestore;
    ProfileAdapter profileAdapter;
    List<UserModel> userModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        fstore = FirebaseFirestore.getInstance();

        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        number = root.findViewById(R.id.profile_number);
        address = root.findViewById(R.id.profile_adress);
        update = root.findViewById(R.id.update);
        signOut = root.findViewById(R.id.sign_out);


        firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        name.setText(userModel.getName());
                        Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                        userModel.setName(name.toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String profileName = user.getDisplayName();
            String profileEmail = user.getEmail();
            name.setText(profileName);
            email.setText(profileEmail);
            email.setEnabled(false);
            Uri photoUrl = user.getPhotoUrl();
            Glide.with(ProfileFragment.this).load(photoUrl).into(profileImg);

            firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("number").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    number.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });

            firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("address").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    address.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });

            firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("profileImg").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("TAG", dataSnapshot.getValue(String.class));
                    Glide.with(getContext()).load(dataSnapshot.getValue(String.class)).into(profileImg);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });




        }
            profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 33);
                }
            });


            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    auth.signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    email.getText();
                    number.getText();
                    address.getText();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(String.valueOf(name.getText()))
                            .build();

                    firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .child("number").setValue(String.valueOf(number.getText()));
                    firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .child("address").setValue(String.valueOf(address.getText()));
                    Toast.makeText(getContext(), "Podaci izmenjeni", Toast.LENGTH_SHORT).show();


                }
            });

            return root;

        }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Profilna slika izmenjena", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firestore.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Profilna slika ucitana", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });


        }
    }
}