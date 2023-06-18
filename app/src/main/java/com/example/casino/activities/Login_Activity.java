package com.example.casino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.casino.R;
import com.example.casino.models.ApiEndpoints;
import com.example.casino.models.User;
import com.example.casino.models.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login_Activity extends AppCompatActivity {

    private TextView registro;
    private EditText etEmail, etPassword;
    private String email, pswd;
    private ApiEndpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail2);
        etPassword = findViewById(R.id.etContrasenya3);
        registro = findViewById(R.id.tvCrearCuenta2);

    }

    public void registrarCuenta(View view) {
        Intent intent = new Intent(Login_Activity.this, Registro_Activity.class);
        startActivity(intent);
        finish();
    }

    public void volverInicio(View view) {
        Intent intent = new Intent(getApplicationContext(), Inicio_Activity.class);
        startActivity(intent);
        finish();
    }

    public void iniciarSesion(View view) {

        if (isValido()) {

            //Instanciar la Request Queue
            RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);

            //La url a la que se hará un POST para loguear en tu cuenta
            endpoints = new ApiEndpoints();
            String url = endpoints.endpointAPI("/login");

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", pswd);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String nombre = (String) response.get("nombre");
                                String apellidos = (String) response.get("apellidos");
                                String dni = (String) response.get("dni");
                                String fechaNacimiento = (String) response.get("fecha_nacimiento");
                                String email = (String) response.get("email");

                                User user = new User(nombre, apellidos, dni, fechaNacimiento, email);
                                Log.i("onResponse", "Usuario:" + user.getNombre() + user.getApellidos() + user.getDNI() + user.getFecha_nacimiento() + user.getEmail());
                                //Se envian los datos del usuario para que se muestren en su cuenta
                                Intent intent = new Intent(Login_Activity.this, Inicio_Activity2.class);
                                startActivity(intent);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(Login_Activity.this, "Login fallido", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
        }
    }

    public boolean isValido() {

        Validation valid = new Validation();
        boolean valido = true;

        email = etEmail.getText().toString();
        pswd = etPassword.getText().toString();

        if (email.isEmpty() || !valid.correoValido(email)) {
            etEmail.setError("Introduce un email válido");
            etEmail.requestFocus();
            valido = false;
        }

        if (etPassword.length() < 6) {
            etPassword.setError("Debe contener min 6 caracteres");
            etEmail.requestFocus();
            valido = false;
        }
        return valido;
    }
}