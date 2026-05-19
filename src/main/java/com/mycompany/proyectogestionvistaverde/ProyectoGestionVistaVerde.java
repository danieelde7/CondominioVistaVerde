/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectogestionvistaverde;

import com.formdev.flatlaf.FlatDarkLaf;
import ui.PantallaLogin;
import javax.swing.UIManager;

public class ProyectoGestionVistaVerde {

    public static void main(String[] args) {
        // 1. Instalar el tema FlatLaf ANTES de crear cualquier ventana
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            // Si prefieres modo claro, puedes cambiar la línea de arriba por:
            // UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Error al inicializar el diseño FlatLaf");
        }

        // 2. Ejecutar la UI en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PantallaLogin login = new PantallaLogin();
                login.setVisible(true);
            }
        });
    }
}