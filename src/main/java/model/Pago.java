package model;

import java.io.Serializable;
public class Pago implements Serializable {
    private String mes;
    private int anio;
    private double monto;
    private String estado;

    // Constructor que exige la pantalla
    public Pago(String mes, int anio, double monto, String estado) {
        this.mes = mes;
        this.anio = anio;
        this.monto = monto;
        this.estado = estado;
    }

    // Método para poder leer el mes en la validación
    public String getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }
}