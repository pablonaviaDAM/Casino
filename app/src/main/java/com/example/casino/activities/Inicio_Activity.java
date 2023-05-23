package com.example.casino.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casino.R;

public class Inicio_Activity extends AppCompatActivity {

    ImageButton btRuleta, btSlots, btBlackJack;
    Dialog edadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btRuleta = findViewById(R.id.btRuleta);
        btSlots = findViewById(R.id.btSlots);
        btBlackJack = findViewById(R.id.btBlackJack);
        edadDialog = new Dialog(this);
    }

    public void onButtonRuletaClicked(View view) {
        mostrarDialogo();
    }

    public void onButtonSlotsClicked(View view) {
        mostrarDialogo();
    }

    public void onButtonBlackJackClicked(View view) {
        mostrarDialogo();
    }

    //Nos muestra un dialogo para verificar nuestra edad
    public void mostrarDialogo() {
        edadDialog.setContentView(R.layout.edad_dialog);
        edadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgClose = edadDialog.findViewById(R.id.imgClose);
        Button btSi = edadDialog.findViewById(R.id.btSi);
        Button btNo = edadDialog.findViewById(R.id.btNo);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edadDialog.dismiss();
            }
        });

        btSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio_Activity.this, Sesion_Activity.class);
                startActivity(intent);
                edadDialog.dismiss();
                finish();
            }
        });

        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edadDialog.dismiss();
            }
        });
        edadDialog.show();
    }
}