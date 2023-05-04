package com.example.casino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Splah_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

        //Para ocultar la barra de estado
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Animaciones del texto del Splash
        TextView tvSplash = findViewById(R.id.tvSplash);
        tvSplash.animate().rotationX(1000).setDuration(2000).setStartDelay(2000);


        Thread thread = new Thread() {

            public void run() {

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.getMessage();
                } finally {
                    Intent intent = new Intent(Splah_Activity.this, Inicio_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}