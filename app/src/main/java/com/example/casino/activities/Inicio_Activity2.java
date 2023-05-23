package com.example.casino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.casino.drawerMenu.DrawerBase_Activity;
import com.example.casino.databinding.ActivityInicio2Binding;

public class Inicio_Activity2 extends DrawerBase_Activity {

    ActivityInicio2Binding activityInicio2Binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInicio2Binding = ActivityInicio2Binding.inflate(getLayoutInflater());
        allocateActivityTitle("Lucked K");
        setContentView(activityInicio2Binding.getRoot());


    }


    public void onButtonRuletaClicked2(View view) {
        Intent intent = new Intent(Inicio_Activity2.this, Ruleta_Activity.class);
        startActivity(intent);

    }

    public void onButtonSlotsClicked2(View view) {

    }

    public void onButtonBlackJackClicked2(View view) {
    }
}