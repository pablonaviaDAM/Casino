package com.example.casino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registro_Activity2 extends AppCompatActivity {

    private EditText etEmail, etContrasenya, etContrasenya2;
    private FirebaseAuth mAuth;
    private String email, pswd, pswd2, nombre, apellidos, DNI, fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        etEmail = findViewById(R.id.etEmail);
        etContrasenya = findViewById(R.id.etContrasenya);
        etContrasenya2 = findViewById(R.id.etContrasenya2);
        mAuth = FirebaseAuth.getInstance();

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
        if (pswd.isEmpty()) {
            etContrasenya.setError("Este campo no puede estar vacio.");
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
            mAuth.createUserWithEmailAndPassword(email, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            // Recogemos los datos del anterior activity y creamos un usuario con esos datos
                            recibirDatos();
                            User user = new User(nombre, apellidos, DNI, fecha, email, pswd);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Registro_Activity2.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(Registro_Activity2.this, "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Registro_Activity2.this, "Usuario no autenticado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Registro_Activity2.this, "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}