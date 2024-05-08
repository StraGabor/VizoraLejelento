package com.example.vizoralejelento;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Diktalas extends AppCompatActivity {

    EditText azonosito;
    EditText oraallas;
    EditText ugyfel;
    EditText mail;
    TextView befizet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diktalas);

        azonosito = findViewById(R.id.azonosito);
        oraallas = findViewById(R.id.oraallas);
        ugyfel = findViewById(R.id.ugyfelnev);
        mail = findViewById(R.id.email);
        befizet = findViewById(R.id.befizetett);

        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
}