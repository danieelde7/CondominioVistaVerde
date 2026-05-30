package logic;

/**
 * Clase encargada de centralizar la lógica de negocio y las validaciones del sistema.
 */
public class Validador {

    public static boolean esNombreValido(String nombre) {
        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("\\d{8}");
    }

    public static boolean esCorreoValido(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return correo.matches(regex);
    }

    public static double calcularMetaEsperada(double cuotaMensual, int cantidadCasas) {
        return cuotaMensual * cantidadCasas;
    }
}