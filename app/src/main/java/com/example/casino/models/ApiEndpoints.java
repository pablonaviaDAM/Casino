package com.example.casino.models;

public class ApiEndpoints {

    //Comprobar que coincide con el localhost del cmd!!!!!
    //Si estas usando wifi: Adaptador de LAN inalámbrica Wi-Fi
    //Comprobar que el emulador tenga wifi
    private static final String BASE_URL = "http://192.168.1.34:8080/api/v1/user";

    public String endpointAPI(String endpoint) { //Para usar los endpoints se debera añadir "/" + "palabra clave del endpoint"

        String URL = BASE_URL + endpoint;

        return URL; // Ejemplo endpoint: BASE_URL + "/login"
    }
}
