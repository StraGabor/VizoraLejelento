package com.example.vizoralejelento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VizoraList extends AppCompatActivity {
    private static final String TAG = VizoraList.class.getName();
    private FirebaseUser user;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private ArrayList<Vizora> vizorak;
    private VizoraAdapter adapter;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private Notification notification;
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

        /*
        EdgeToEdge.enable(this);
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

        notification = new Notification(this);

        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Vizorak");
        queryData();
        Log.i(TAG, "onCreate");
    }

    private void queryData() {
        vizorak.clear();
        reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                Vizora vizora = document.toObject(Vizora.class);
                vizorak.add(vizora);
            }

            adapter.notifyDataSetChanged();
        });

    }

    private void initializeData() {
        String[] ID = getResources().getStringArray(R.array.id);
        String[] azonosito = getResources().getStringArray(R.array.vizora_az);
        String[] vizoraAllas = getResources().getStringArray(R.array.vizora_allas);
        String[] ugyfelNeve = getResources().getStringArray(R.array.vizora_ugyfel);
        String[] email = getResources().getStringArray(R.array.mail);
        String[] befizetett = getResources().getStringArray(R.array.befizet);

        for (int i = 0; i < azonosito.length; i++) {
            reference.add(new Vizora(ID[i],azonosito[i],vizoraAllas[i],ugyfelNeve[i],email[i],befizetett[i]));
        }
    }



    public void torles(Vizora ora){
        if (ora.getAzonosito() == null){
            Toast.makeText(this, "Vizora null erteku.", Toast.LENGTH_LONG).show();
            return;
        }
        DocumentReference ref = reference.document(ora.getAzonosito());
        ref.delete().addOnSuccessListener(success ->{
            notification.send("Diktalas torolve");
        }).addOnFailureListener(fail ->{
            Toast.makeText(this, "Vizora " + ora.getAzonosito() + " nem lehet torolni.", Toast.LENGTH_LONG).show();
        });
        queryData();
        notification.cancel();
    }

    public void modositas(Vizora ora){
        reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                Vizora vizora = document.toObject(Vizora.class);
                if (vizora.getID().equals(ora.getID())){
                    vizora.setAzonosito(ora.getAzonosito());
                    vizora.setVizoraAllas(ora.getVizoraAllas());
                    vizora.setUgyfelNeve(ora.getUgyfelNeve());
                    vizora.setEmail(ora.getEmail());
                    vizora.setBefizetett(ora.getBefizetett());
                    torles(vizora);
                    reference.add(vizora);
                    break;
                }
            }


        });
        notification.send("Diktalas modositva");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.vizora_list_menu,menu);
        Log.i(TAG, "onCreateMenu");
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

        if (item.getItemId() == R.id.lista){
            Intent intent = new Intent(this,VizoraList.class);
            startActivity(intent);
        }
        Log.i(TAG, "onOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepMenu");
        return super.onPrepareOptionsMenu(menu);
    }
}