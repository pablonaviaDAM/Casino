package com.example.casino;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.casino.databinding.ActivityRuletaBinding;

import java.util.Random;

public class Ruleta_Activity extends DrawerBase_Activity {

    ActivityRuletaBinding activityRuletaBinding;
    private Button btSpin, botonSeleccionado;
    private ImageView ruleta, fichaSelected;
    private TextView tvNumResults, tvValorApuesta;
    private static final Random RAMDOM = new Random();
    private int angulo = 0, anguloAntiguo = 0;
    private int valorApuesta = 0;

    private static final float MEDIO_SECTOR = 360f / 37f / 2f; //Calcula cada casilla
    private static final String[] sectors = {"32 rojo", "15 negro",
            "19 rojo", "4 negro", "21 rojo", "2 negro", "25 rojo", "17 negro", "34 rojo",
            "6 negro", "27 rojo", "13 negro", "36 rojo", "11 negro", "30 rojo", "8 negro",
            "23 rojo", "10 negro", "5 rojo", "24 negro", "16 rojo", "33 negro",
            "1 rojo", "20 negro", "14 rojo", "31 negro", "9 rojo", "22 negro",
            "18 rojo", "29 negro", "7 rojo", "28 negro", "12 rojo", "35 negro",
            "3 rojo", "26 negro", "zero verde"
    }; //Los valores se tienen que declarar en orden de acuerdo a la imagen de la ruleta

    private final int[] botonesIds = {R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9,
            R.id.bt10, R.id.bt11, R.id.bt12, R.id.bt13, R.id.bt14, R.id.bt15, R.id.bt16, R.id.bt17, R.id.bt18, R.id.bt19,
            R.id.bt20, R.id.bt21, R.id.bt22, R.id.bt23, R.id.bt24, R.id.bt25, R.id.bt26, R.id.bt27, R.id.bt28, R.id.bt29,
            R.id.bt30, R.id.bt31, R.id.bt32, R.id.bt33, R.id.bt34, R.id.bt35, R.id.bt36};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRuletaBinding = ActivityRuletaBinding.inflate(getLayoutInflater());
        setContentView(activityRuletaBinding.getRoot());
        allocateActivityTitle("Casino");

