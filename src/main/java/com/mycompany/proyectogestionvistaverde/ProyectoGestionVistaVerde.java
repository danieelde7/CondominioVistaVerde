package com.mycompany.proyectogestionvistaverde;

import logic.GestorDatos;
import model.Condominio;
import ui.PantallaLogin;
import javax.swing.UIManager;

public class ProyectoGestionVistaVerde {

    public static void main(String[] args) {
        
        // 1. Cargar el diseño moderno (FlatLaf Dark Theme)
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf()); 
        } catch (Exception ex) {
            System.err.println("Error al inicializar FlatLaf");
        }

        // 2. ¡AQUÍ USAMOS EL PAQUETE LOGIC!
        // Le pedimos al gestor que busque el archivo .dat en el disco duro.
        // Si no existe, nos devuelve un Condominio nuevo con 30 casas vacías.
        Condominio bdCondominio = GestorDatos.cargar();

        // 3. Arrancamos el sistema abriendo el Login y entregándole la base de datos
        java.awt.EventQueue.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin(bdCondominio);
            login.setVisible(true);
        });
    }
}