package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Casa implements Serializable{
    private int numero;
    private Propietario propietario;
    private ArrayList<Pago> pagos; // La lista que guarda los pagos de esta casa

    public Casa(int numero) {
        this.numero = numero;
        this.pagos = new ArrayList<>(); // Inicializamos la lista vacía
    }

    // --- MÉTODOS PARA EL PROPIETARIO ---
    public boolean tienePropietario() {
        return this.propietario != null;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    // --- MÉTODOS PARA LOS PAGOS ---
    public ArrayList<Pago> getPagos() {
        return pagos; // Este es el método que te marcaba en rojo
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
