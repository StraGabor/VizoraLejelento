package com.example.vizoralejelento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Diktalas extends AppCompatActivity {
    private static final String TAG = Diktalas.class.getName();
    EditText azonosito;
    EditText oraallas;
    EditText ugyfel;
    EditText mail;
    TextView befizet;
    private ArrayList<Vizora> vizorak;
    private Notification notification;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private ArrayList<Integer> max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diktalas);

        azonosito = findViewById(R.id.azonosito);
        oraallas = findViewById(R.id.oraallas);
        ugyfel = findViewById(R.id.ugyfelnev);
        mail = findViewById(R.id.email);
        befizet = findViewById(R.id.befizetett);
        notification = new Notification(this);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Vizorak");
        vizorak = new ArrayList<>();
        max = new ArrayList<Integer>();
        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Log.i(TAG, "onCreate");
    }

    public void lementes(View view){
        reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                Vizora vizora = document.toObject(Vizora.class);
                vizorak.add(vizora);
            }
        });

        String azon = azonosito.getText().toString();
        String oraall = oraallas.getText().toString();
        String ugy = ugyfel.getText().toString();
        String email = mail.getText().toString();
        String befizetve = befizet.getText().toString();

        if (azon.isEmpty()){
            Toast.makeText(this, "Ervenytelen ID.", Toast.LENGTH_LONG).show();
            finish();
        }
        if (oraall.isEmpty()){
            Toast.makeText(this, "Ervenytelen oraallas.", Toast.LENGTH_LONG).show();
            finish();
        }
        if (ugy.isEmpty()){
            Toast.makeText(this, "Ervenytelen ugyfelnev.", Toast.LENGTH_LONG).show();
            finish();
        }
        if (email.isEmpty()){
            Toast.makeText(this, "Ervenytelen email.", Toast.LENGTH_LONG).show();
            finish();
        }

        if (Integer.valueOf(azon) < max.size()){
            Toast.makeText(this, "Ervenytelen ID.", Toast.LENGTH_LONG).show();
        }else{
            Vizora ora = new Vizora(String.valueOf(max.size()+1),azon,oraall,ugy,email,befizetve);
            reference.add(ora).addOnSuccessListener(success ->{
                notification.send("Oraallas sikeresen bediktalva.");
            }).addOnFailureListener(fail->{
                Toast.makeText(this, "Sikertelen vizora diktalas.", Toast.LENGTH_LONG).show();
            });
            Intent intent = new Intent(this,VizoraList.class);
            startActivity(intent);
        }
    }

    public void listazas(View view) {
        Intent intent = new Intent(this,VizoraList.class);
        startActivity(intent);
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

        return super.onPrepareOptionsMenu(menu);
    }

    public void fotozas(View view) {

    }

}