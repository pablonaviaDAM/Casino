package com.example.casino.models;

public class User {


    private String nombre;
    private String apellidos;
    private String DNI;
    private String fechaNacimiento;
    private String email;
    private String password;

    public User() {
    }


    public User(String nombre, String apellidos,
                String DNI, String fechaNacimiento,
                String email, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.password = password;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
