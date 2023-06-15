package com.example.casino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.casino.R;
import com.example.casino.models.ApiEndpoints;
import com.example.casino.models.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registro_Activity2 extends AppCompatActivity {

    private EditText etEmail, etContrasenya, etContrasenya2;
    private String email, pswd, pswd2, nombre, apellidos, DNI, fecha;

    private Button btCancelar, btAceptar;
    private ApiEndpoints endpoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        etEmail = findViewById(R.id.etEmail);
        etContrasenya = findViewById(R.id.etContrasenya);
        etContrasenya2 = findViewById(R.id.etContrasenya2);
        btCancelar = findViewById(R.id.btCancelar);
        btAceptar = findViewById(R.id.btAceptar);

        recibirDatos();
    }

    public void recibirDatos() {
        Bundle datosRecibidos = getIntent().getExtras();
        nombre = datosRecibidos.getString("keyNombre");
        apellidos = datosRecibidos.getString("keyApellidos");
        DNI = datosRecibidos.getString("keyDNI");
        fecha = datosRecibidos.getString("keyFecha");
        Log.i("onCreate", "onCreate:" + nombre + apellidos + DNI + fecha);
    }

    public boolean isValido() {

        Validation valid = new Validation();
        boolean valido = true;

        email = etEmail.getText().toString();
        pswd = etContrasenya.getText().toString();
        pswd2 = etContrasenya2.getText().toString();

        //Valida que el campo email no este vacio y que se introduzcan un correo valido
        if (email.isEmpty() || !valid.correoValido(email)) {
            etEmail.setError("Introduce un correo valido.");
            etEmail.requestFocus();
            valido = false;
        }

        //Valida que el campo contraseña no este vacio y que se introduzcan un correo valido
        if (pswd.isEmpty() || pswd.length() < 6) {
            etContrasenya.setError("El campo tiene que tener min 6 carácteres");
            etContrasenya.requestFocus();
            valido = false;
        }

        //Valida que el campo confirmar contraseña no este vacio y que coincida con el campo contraseña
        if (pswd2.isEmpty() || !valid.contrasenyasIguales(pswd, pswd2)) {
            etContrasenya2.setError("Debe coincidir con la contraseña.");
            etContrasenya2.requestFocus();
            valido = false;
        }
        return valido;
    }

    public void crearCuenta(View view) {

        if (isValido()) {

            //Instanciar la Request Queue
            RequestQueue queue = Volley.newRequestQueue(Registro_Activity2.this);

            //La url a la que se hará un POST para loguear en tu cuenta
            endpoints = new ApiEndpoints();
            String url = endpoints.endpointAPI("/registro");

            //String Request Object
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equalsIgnoreCase("success")) {
                        etEmail.setText(null);
                        etContrasenya.setText(null);
                        etContrasenya2.setText(null);
                        Toast.makeText(Registro_Activity2.this, "Registro correcto", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Registro_Activity2.this, Inicio_Activity2.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro_Activity2.this, "Registro fallido", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    System.out.println(error.getMessage());
                }
            }) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaDate = null;
                    try {
                        fechaDate = dateFormat.parse(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String fechaFormateada = dateFormat.format(fechaDate);

                    //La Key debe coincidir con el nombre exacto de los parametros del servidor
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("apellidos", apellidos);
                    params.put("dni", DNI);
                    params.put("fecha_nacimiento", fechaFormateada);
                    params.put("email", email);
                    params.put("password", pswd);

                    return params;

                }
            }; //End String Request Object
            queue.add(request); //Se realiza la peticion Volley
        }
    }

    public void cancelar(View view) {
        Intent intent = new Intent(Registro_Activity2.this, Inicio_Activity.class);
        startActivity(intent);
        finish();
    }


}