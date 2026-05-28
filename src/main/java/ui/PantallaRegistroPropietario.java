package ui;

import model.Casa;
import model.Condominio;
import model.Propietario;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla 3: Formulario de Registro de Propietario.
 * Cuenta con programación defensiva extrema para evitar el ingreso de datos erróneos.
 */
public class PantallaRegistroPropietario extends JFrame {

    private Condominio bdCondominio;

    private JTextField txtNombre;
    private JComboBox<Integer> cbxNumeroCasa;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public PantallaRegistroPropietario(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Propietario");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        // DISPOSE_ON_CLOSE asegura que al cerrar esta ventana no se cierre todo el programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    }

    private void inicializarComponentes() {
        JPanel pnlFondo = new JPanel(new BorderLayout(10, 10));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("Nuevo Propietario", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#10B981")); // Verde corporativo
        pnlFondo.add(lblTitulo, BorderLayout.NORTH);

        // --- FORMULARIO CENTRAL ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        // 1. Campo Nombre Completo
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnlForm.add(new JLabel("Nombre Completo:"), gbc);
        
        txtNombre = new JTextField();
        // FILTRO EN TIEMPO REAL: Solo permite letras y espacios
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ') {
                    e.consume(); // Cancela la acción de escribir el carácter
                    Toolkit.getDefaultToolkit().beep(); // Sonido de advertencia discreto
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        pnlForm.add(txtNombre, gbc);

        // 2. Desplegable de Número de Casa (1 al 30)
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Número de Casa:"), gbc);
        
        Integer[] numerosCasas = new Integer[30];
        for (int i = 0; i < 30; i++) {
            numerosCasas[i] = i + 1;
        }
        cbxNumeroCasa = new JComboBox<>(numerosCasas);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(cbxNumeroCasa, gbc);

        // 3. Campo Teléfono
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Teléfono:"), gbc);
        
        txtTelefono = new JTextField();
        txtTelefono.putClientProperty("JTextField.placeholderText", "Ej. 55443322");
        // FILTRO EN TIEMPO REAL: Solo permite números y máximo 8 dígitos
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtTelefono.getText().length() >= 8) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 2;
        pnlForm.add(txtTelefono, gbc);

        // 4. Campo Correo Electrónico
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Correo Electrónico:"), gbc);
        
        txtCorreo = new JTextField();
        txtCorreo.putClientProperty("JTextField.placeholderText", "usuario@dominio.com");
        gbc.gridx = 1; gbc.gridy = 3;
        pnlForm.add(txtCorreo, gbc);

        pnlFondo.add(pnlForm, BorderLayout.CENTER);

        // --- BOTONES INFERIORES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> this.dispose()); // Cierra únicamente esta ventana

        btnRegistrar = new JButton("Registrar Propietario");
        btnRegistrar.putClientProperty("JButton.buttonType", "roundRect");
        btnRegistrar.putClientProperty("FlatLaf.styleClass", "default");
        btnRegistrar.addActionListener(e -> registrarPropietario());

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnRegistrar);

        pnlFondo.add(pnlBotones, BorderLayout.SOUTH);
        add(pnlFondo);
    }

private void registrarPropietario() {
        // Extracción de datos eliminando espacios extraños al inicio/final
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        int numCasa = (Integer) cbxNumeroCasa.getSelectedItem();

        // VALIDACIÓN 1: Campos obligatorios vacíos
        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios para efectuar el alta.", 
                "Campos Incompletos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // VALIDACIÓN 2: Bloqueo anti Ctrl+V para el Nombre
        // Exige que estrictamente desde el inicio (^) hasta el final ($) solo haya letras y espacios
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, 
                "El nombre no puede contener números ni caracteres especiales.", 
                "Nombre Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDACIÓN 3: Bloqueo anti Ctrl+V para el Teléfono (Exactamente 8 números)
        // \\d significa "dígitos" y {8} significa "exactamente ocho"
        if (!telefono.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono debe constar de exactamente 8 dígitos numéricos.", 
                "Teléfono Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDACIÓN 4: Formato de correo electrónico estándar
        String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!correo.matches(regexCorreo)) {
            JOptionPane.showMessageDialog(this, 
                "El formato del correo electrónico ingresado no es válido (ejemplo@dominio.com).", 
                "Correo Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar la casa seleccionada
        Casa casaSeleccionada = bdCondominio.buscarCasaPorNumero(numCasa);

        // RESTRICCIÓN DE NEGOCIO: Validar duplicación
        if (casaSeleccionada.tienePropietario()) {
            JOptionPane.showMessageDialog(this, 
                "La residencia #" + numCasa + " ya posee un propietario registrado en el sistema.", 
                "Conflicto de Registro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creación del registro
        Propietario nuevoPropietario = new Propietario(nombre, numCasa, telefono, correo);
        casaSeleccionada.setPropietario(nuevoPropietario);

        JOptionPane.showMessageDialog(this, 
            "Propietario asignado correctamente a la Casa #" + numCasa, 
            "Alta Completada", 
            JOptionPane.INFORMATION_MESSAGE);
         
        logic.GestorDatos.guardar(bdCondominio);
        this.dispose(); 
    }
}
