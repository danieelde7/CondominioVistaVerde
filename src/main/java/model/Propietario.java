package model;

import java.io.Serializable;
public class Propietario implements Serializable{
    private String nombreCompleto;
    private int numeroCasa;
    private String telefono;
    private String correo;

    public Propietario(String nombreCompleto, int numeroCasa, String telefono, String correo) {
        this.nombreCompleto = nombreCompleto;
        this.numeroCasa = numeroCasa;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (Casa " + numeroCasa + ") - Tel: " + telefono;
    }
}
