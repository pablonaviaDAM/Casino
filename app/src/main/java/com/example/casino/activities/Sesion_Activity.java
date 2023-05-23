package com.example.casino.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casino.R;
import com.example.casino.models.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sesion_Activity extends AppCompatActivity {

    private TextView registro;
    private EditText etEmail, etPassword;
    private String email, pswd;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        registro = findViewById(R.id.tvCrearCuenta2);
        etEmail = findViewById(R.id.etEmail2);
        etPassword = findViewById(R.id.etContrasenya3);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registrarCuenta(View view) {
        Intent intent = new Intent(Sesion_Activity.this, Registro_Activity.class);
        startActivity(intent);
        finish();
    }

    public void volverInicio(View view) {
        Intent intent = new Intent(getApplicationContext(), Inicio_Activity.class);
        startActivity(intent);
        finish();
    }

    public void iniciarSesion(View view) {

        if (isValido()) {
            mAuth.signInWithEmailAndPassword(email, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(Sesion_Activity.this, "Usuario logueado correctamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Sesion_Activity.this, Inicio_Activity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Sesion_Activity.this, "Error al iniciar sesion", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public boolean isValido() {

        Validation valid = new Validation();
        boolean valido = true;

        email = etEmail.getText().toString();
        pswd = etPassword.getText().toString();

        if (email.isEmpty() || !valid.correoValido(email)) {
            etEmail.setError("Introduce un email válido");
            etEmail.requestFocus();
            valido = false;
        }

        if (etPassword.length() < 6) {
            etPassword.setError("Debe contener min 6 caracteres");
            etEmail.requestFocus();
            valido = false;
        }
        return valido;
    }
}