package com.example.cenozona;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cenozona.adapters.MyCartAdapter;
import com.example.cenozona.models.MyCartModel;
import com.example.cenozona.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyCartsFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth auth;
    ProgressBar progressBar;
    Button buyNow;

    List<Integer> totalList = new ArrayList<Integer>();
    ImageView minPriceImg;
    TextView overTotalAmount, overTotalAmountDis, overTotalAmountTempo, overTotalAmountRoda,overTotalAmountMaxi,overTotalAmountLidl;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    public MyCartsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db= FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        progressBar=root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);


        overTotalAmount = root.findViewById(R.id.textView6);
        overTotalAmountDis = root.findViewById(R.id.dis_total_price_over);
        overTotalAmountTempo = root.findViewById(R.id.tempo_total_price_over);
        overTotalAmountRoda = root.findViewById(R.id.roda_total_price_over);
        overTotalAmountMaxi = root.findViewById(R.id.maxi_total_price_over);
        overTotalAmountLidl = root.findViewById(R.id.lidl_total_price_over);
        minPriceImg=root.findViewById(R.id.min_totalPrice_img);


        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);
        buyNow=root.findViewById(R.id.buy_now);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                    String documentId = documentSnapshot.getId();

                    MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);

                    myCartModel.setDocumentId(documentId);

                    cartModelList.add(myCartModel);
                    cartAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                calculateATotalAmount(cartModelList);
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);

                cartModelList.clear();
                overTotalAmount.setText("0");
                overTotalAmountRoda.setText("0");
                overTotalAmountLidl.setText("0");
                overTotalAmountTempo.setText("0");
                overTotalAmountDis.setText("0");
                overTotalAmountMaxi.setText("0");
                overTotalAmount.setText("0");
                cartAdapter.notifyDataSetChanged();

            }
        });
        return root;
    }

    private void calculateATotalAmount(List<MyCartModel> cartModelList) {

        List<Double> list = new ArrayList<Double>();
        double totalAmount=0.0;
        double totalAmountRoda=0.0;
        double totalAmountDis=0.0;
        double totalAmountMaxi=0.0;
        double totalAmountTempo=0.0;
        double totalAmountLidl=0.0;
        for (MyCartModel myCartModel : cartModelList){

            totalAmountRoda += myCartModel.getTotalPriceRoda();
            totalAmountDis += myCartModel.getTotalPriceDis();
            totalAmountMaxi += myCartModel.getTotalPriceMaxi();
            totalAmountTempo += myCartModel.getTotalPriceTempo();
            totalAmountLidl += myCartModel.getTotalPriceLidl();


        }

        list.add( totalAmountRoda);
        list.add( totalAmountDis);
        list.add( totalAmountMaxi);
        list.add( totalAmountTempo);
        list.add( totalAmountLidl);
        Collections.sort(list);

        totalAmount=list.get(0);

        if (list.get(0)==totalAmountDis){

            minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.dis));

        } else if (list.get(0)==totalAmountRoda){

            minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.roda));

        } else if (list.get(0)==totalAmountTempo){

            minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.tempo));

        } else if (list.get(0)==totalAmountLidl){

            minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.lidl));

        } else if (list.get(0)==totalAmountMaxi){

            minPriceImg.setImageDrawable(getResources().getDrawable(R.drawable.maxi));

        }

        overTotalAmountRoda.setText("Ukupan iznos: "+totalAmountRoda);
        overTotalAmountLidl.setText("Ukupan iznos: "+totalAmountLidl);
        overTotalAmountTempo.setText("Ukupan iznos: "+totalAmountTempo);
        overTotalAmountDis.setText("Ukupan iznos: "+totalAmountDis);
        overTotalAmountMaxi.setText("Ukupan iznos: "+totalAmountMaxi);
        overTotalAmount.setText("Ukupan iznos: "+totalAmount);


    }


}