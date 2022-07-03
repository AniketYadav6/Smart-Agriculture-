package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    private DatabaseReference myRef2;
    private FirebaseDatabase database;
    private TextView textview;
    private TextView textview1;
    private TextView textview2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

        textview=findViewById(R.id.temp);
        textview1=findViewById(R.id.humi);
        textview2=findViewById(R.id.moi);
        button=findViewById(R.id.refresh);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Temperature");
        myRef1 = database.getReference().child("Humidity");
        myRef2 = database.getReference().child("Moisture");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            textview.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data1=snapshot.getValue().toString();
                            textview1.setText(data1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data2=snapshot.getValue().toString();
                            textview2.setText(data2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}