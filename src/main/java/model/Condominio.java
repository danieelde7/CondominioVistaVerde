package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Condominio: Administra las 30 casas y la configuración global del sistema.
 * Actúa como nuestra "base de datos" centralizada en memoria.
 */
public class Condominio implements Serializable {
    
    private ArrayList<Casa> listaCasas;
    private double cuotaMantenimiento = 1500.00; // Cuota global dinámica

    // Constructor: Inicializa el condominio con exactamente 30 casas del 1 al 30
    public Condominio() {
        this.listaCasas = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            this.listaCasas.add(new Casa(i));
        }
    }

    // --- MÉTODOS DE BÚSQUEDA ---
    
    /**
     * Busca una residencia dentro del ArrayList según su identificador numérico.
     */
    public Casa buscarCasaPorNumero(int numero) {
        for (Casa casa : listaCasas) {
            if (casa.getNumero() == numero) {
                return casa;
            }
        }
        return null; 
    }

    // --- GETTERS Y SETTERS ---

    public ArrayList<Casa> getListaCasas() {
        return listaCasas;
    }

    public double getCuotaMantenimiento() {
        return cuotaMantenimiento;
    }

    public void setCuotaMantenimiento(double cuotaMantenimiento) {
        this.cuotaMantenimiento = cuotaMantenimiento;
    }
}
