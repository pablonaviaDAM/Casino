package com.example.casino.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.casino.R;
import com.example.casino.databinding.ActivityRuletaBinding;
import com.example.casino.drawerMenu.DrawerBase_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Ruleta_Activity extends DrawerBase_Activity {

    ActivityRuletaBinding activityRuletaBinding;
    private Button botonSeleccionado;
    private ImageButton btSpin;
    private ImageView ruleta, fichaSelected;
    private TextView tvSaldoCuenta, tvFichasApostadas, tvNumResults;
    private Dialog dialogVictory;

    ////////// PROBANDO //////////
    private List<Integer> numerosApostados = new ArrayList<>();
    private Map<Integer, Double> numeroValorApuesta = new HashMap<>();

    private HashMap<Button, Double> valoresApuestaPorBoton = new HashMap<>();
    private List<Button> botonesSeleccionados = new ArrayList<>();

    ////////// ---------PROBANDO--------- //////////

    private static final Random RAMDOM = new Random();
    private String numGanadorS;
    private int angulo = 0, anguloAntiguo = 0;
    private int valorFichasTotales = 0, valorFichaBoton = 0; //VALORFICHABOTON PROBANDO

    private static final float MEDIO_SECTOR = 360f / 37f / 2f; //Calcula cada casilla
    private static final String[] SECTORS = {"32 rojo", "15 negro",
            "19 rojo", "4 negro", "21 rojo", "2 negro", "25 rojo", "17 negro", "34 rojo",
            "6 negro", "27 rojo", "13 negro", "36 rojo", "11 negro", "30 rojo", "8 negro",
            "23 rojo", "10 negro", "5 rojo", "24 negro", "16 rojo", "33 negro",
            "1 rojo", "20 negro", "14 rojo", "31 negro", "9 rojo", "22 negro",
            "18 rojo", "29 negro", "7 rojo", "28 negro", "12 rojo", "35 negro",
            "3 rojo", "26 negro", "0 verde"
    }; //Los valores se tienen que declarar en orden de acuerdo a la imagen de la ruleta

    private final int[] botonesIds = {R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9,
            R.id.bt10, R.id.bt11, R.id.bt12, R.id.bt13, R.id.bt14, R.id.bt15, R.id.bt16, R.id.bt17, R.id.bt18, R.id.bt19,
            R.id.bt20, R.id.bt21, R.id.bt22, R.id.bt23, R.id.bt24, R.id.bt25, R.id.bt26, R.id.bt27, R.id.bt28, R.id.bt29,
            R.id.bt30, R.id.bt31, R.id.bt32, R.id.bt33, R.id.bt34, R.id.bt35, R.id.bt36, R.id.btRojo, R.id.btNegro};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Oculta la barra de estado
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Para mostrar nuestro Toolbar personalizado
        activityRuletaBinding = ActivityRuletaBinding.inflate(getLayoutInflater());
        setContentView(activityRuletaBinding.getRoot());
        allocateActivityTitle("Lucked K");

        btSpin = findViewById(R.id.btSpin);
        ruleta = findViewById(R.id.ruletaSpin);
        tvNumResults = findViewById(R.id.tvNumResults);
        tvFichasApostadas = findViewById(R.id.tvFichasApostadas);
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
                    Button botonRuleta = findViewById(botonId);
                    botonRuleta.setClickable(false);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //CUANDO LA ANIMACION TERMINE SETEAREMOS EN UN TEXTVIEW EL VALOR RESULTANTE DE LA RULETA
                Log.i("onResponse", getSector(360 - (angulo % 360)));

                //Bucle que desbloquea los botones para poder apostar
                for (int botonId : botonesIds) {
                    Button botonRuleta = findViewById(botonId);
                    botonRuleta.setClickable(true);
                }

                //Nos muestra los resultados en el textView
                numGanadorS = getSector(360 - (angulo % 360)); //Ultimo numero que ha salido ganador
                String numResults = tvNumResults.getText().toString();

                String[] partes = numGanadorS.split(" "); //Nos separa el numero del color
                int numeroGanador = Integer.parseInt(partes[0].trim());
                String color = partes[1].trim();


                //En el caso que el textview no este vacio se anyade un espacio
                if (!numResults.isEmpty()) {
                    numGanadorS = " " + numGanadorS;

                }

                numResults = numGanadorS + numResults;

                // Establecer el texto actualizado en el TextView
                tvNumResults.setText(" " + numResults);

                comprobarVictoria(numeroGanador);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //NO SE HACE NADA
            }
        });

        ruleta.startAnimation(rotateAnimation);

    }


    //ESTE METODO NOS MOSTRARA EL NUMERO GANADOR DE LA RULETA
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
                text = SECTORS[i];
            }
            i++;
        } while (text == null && i < SECTORS.length);

        return text;
    }


    private void comprobarVictoria(int numeroGanador) {
        //En el caso que los numeros apostados coincida con el numero ganador nos muestra el dialog de victoria
        if (numerosApostados.contains(numeroGanador)) {
            mostrarDialogo();
        }
    }

    private void mostrarDialogo() {
        dialogVictory.setContentView(R.layout.victoria_dialog);
        dialogVictory.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgClose = dialogVictory.findViewById(R.id.imgClose);
        TextView saldoGanado = dialogVictory.findViewById(R.id.tvSaldoGanado);

        saldoGanado.setText(tvFichasApostadas.getText());


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVictory.dismiss();
            }
        });
        dialogVictory.show(); //Nos muestra el dialogo de victoria
    }

