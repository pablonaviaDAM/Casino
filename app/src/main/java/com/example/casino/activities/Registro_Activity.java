package com.example.casino.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casino.R;
import com.example.casino.models.Validation;

import java.util.Calendar;

public class Registro_Activity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etDNI, etFecha;
    private Button btCancelar, btAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etDNI = findViewById(R.id.etDNI);
        etFecha = findViewById(R.id.etFecha);
        btCancelar = findViewById(R.id.btCancelar3);
        btAceptar = findViewById(R.id.btAceptar3);


    }

    //HACE QUE EL EDITTEXT NOS MUESTRE LA FECHA EN UN DIALOG
    public void seleccionarFecha(View view) {
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(Registro_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anyo, int mes, int dia) {
                mes = mes + 1;
                String formattedDay = String.format("%02d", dia);  // Agrega cero a la izqda si el día es menor a 10
                String formattedMonth = String.format("%02d", mes);  // Agrega cero a la izqda si el mes es menor a 10
                String date = formattedDay + "/" + formattedMonth + "/" + anyo;
                etFecha.setText(date);
            }
        }, year, month, day);
        dialog.show();
    }


    public void enviarDatos(View view) {

        //COMPRUEBA QUE TODOS LOS DATOS SEAN VALIDOS
        // Y SI SON CORRECTOS SE ENVIARAN LOS DATOS AL SIGUIENTE ACTIVITY
        if (isValido()) {

            Bundle enviarDatos = new Bundle();
            enviarDatos.putString("keyNombre",etNombre.getText().toString());
            enviarDatos.putString("keyApellidos",etApellidos.getText().toString());
            enviarDatos.putString("keyDNI",etDNI.getText().toString());
            enviarDatos.putString("keyFecha",etFecha.getText().toString());

            Intent intent = new Intent(Registro_Activity.this,Registro_Activity2.class);
            intent.putExtras(enviarDatos);
            startActivity(intent);
        }
    }

    public boolean isValido() {

        Validation valid = new Validation();
        boolean valido = true;
        String nombre = etNombre.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String dni = etDNI.getText().toString();
        String fecha = etFecha.getText().toString();

        //Valida que el campo nombre no este vacio y que se introduzcan solo letras
        if (nombre.isEmpty() || !valid.textoLetras(nombre)) {
            etNombre.setError("Introduce un nombre valido.");
            valido = false;
        }

        //Valida que el campo apellidos no este vacio y que se introduzcan solo letras
        if (apellidos.isEmpty() || !valid.textoLetras(apellidos)) {
            etApellidos.setError("Introduce unos apellidos validos.");
            valido = false;
        }

        //Valida que el campo dni no este vacio y que tenga el formato de 8 numeros y 1 letra
        if (dni.isEmpty() || !valid.dniValido(dni)) {
            etDNI.setError("Tiene que ser un DNI válido.");
            valido = false;
        }

        //Valida que el campo no este vacio y que la fecha tenga el formato dd/mm/yyyy
        if (fecha.isEmpty() || !valid.fechaValida(fecha)) {
            etFecha.setError("Tiene que seleccionar una fecha válida.");
            valido = false;
        }
        return valido;
    }
}