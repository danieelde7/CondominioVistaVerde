package ui;

import model.Condominio;
import javax.swing.*;
import java.awt.*;

/**
 * Pantalla 5: Configuración de Cuota Global.
 * Permite modificar el monto de mantenimiento guardado en la base de datos local.
 */
public class PantallaConfigurarCuota extends JFrame {

    private Condominio bdCondominio;
    private JTextField txtNuevaCuota;
    private JLabel lblCuotaActual;

    public PantallaConfigurarCuota(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Configuración de Cuota");
        setSize(400, 260);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel pnlFondo = new JPanel(new BorderLayout(15, 15));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("Ajuste de Cuota Mensual", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#10B981")); // Verde esmeralda
        pnlFondo.add(lblTitulo, BorderLayout.NORTH);

        // --- CUERPO (Información e Input) ---
        JPanel pnlCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        
        // Muestra la cuota en tiempo real leyendo la base de datos global
        lblCuotaActual = new JLabel("Cuota actual vigente: Q" + bdCondominio.getCuotaMantenimiento());
        lblCuotaActual.putClientProperty("FlatLaf.styleClass", "h3");
        lblCuotaActual.setHorizontalAlignment(SwingConstants.CENTER);
        pnlCentro.add(lblCuotaActual);

        // Subpanel para alinear el campo de texto horizontalmente
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlInput.add(new JLabel("Nuevo Monto (Q): "));
        
        txtNuevaCuota = new JTextField(12);
        txtNuevaCuota.putClientProperty("JTextField.placeholderText", "Ej. 1600.00");
        
        // PROGRAMACIÓN DEFENSIVA: Solo permite dígitos y un único punto decimal
        txtNuevaCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                
                // Si no es número y no es punto, se descarta
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
                
                // Bloquear si intentan poner un segundo punto decimal
                if (c == '.' && txtNuevaCuota.getText().contains(".")) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        
        pnlInput.add(txtNuevaCuota);
        pnlCentro.add(pnlInput);

        pnlFondo.add(pnlCentro, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> this.dispose());

        JButton btnGuardar = new JButton("Actualizar Cuota");
        btnGuardar.putClientProperty("JButton.buttonType", "roundRect");
        btnGuardar.putClientProperty("FlatLaf.styleClass", "default");
        btnGuardar.addActionListener(e -> guardarCuota());

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);

        pnlFondo.add(pnlBotones, BorderLayout.SOUTH);
        add(pnlFondo);
    }

    private void guardarCuota() {
        String texto = txtNuevaCuota.getText().trim();
        
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un valor numérico para actualizar la cuota.", 
                "Campo Vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double nuevaCuota = Double.parseDouble(texto);
            
            if (nuevaCuota <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "El monto de la cuota debe ser estrictamente mayor a Q0.00", 
                    "Monto Inválido", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Guardar el nuevo valor en la memoria RAM (Esto es lo que leen las demás pantallas)
            bdCondominio.setCuotaMantenimiento(nuevaCuota);
            
            // 2. Refrescar la etiqueta de esta pantalla de inmediato
            lblCuotaActual.setText("Cuota actual vigente: Q" + bdCondominio.getCuotaMantenimiento());
            
            // 3. Guardar en el archivo .dat para que no se pierda al cerrar el sistema
            logic.GestorDatos.guardar(bdCondominio);
            
            JOptionPane.showMessageDialog(this, 
                "La cuota global se actualizó a Q" + nuevaCuota + " correctamente.", 
                "Configuración Guardada", 
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "El formato del número ingresado es incorrecto.", 
                "Error de Conversión", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}