/*
    private void empezarPartida() {

        //Recoge el saldo de la cuenta y fichas apostadas
        tvSaldoCuenta = findViewById(R.id.tvSaldoCuenta);
        double saldo = Double.parseDouble(tvSaldoCuenta.getText().toString());
        double valorApostado = Double.parseDouble(tvFichasApostadas.getText().toString());

        //En el caso que se realice alguna apuesta se restará el valor del saldo
        if (valorApostado > 0) {
            saldo -= valorApostado;
        }
    }

    public void comprobarApuestaBotones() {
        for (int botonId : botonesIds) {
            Button botonApostado = findViewById(botonId);

            //En el caso que este apostado su foreground sera diferente de null
            if (botonApostado.getForeground() != null) {
                int numBoton = obtenerNumeroBoton(botonId);
                double valorApuestaBoton = ;
            }

        }
    }

    public int obtenerNumeroBoton(int botonId) {
        Button boton = findViewById(botonId);
        String numeroBoton = boton.getText().toString();

        return Integer.parseInt(numeroBoton);
    }

    // Este método recoge el valor de apuesta desde un botón
    private double obtenerValorApuestaDesdeBoton(Button boton) {
        double valorApuesta = 0;

        if (valoresApuestaPorBoton.containsKey(boton)) {
            valorApuesta = valoresApuestaPorBoton.get(boton);
        }

        return valorApuesta;
    }

    public HashMap<Button, Double> obtenerValoresApuestaPorBoton() {
        HashMap<Button, Double> valoresApuestaPorBoton = new HashMap<>();

        for (Button boton : botonesSeleccionados) {
            double valorApuesta = obtenerValorApuestaDesdeBoton(boton);
            valoresApuestaPorBoton.put(boton, valorApuesta);
        }

        return valoresApuestaPorBoton;
    }
*/

    //ESTE METODO AUMENTARA EL TAMAÑO DE LA FICHA SELECCIONADA
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
    public void mostrarFicha(@NonNull View view) {

        //Para obtener el id del boton seleccionado
        int buttonId = view.getId();
        botonSeleccionado = findViewById(buttonId);
        Drawable fichaSelected, fichaAjustada;
        Bitmap bitmap;

        // Si no hay ficha seleccionada, no se realiza ninguna acción
        if (this.fichaSelected == null) {
            return;
        }
        int fichaId = this.fichaSelected.getId();

        switch (fichaId) {
            case R.id.imgFicha5:
                // Obtener la imagen de foreground
                fichaSelected = ContextCompat.getDrawable(this, R.drawable.ficha5);

                // PARA CAMBIAR EL TAMAÑO DE LA IMAGEN DEL FOREGROUND
                bitmap = ((BitmapDrawable) Objects.requireNonNull(fichaSelected)).getBitmap();
                fichaAjustada = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 500, true));

                botonSeleccionado.setForeground(fichaAjustada);
                numerosApostados.add(fichaId);
                break;
            case R.id.imgFicha10:
                fichaSelected = ContextCompat.getDrawable(this, R.drawable.ficha10);
                bitmap = ((BitmapDrawable) fichaSelected).getBitmap();
                fichaAjustada = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 500, true));

                botonSeleccionado.setForeground(fichaAjustada);

                numerosApostados.add(fichaId);

                break;
            case R.id.imgFicha25:
                fichaSelected = ContextCompat.getDrawable(this, R.drawable.ficha25);
                bitmap = ((BitmapDrawable) fichaSelected).getBitmap();
                fichaAjustada = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 500, true));

                botonSeleccionado.setForeground(fichaAjustada);

                numerosApostados.add(fichaId);

                break;
            case R.id.imgFicha50:
                fichaSelected = ContextCompat.getDrawable(this, R.drawable.ficha50);
                bitmap = ((BitmapDrawable) fichaSelected).getBitmap();
                fichaAjustada = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 500, true));

                botonSeleccionado.setForeground(fichaAjustada);

                numerosApostados.add(fichaId);

                break;
            case R.id.imgFicha100:
                fichaSelected = ContextCompat.getDrawable(this, R.drawable.ficha100);
                bitmap = ((BitmapDrawable) fichaSelected).getBitmap();
                fichaAjustada = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 500, true));

                botonSeleccionado.setForeground(fichaAjustada);

                numerosApostados.add(fichaId);

                break;
            default:
                // No se establece ningún foreground si la ficha seleccionada no coincide con ninguna de las opciones anteriores
                botonSeleccionado.setForeground(null);
                break;
        }
        recogerValoresFichas();
    }

    // Dependiendo de la ficha seleccionada, el valor total de la apuesta aumentara
    public void recogerValoresFichas() {
        int fichaId = fichaSelected.getId();

        switch (fichaId) {
            case R.id.imgFicha5:
                valorFichasTotales += 5;
                break;
            case R.id.imgFicha10:
                valorFichasTotales += 10;
                break;
            case R.id.imgFicha25:
                valorFichasTotales += 25;
                break;
            case R.id.imgFicha50:
                valorFichasTotales += 50;
                break;
            case R.id.imgFicha100:
                valorFichasTotales += 100;
                break;
            default:
                valorFichasTotales = 0;
                break;
        }
        tvFichasApostadas.setText(String.valueOf(valorFichasTotales)); //Nos muestra el valor total de las fichas
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
        //Nos borra el valor de la apuesta, de las fichas y los numeros apostados
        valorFichasTotales = 0;
        tvFichasApostadas.setText(String.valueOf(valorFichasTotales));
        numerosApostados.clear();
    }
}