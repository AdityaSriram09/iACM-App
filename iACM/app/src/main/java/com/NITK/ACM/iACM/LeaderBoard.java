package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {
    private List<LeaderBoardElement> eList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeaderBoardAdaptor lbAdaptor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        setTitle("Leaderboard");
       // this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        lbAdaptor = new LeaderBoardAdaptor(eList);
        firebaseDatabase=FirebaseDatabase.getInstance();
        GetDataFireBase();
        recyclerView.setAdapter(lbAdaptor);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        lbAdaptor.notifyDataSetChanged();


    }

    void GetDataFireBase(){
        databaseReference=firebaseDatabase.getReference("Leader Board");
        query=databaseReference.orderByChild("points");
        Log.i("data2","hiiii");
        query.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                LeaderBoardElement data=new LeaderBoardElement();
                data= dataSnapshot.getValue(LeaderBoardElement.class);

                Log.i("data",data.person_name);
                lbAdaptor.addItem(data);
                lbAdaptor.notifyDataSetChanged();
                recyclerView.setAdapter(lbAdaptor);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
