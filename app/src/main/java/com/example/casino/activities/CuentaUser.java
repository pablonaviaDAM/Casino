package com.example.casino.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.casino.R;
import com.example.casino.activities.Inicio_Activity;

public class CuentaUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_user);
    }

    public void volverInicio(View view) {
        Intent intent = new Intent(getApplicationContext(), Inicio_Activity.class);
        startActivity(intent);
        finish();
    }
}