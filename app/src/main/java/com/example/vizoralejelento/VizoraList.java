package com.example.vizoralejelento;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class VizoraList extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private ArrayList<Vizora> vizorak;
    private VizoraAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizora_list);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            Toast.makeText(VizoraList.this,"Nem autentikált felhasználó!",Toast.LENGTH_LONG).show();
            finish();
        }

        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        recyclerView = findViewById(R.id.vizoraLista);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        vizorak = new ArrayList<>();
        adapter = new VizoraAdapter(this,vizorak);
        recyclerView.setAdapter(adapter);
        initializeData();

    }

    private void initializeData() {
         int[] azonosito = getResources().getIntArray(R.array.vizora_az);
         int[] vizoraAllas = getResources().getIntArray(R.array.vizora_allas);
         String[] ugyfelNeve = getResources().getStringArray(R.array.vizora_ugyfel);
         String[] email = getResources().getStringArray(R.array.mail);
         String[] befizetett = getResources().getStringArray(R.array.befizet);

         vizorak.clear();

        for (int i = 0; i < azonosito.length; i++) {
            vizorak.add(new Vizora(azonosito[i],vizoraAllas[i],ugyfelNeve[i],email[i],befizetett[i]));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.vizora_list_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.kilepes){
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        if (item.getItemId() == R.id.lejelentes){
            Intent intent = new Intent(this,Diktalas.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }
}