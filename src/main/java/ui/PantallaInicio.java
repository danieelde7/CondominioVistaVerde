package ui;

import model.Condominio;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Pantalla 2: Menú Principal (Dashboard)
 * Diseñada para conectar todos los módulos del sistema pasando la base de datos.
 */
public class PantallaInicio extends JFrame {

    // Variable global que almacena la base de datos en memoria
    private Condominio bdCondominio;

    // Constructor que exige recibir la base de datos centralizada
    public PantallaInicio(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Dashboard - Vista Verde");
        setSize(750, 520); // Un poco más de espacio para comodidad visual
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Panel principal con márgenes limpios
        JPanel pnlFondo = new JPanel(new BorderLayout(20, 20));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // --- ENCABEZADO (Bienvenida y Período Actual) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        
        JLabel lblBienvenida = new JLabel("Bienvenido, Administrador");
        lblBienvenida.putClientProperty("FlatLaf.styleClass", "h1");
        lblBienvenida.setForeground(Color.decode("#10B981")); // Verde Esmeralda Premium
        
        // Generar mes y año de forma dinámica con el reloj del sistema
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoMesAnio = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
        String fechaTexto = fechaActual.format(formatoMesAnio).toUpperCase();

        JLabel lblFecha = new JLabel("Período fiscal: " + fechaTexto);
        lblFecha.putClientProperty("FlatLaf.styleClass", "h3");
        lblFecha.setForeground(UIManager.getColor("Label.disabledForeground"));

        pnlHeader.add(lblBienvenida, BorderLayout.NORTH);
        pnlHeader.add(lblFecha, BorderLayout.SOUTH);
        pnlFondo.add(pnlHeader, BorderLayout.NORTH);

        // --- PANEL DE MENÚ (Cuadrícula de 2 filas x 3 columnas) ---
        JPanel pnlBotones = new JPanel(new GridLayout(2, 3, 20, 20));
        
        // Botón 1: Registro de Propietario (Módulo completamente funcional)
        JButton btnPropietario = crearBotonMenu("Registro Propietario", "Dar de alta a dueños de casas del 1 al 30");
        btnPropietario.addActionListener(e -> {
            // Instanciar el formulario heredando la base de datos
            PantallaRegistroPropietario pantallaRegistro = new PantallaRegistroPropietario(bdCondominio);
            pantallaRegistro.setVisible(true);
        });

        // Botones restantes (Se activarán conforme desarrollemos las siguientes pantallas)
        // Botón 2: Registro de Pago
        JButton btnPago = crearBotonMenu("Registro de Pago", "Registrar abonos mensuales de cuotas");
        // Le quitamos el mensajito de "En construcción" y le ponemos la acción real
        for(java.awt.event.ActionListener al : btnPago.getActionListeners()) {
            btnPago.removeActionListener(al); 
        }
        btnPago.addActionListener(e -> {
            PantallaRegistroPago pantallaPago = new PantallaRegistroPago(bdCondominio);
            pantallaPago.setVisible(true);
        });
        // Botón 3: Configurar Cuota
        JButton btnCuota = crearBotonMenu("Configurar Cuota", "Establecer el monto de mantenimiento global");
        for(java.awt.event.ActionListener al : btnCuota.getActionListeners()) {
            btnCuota.removeActionListener(al); 
        }
        btnCuota.addActionListener(e -> {
            PantallaConfigurarCuota pantallaCuota = new PantallaConfigurarCuota(bdCondominio);
            pantallaCuota.setVisible(true);
        });
        // Botón 4: Estado de Cuenta
        JButton btnEstado = crearBotonMenu("Estado de Cuenta", "Ver historial de pagos detallado de una casa");
        for(java.awt.event.ActionListener al : btnEstado.getActionListeners()) {
            btnEstado.removeActionListener(al); 
        }
        btnEstado.addActionListener(e -> {
            PantallaEstadoCuenta pantallaEstado = new PantallaEstadoCuenta(bdCondominio);
            pantallaEstado.setVisible(true);
        });
        // Botón 5: Reporte General
        JButton btnReporte = crearBotonMenu("Reporte General", "Ver la tabla resumida de ingresos del mes");
        for(java.awt.event.ActionListener al : btnReporte.getActionListeners()) {
            btnReporte.removeActionListener(al); 
        }
        btnReporte.addActionListener(e -> {
            PantallaReporteGeneral pantallaReporte = new PantallaReporteGeneral(bdCondominio);
            pantallaReporte.setVisible(true);
        });
        // Botón 6: Casas Morosas
        JButton btnMorosos = crearBotonMenu("Casas Morosas", "Lista de residencias pendientes de pago");
        for(java.awt.event.ActionListener al : btnMorosos.getActionListeners()) {
            btnMorosos.removeActionListener(al); 
        }
        btnMorosos.addActionListener(e -> {
            PantallaCasasMorosas pantallaMorosos = new PantallaCasasMorosas(bdCondominio);
            pantallaMorosos.setVisible(true);
        });

        // Añadir todos los bloques al contenedor ordenadamente
        pnlBotones.add(btnPropietario);
        pnlBotones.add(btnPago);
        pnlBotones.add(btnCuota);
        pnlBotones.add(btnEstado);
        pnlBotones.add(btnReporte);
        pnlBotones.add(btnMorosos);

        pnlFondo.add(pnlBotones, BorderLayout.CENTER);

        // --- PIE DE PÁGINA COMERCIAL ---
        JLabel lblFooter = new JLabel("Residenciales Vista Verde S.A. | Panel de Control de Alta Seguridad", SwingConstants.CENTER);
        lblFooter.putClientProperty("FlatLaf.styleClass", "small");
        lblFooter.setForeground(UIManager.getColor("Label.disabledForeground"));
        pnlFondo.add(lblFooter, BorderLayout.SOUTH);

        add(pnlFondo);
    }

    /**
     * Generador estándar de botones con diseño unificado para el Dashboard
     */
    private JButton crearBotonMenu(String titulo, String descripcionCorta) {
        JButton btn = new JButton(titulo);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        // Estilos avanzados heredados de FlatLaf
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano al pasar por encima
        btn.setToolTipText(descripcionCorta); // Mensaje de ayuda emergente
        
        // Comportamiento genérico para botones en desarrollo
        if (!titulo.equals("Registro Propietario")) {
            btn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, 
                    "El " + titulo + " estará disponible en la próxima sesión de desarrollo.", 
                    "Módulo en construcción", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
        }
        
        return btn;
    }
}