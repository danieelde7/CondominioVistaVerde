package logic;

import model.Condominio;
import java.io.*;

/**
 * Clase encargada de la persistencia de datos mediante Serialización.
 * Guarda todo el Condominio en un archivo local para no perder información.
 */
public class GestorDatos {
    
    // El nombre del archivo invisible que se guardará en tu computadora
    private static final String ARCHIVO = "BD_VistaVerde.dat";

    // Método para GUARDAR
    public static void guardar(Condominio bdCondominio) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            salida.writeObject(bdCondominio);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Método para CARGAR
    public static Condominio cargar() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (Condominio) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Si es la primera vez que se corre el programa y no hay archivo, 
            // se crea un condominio nuevo desde cero con sus 30 casas vacías.
            return new Condominio(); 
        }
    }
}
