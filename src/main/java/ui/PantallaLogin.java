package ui;

import model.Condominio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pantalla de acceso al sistema con diseño moderno FlatLaf.
 */
public class PantallaLogin extends JFrame {

    private final Condominio bdCondominio;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public PantallaLogin(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Login - Vista Verde");
        setSize(450, 350); // Tamaño ideal para que respire la interfaz
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout y márgenes generosos
        JPanel pnlFondo = new JPanel(new BorderLayout(15, 15));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("Acceso al Sistema", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2"); // Estilo FlatLaf
        lblTitulo.setForeground(Color.decode("#10B981")); // Color verde esmeralda
        pnlFondo.add(lblTitulo, BorderLayout.NORTH);

        // --- FORMULARIO CENTRAL ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 10, 12, 10); // Separación vertical de los campos

        // 1. Campo Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(new JLabel("Usuario:"), gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        pnlForm.add(txtUsuario, gbc);

        // 2. Campo Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Contraseña:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(txtPassword, gbc);

        pnlFondo.add(pnlForm, BorderLayout.CENTER);

        // --- BOTÓN DE INGRESO ---
        JButton btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.putClientProperty("FlatLaf.styleClass", "large"); // Hace el botón más robusto
        btnIngresar.setBackground(Color.decode("#10B981"));
        btnIngresar.setForeground(Color.WHITE);

        // Acción al presionar el botón o dar Enter
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        pnlFondo.add(btnIngresar, BorderLayout.SOUTH);

        // Agregar contenedor principal a la ventana
        add(pnlFondo);
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Credenciales fijas de administración
        if (usuario.equals("iusr_vistaverde") && password.equals("R3sidencial2026%")) {
            // Cierra la pantalla de Login
            this.dispose(); 
            
            // Abre el Menú Principal entregándole los datos cargados
            java.awt.EventQueue.invokeLater(() -> {
                new PantallaInicio(bdCondominio).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Error de Autenticación", 
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}