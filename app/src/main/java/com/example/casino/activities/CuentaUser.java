package com.example.casino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casino.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CuentaUser extends AppCompatActivity {

    TextView tvNombre, tvApellidos, tvDNI, tvFechaNacimiento, tvEmail;
    String nombre, apellidos, dni, email, fecha_nacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_user);

        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvDNI = findViewById(R.id.tvDNI);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
        tvEmail = findViewById(R.id.tvEmail);

        mostrarDatos();

    }

    public void volverInicio(View view) {
        Intent intent = new Intent(getApplicationContext(), Inicio_Activity.class);
        startActivity(intent);
        finish();
    }

    public void recogerDatos() {
        nombre = getIntent().getStringExtra("nombre");
        apellidos = getIntent().getStringExtra("apellidos");
        dni = getIntent().getStringExtra("dni");
        fecha_nacimiento = getIntent().getStringExtra("fecha_nacimiento");
        email = getIntent().getStringExtra("email");
        Log.i("onCreate", nombre + apellidos + dni + email);
    }

    public void mostrarDatos() {
        recogerDatos();
        tvNombre.setText(nombre);
        tvApellidos.setText(apellidos);
        tvDNI.setText(dni);
        tvFechaNacimiento.setText(formatearFecha(fecha_nacimiento));
        tvEmail.setText(email);
    }

    public String formatearFecha(String fechaFormateada) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fechaNacimientoDate = sdf.parse(fecha_nacimiento);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            fechaFormateada = formatoFecha.format(fechaNacimientoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaFormateada;
    }
}