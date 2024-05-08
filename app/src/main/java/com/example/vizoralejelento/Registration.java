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

public class Registration extends AppCompatActivity {
    private static final String PREF_KEY = Registration.class.getPackage().toString();
    EditText user;
    EditText email;
    EditText passwd;
    EditText passwdCOnfirm;
    private SharedPreferences preferences;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user = findViewById(R.id.usrField);
        email = findViewById(R.id.emailField);
        passwd = findViewById(R.id.passwdField);
        passwdCOnfirm = findViewById(R.id.repassField);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        user.setText(userName);
        passwd.setText(password);
        passwdCOnfirm.setText(password);

        auth = FirebaseAuth.getInstance();

        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    public void register(View view) {
        String userName = user.getText().toString();
        String mail = email.getText().toString();
        String password = passwd.getText().toString();
        String confirmPassword = passwdCOnfirm.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(Registration.this,"Nem egyenlo a ket jelszo!",Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(this, task ->{
           if (task.isSuccessful()){
               Toast.makeText(Registration.this,"Sikeres regisztracio!",Toast.LENGTH_LONG).show();
               startDiktate();
           }else{
               Toast.makeText(Registration.this, "Sikertelen regisztracio: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
           }
        });
    }

    private void startDiktate() {
        Intent intent = new Intent(this,VizoraList.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}