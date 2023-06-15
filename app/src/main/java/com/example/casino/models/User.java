package com.example.casino.models;

public class User {


    private String nombre;
    private String apellidos;
    private String DNI;
    private String fecha_nacimiento;
    private String email;
    private String password;

    public User() {
    }


    public User(String nombre, String apellidos,
                String DNI, String fecha_nacimiento,
                String email, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
        this.password = password;
    }
    public User(String nombre, String apellidos,
                String DNI, String fecha_nacimiento,
                String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
