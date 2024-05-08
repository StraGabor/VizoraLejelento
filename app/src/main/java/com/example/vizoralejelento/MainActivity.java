package com.example.vizoralejelento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    EditText usrText;
    EditText passwdText;
    private SharedPreferences preferences;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usrText = findViewById(R.id.userName);
        passwdText = findViewById(R.id.passwd);
        preferences = getSharedPreferences(PREF_KEY,MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();

        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    public void login(View view) {
        String usrName = usrText.getText().toString();
        String password = passwdText.getText().toString();

        auth.signInWithEmailAndPassword(usrName, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(MainActivity.this, "Sikeres bejelentkezes", Toast.LENGTH_LONG).show();
                startDiktate();
            } else {
                Toast.makeText(MainActivity.this, "Sikertelen bejelentkezes: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName",usrText.getText().toString());
        editor.putString("password",passwdText.getText().toString());
        editor.apply();
    }

    private void startDiktate() {
        Intent intent = new Intent(this,VizoraList.class);
        startActivity(intent);
    }

    public void registrate(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}