        btSpin = findViewById(R.id.btSpin);
        ruleta = findViewById(R.id.ruletaSpin);
        tvNumResults = findViewById(R.id.tvNumResults);
        tvValorApuesta = findViewById(R.id.tvValorApuesta);
    }

    public void spinRuleta(View view) {
        //CALCULAMOS EL ANGULO ALEATORIO PARA LA ROTACION DE NUESTRA RULETA
        anguloAntiguo = angulo % 360;
        angulo = RAMDOM.nextInt(360) + 720;
        //EFECTO DE ROTACION DESDE EL CENTRO DE NUESTRA RULETA
        RotateAnimation rotateAnimation = new RotateAnimation(anguloAntiguo, angulo,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        //Generamos un numero aleatorio
        int[] timeSpin = {3600, 4600, 5600};
        Random randomTime = new Random();
        int tiempoAleatorio = timeSpin[randomTime.nextInt(timeSpin.length)];

        rotateAnimation.setDuration(tiempoAleatorio);
        Log.i("onResponse", "Tiempo: " + tiempoAleatorio);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //CUANDO LA ANIMACION COMIENCE NO DEBEREMOS PERMITIR QUE SE REALICEN LAS APUESTAS
                //Bucle que bloquea los botones para no poder apostar
                for (int botonId : botonesIds) {
                    Button boton = findViewById(botonId);
                    boton.setClickable(false);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animation animation) {
                //CUANDO LA ANIMACION TERMINE SETEAREMOS EN UN TEXTVIEW EL VALOR RESULTANTE DE LA RULETA
                Log.i("onResponse", getSector(360 - (angulo % 360)));

                //Bucle que desbloquea los botones para poder apostar
                for (int botonId : botonesIds) {
                    Button boton = findViewById(botonId);
                    boton.setClickable(true);
                }

                //Nos muestra los resultados en el textView
                String numResult = getSector(360 - (angulo % 360));
                String numResults = tvNumResults.getText().toString();
                if (!numResults.isEmpty()) {
                    numResult = " " + numResult;
                }

                numResults = numResult + numResults;

                // Establecer el texto actualizado en el TextView
                tvNumResults.setText(" " + numResults);
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

    public void fichaSeleccionada(View view) {
        ImageView imgClickada = (ImageView) view;

        if (fichaSelected != null) {
            // Restablecer el tamaño de la imagen previamente seleccionada
            fichaSelected.setScaleX(1.0f);
            fichaSelected.setScaleY(1.0f);
        }

        //Ficha == imagenSeleccionada; imgClickada == imagen que se hizo clic recientemente
        if (fichaSelected == imgClickada) {
            // Si se hace clic en la misma imagen nuevamente, deseleccionarla
            fichaSelected = null;
        } else {
            // Establecer la nueva imagen seleccionada y ajustar su tamaño
            fichaSelected = imgClickada;
            fichaSelected.setScaleX(1.2f);
            fichaSelected.setScaleY(1.2f);

        }
    }

    //ESTE METODO NOS PERMITE MOSTRAR EN LOS BOTONES LA FICHA SELECCIONADA
    @SuppressLint("NonConstantResourceId")
    public void mostrarFicha(View view) {

        //Para obtener el id del boton seleccionado
        int buttonId = view.getId();
        botonSeleccionado = findViewById(buttonId);

        // Si no hay ficha seleccionada, no se realiza ninguna acción
        if (fichaSelected == null) {
            return;
        }
        int fichaId = fichaSelected.getId();

        switch (fichaId) {
            case R.id.imgFicha5:
                botonSeleccionado.setForeground(ContextCompat.getDrawable(this, R.drawable.ficha5));

                //*************************** SE PODRIA IMPLEMENTAR LA FUNCION DE COMPROBACION QUE CUANDO LLEGUE
                // A LA CIFRA DE LA FICHA SIGUIENTE CAMBIE DE IMAGEN ***************************

                break;
            case R.id.imgFicha10:
                botonSeleccionado.setForeground(ContextCompat.getDrawable(this, R.drawable.ficha10));
                break;
            case R.id.imgFicha25:
                botonSeleccionado.setForeground(ContextCompat.getDrawable(this, R.drawable.ficha25));
                break;
            case R.id.imgFicha50:
                botonSeleccionado.setForeground(ContextCompat.getDrawable(this, R.drawable.ficha50));
                break;
            case R.id.imgFicha100:
                botonSeleccionado.setForeground(ContextCompat.getDrawable(this, R.drawable.ficha100));
                break;
            default:
                // No se establece ningún foreground si la ficha seleccionada no coincide con ninguna de las opciones anteriores
                botonSeleccionado.setForeground(null);
                break;
        }
        recogerValoresApuesta();
    }

    // Dependiendo de la ficha seleccionada, el valor de la apuesta aumentará
    @SuppressLint("NonConstantResourceId")
    public void recogerValoresApuesta() {
        int fichaId = fichaSelected.getId();

        switch (fichaId) {
            case R.id.imgFicha5:
                valorApuesta += 5;
                break;
            case R.id.imgFicha10:
                valorApuesta += 10;
                break;
            case R.id.imgFicha25:
                valorApuesta += 25;
                break;
            case R.id.imgFicha50:
                valorApuesta += 50;
                break;
            case R.id.imgFicha100:
                valorApuesta += 100;
                break;
        }
        tvValorApuesta.setText(String.valueOf(valorApuesta));
    }

    //METODO QUE NOS BORRA LA APUESTA REALIZADA
    public void borrarApuesta(View view) {

        // Si no hay botón seleccionado, no se realiza ninguna acción
        if (fichaSelected == null) {
            return;
        }

        //Bucle que borra los foregrounds de los botones
        for (int botonId : botonesIds) {
            Button boton = findViewById(botonId);
            boton.setForeground(null);
        }
        //Nos borra el valor de la apuesta
        valorApuesta = 0;
        tvValorApuesta.setText(String.valueOf(valorApuesta));
    }
}