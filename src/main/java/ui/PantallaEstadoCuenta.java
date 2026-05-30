package ui;

import model.Casa;
import model.Condominio;
import model.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pantalla 6: Estado de Cuenta
 * Muestra el historial de pagos de una casa específica utilizando un JTable.
 */
public class PantallaEstadoCuenta extends JFrame {

    private Condominio bdCondominio;
    private JComboBox<Integer> cbxNumeroCasa;
    private JLabel lblInfoPropietario;
    private JTable tablaPagos;
    private DefaultTableModel modeloTabla;

    public PantallaEstadoCuenta(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Estado de Cuenta por Casa");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel pnlFondo = new JPanel(new BorderLayout(15, 15));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- ENCABEZADO Y BÚSQUEDA ---
        JPanel pnlNorte = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("Consultar Estado de Cuenta", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#10B981"));
        pnlNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBusqueda.add(new JLabel("Seleccione Casa: "));
        
        Integer[] numeros = new Integer[30];
        for(int i=0; i<30; i++) numeros[i] = i+1;
        cbxNumeroCasa = new JComboBox<>(numeros);
        pnlBusqueda.add(cbxNumeroCasa);

        JButton btnBuscar = new JButton("Buscar Historial");
        btnBuscar.putClientProperty("JButton.buttonType", "roundRect");
        btnBuscar.putClientProperty("FlatLaf.styleClass", "default");
        btnBuscar.addActionListener(e -> buscarEstadoCuenta());
        pnlBusqueda.add(btnBuscar);

        pnlNorte.add(pnlBusqueda, BorderLayout.CENTER);
        
        lblInfoPropietario = new JLabel("Propietario: (Seleccione una casa)", SwingConstants.CENTER);
        lblInfoPropietario.setForeground(Color.GRAY);
        pnlNorte.add(lblInfoPropietario, BorderLayout.SOUTH);

        pnlFondo.add(pnlNorte, BorderLayout.NORTH);

        // --- TABLA DE RESULTADOS ---
        // Definimos las columnas de la tabla
        String[] columnas = {"Mes", "Año", "Monto (Q)", "Estado"};
        
        // El DefaultTableModel es el que maneja los datos de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueamos la tabla para que no puedan editar el texto directamente
            }
        };
        
        tablaPagos = new JTable(modeloTabla);
        tablaPagos.getTableHeader().setReorderingAllowed(false); // Evita que muevan las columnas
        
        // Metemos la tabla en un ScrollPane por si hay muchos pagos
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        pnlFondo.add(scrollPane, BorderLayout.CENTER);

        // --- BOTÓN CERRAR ---
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> this.dispose());
        pnlSur.add(btnCerrar);
        
        pnlFondo.add(pnlSur, BorderLayout.SOUTH);

        add(pnlFondo);
    }

    private void buscarEstadoCuenta() {
        int numCasa = (Integer) cbxNumeroCasa.getSelectedItem();
        Casa casa = bdCondominio.buscarCasaPorNumero(numCasa);

        // Limpiar la tabla antes de meter datos nuevos
        modeloTabla.setRowCount(0);

        // Verificar si la casa tiene dueño
        if (!casa.tienePropietario()) {
            lblInfoPropietario.setText("Propietario: SIN REGISTRO");
            lblInfoPropietario.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Esta casa aún no tiene un propietario asignado.", "Sin Datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mostrar el nombre del dueño
        lblInfoPropietario.setText("Propietario: " + casa.getPropietario().getNombreCompleto() + " | Tel: " + casa.getPropietario().getTelefono());
        lblInfoPropietario.setForeground(Color.decode("#10B981")); // Verde

        // Extraer la lista de pagos de la casa
        ArrayList<Pago> listaPagos = casa.getPagos();

        if (listaPagos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El propietario no tiene pagos registrados.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Llenar la tabla fila por fila
        for (Pago p : listaPagos) {
            Object[] fila = {
                p.getMes(),
                p.getAnio(),
                String.format("%.2f", p.getMonto()), // Formato de moneda con 2 decimales
                p.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
}
