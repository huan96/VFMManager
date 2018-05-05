package com.haui.huantd.vfmmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Product> listProduct;
    private List<Product> listProductShow;
    private PostListAdapter adapter;
    private RecyclerView rvPost;
    private ImageView btnBack, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listProduct = new ArrayList<>();
        listProductShow = new ArrayList<>();
        initView();
        getData();
    }

    private void initView() {
        rvPost = findViewById(R.id.rv_list_product);
        btnBack = findViewById(R.id.btn_back);
        btnLogout = findViewById(R.id.btn_log_out);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        rvPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new PostListAdapter(rvPost, listProductShow, this);
        adapter.setLoadMore(new PostListAdapter.ILoadMore() {
            @Override
            public void onLoadMore() {
                listProductShow.add(null);
                adapter.notifyItemInserted(listProductShow.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listProductShow.remove(listProductShow.size() - 1);
                        adapter.notifyItemRemoved(listProductShow.size());
                        addNewPost();
                        adapter.notifyDataSetChanged();
                        adapter.setLoaded();
                    }
                }, 300); // Time to load
            }
        });
        rvPost.setAdapter(adapter);
    }

    private void addNewPost() {
        int sizeListProductShow = listProductShow.size() - 1;
        int sizeListProduct = listProduct.size() - 1;
        if ((sizeListProduct - sizeListProductShow) >= 10) {
            int sizeListProductShowNew = sizeListProductShow + 10;
            for (int i = sizeListProductShow; i < sizeListProductShowNew; i++) {
                listProductShow.add(listProduct.get(i));
            }
        } else {
            for (int i = sizeListProductShow; i < sizeListProduct; i++) {
                listProductShow.add(listProduct.get(i));
            }
        }
    }

    private void getData() {
        getListIDProduct();

    }

    private void getListIDProduct() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.NON_CONFIRM_POST);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Product product = dataSnapshot.getValue(Product.class);
                product.setId(dataSnapshot.getKey());
                Log.e(TAG, "CONFIRM_POST.onChildAdded: ");
                listProduct.add(product);
                if (listProductShow.size() < 10) {
                    listProductShow.add(product);
                }
                Log.e("onChildAdded", "xxxxxxxx");
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
