package com.example.casino;

public class User {


    private String nombre;
    private String apellidos;
    private String DNI;
    private String fechaNacimiento;
    private String email;
    private String contraseña;

    public User() {
    }


    public User(String nombre, String apellidos,
                String DNI, String fechaNacimiento,
                String email, String contraseña) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.contraseña = contraseña;
    }
}
