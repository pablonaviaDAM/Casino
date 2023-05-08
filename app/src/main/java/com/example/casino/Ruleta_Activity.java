package com.example.casino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Ruleta_Activity extends AppCompatActivity {


    private Button btSpin;
    private ImageView ruleta;
    private TextView tvNumResults;
    private static final Random RAMDOM = new Random();
    private int angulo = 0, anguloAntiguo = 0;
    private static final float MEDIO_SECTOR = 360f / 37f / 2f;
    private static final String[] sectors = {"32 red", "15 black",
            "19 red", "4 black", "21 red", "2 black", "25 red", "17 black", "34 red",
            "6 black", "27 red", "13 black", "36 red", "11 black", "30 red", "8 black",
            "23 red", "10 black", "5 red", "24 black", "16 red", "33 black",
            "1 red", "20 black", "14 red", "31 black", "9 red", "22 black",
            "18 red", "29 black", "7 red", "28 black", "12 red", "35 black",
            "3 red", "26 black", "zero"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        btSpin = findViewById(R.id.btSpin);
        ruleta = findViewById(R.id.ruletaSpin);
        tvNumResults = findViewById(R.id.tvNumResults);


        btSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CALCULAMOS EL ANGULO ALEATORIO PARA LA ROTACION DE NUESTRA RULETA
                anguloAntiguo = angulo % 360;
                angulo = RAMDOM.nextInt(360) + 720;
                //EFECTO DE ROTACION DESDE EL CENTRO DE NUESTRA RULETA
                RotateAnimation rotateAnimation = new RotateAnimation(anguloAntiguo, angulo,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                rotateAnimation.setDuration(3600);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        //CUANDO LA ANIMACION COMIENCE DEBEREMOS NO PERMITIR QUE SE REALICEN LAS APUESTAS
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //CUANDO LA ANIMACION TERMINE SETEAREMOS EN UN TEXTVIEW EL VALOR RESULTANTE DE LA RULETA
                        Log.i("onResponse", getSector(360 - (angulo % 360)));
                        //tvNumResults.setText(getSector(360 - (angulo % 360)));
                        //tvNumResults.append(" ");

/*
                        //Opcion1
                        StringBuilder sb = new StringBuilder(tvNumResults.getText());
                        sb.append(getSector(360 - (angulo % 360))).append(" ");
                        tvNumResults.setText(sb.toString());*/

                        String numResult = getSector(360 - (angulo % 360));
                        String numResults = tvNumResults.getText().toString();
                        if (!numResults.isEmpty()) {
                            numResult = " " + numResult;
                        }

                        numResults = numResult + numResults;


                        // Establecer el texto actualizado en el TextView
                        tvNumResults.setText(" " + numResults + " ");


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                ruleta.startAnimation(rotateAnimation);
            }

            private String getSector(int angulo) {
                int i = 0;
                String text = null;

                do {
                    //empieza y termina en cada sector de la ruleta
                    float start = MEDIO_SECTOR * (i * 2 + 1);
                    float end = MEDIO_SECTOR * (i * 2 + 3);

                    if (angulo >= start && angulo < end) {
                        //angulo esta dentro
                        //asi que el texto es igual a los sectores
                        text = sectors[i];
                    }
                    i++;

                } while (text == null && i < sectors.length);
                return text;
            }
        });
    }
}