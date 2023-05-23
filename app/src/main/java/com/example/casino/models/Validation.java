package com.example.casino.models;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String EMAIL_REGEX
            = "^[_A-Za-z\\d-+]+(\\.[_A-Za-z\\d-]+)*@"
            + "[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$";
    private static final String DNI_REGEX = "\\d{8}[A-HJ-NP-TV-Z]";
    private static final String LETRAS_REGEX = "^[a-zA-Z\\s]+$";
    private static final String FECHA_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";


    //METODO PARA VALIDAR EL CORREO HACIENDO USO DE LAS CLASES PATTERN Y MATCHER
    public boolean correoValido(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //VALIDA QUE EL FORMATO DE DNI 8 NUMEROS Y UNA LETRA
    public boolean dniValido(String dni) {
        Pattern pattern = Pattern.compile(DNI_REGEX);
        Matcher matcher = pattern.matcher(dni);
        return matcher.matches();
    }

    //VALIDA QUE EL TEXTO QUE SE INTRODUCE SEA SOLO LETRAS
    public boolean textoLetras(String soloLetras) {
        Pattern pattern = Pattern.compile(LETRAS_REGEX);
        Matcher matcher = pattern.matcher(soloLetras);
        return matcher.matches();
    }

    //VALIDA QUE LA FECHA INTRODUCIDA TENGA EL FORMATO "dd/mm/yyyy"
    public boolean fechaValida(String fecha) {
        Pattern pattern = Pattern.compile(FECHA_REGEX);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.matches();
    }

    public boolean contrasenyasIguales(@NonNull String pswd, String pswd2) {
        return pswd.equals(pswd2);
    }
}
