package ui;

import model.Casa;
import model.Condominio;
import model.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Pantalla 8: Casas Morosas
 * Filtra y muestra las residencias que NO tienen un pago registrado en un mes específico.
 */
public class PantallaCasasMorosas extends JFrame {

    private Condominio bdCondominio;
    private JComboBox<String> cbxMes;
    private JTable tablaMorosos;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalMorosos;

    public PantallaCasasMorosas(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Control de Morosidad");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel pnlFondo = new JPanel(new BorderLayout(15, 15));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- ENCABEZADO Y FILTRO ---
        JPanel pnlNorte = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("Residencias en Mora", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#EF4444")); // Rojo alerta
        pnlNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFiltro.add(new JLabel("Seleccione el Mes a Auditar: "));
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbxMes = new JComboBox<>(meses);
        pnlFiltro.add(cbxMes);

        JButton btnGenerar = new JButton("Buscar Morosos");
        btnGenerar.putClientProperty("JButton.buttonType", "roundRect");
        btnGenerar.putClientProperty("FlatLaf.styleClass", "default");
        btnGenerar.addActionListener(e -> buscarMorosos());
        pnlFiltro.add(btnGenerar);

        pnlNorte.add(pnlFiltro, BorderLayout.CENTER);
        pnlFondo.add(pnlNorte, BorderLayout.NORTH);

        // --- TABLA DE DATOS ---
        String[] columnas = {"No. Casa", "Propietario", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueo de edición manual
            }
        };
        
        tablaMorosos = new JTable(modeloTabla);
        tablaMorosos.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tablaMorosos);
        pnlFondo.add(scrollPane, BorderLayout.CENTER);

        // --- PIE DE PÁGINA (TOTALES Y CIERRE) ---
        JPanel pnlSur = new JPanel(new BorderLayout());
        
        lblTotalMorosos = new JLabel("Total de morosos detectados: 0");
        lblTotalMorosos.putClientProperty("FlatLaf.styleClass", "h3");
        lblTotalMorosos.setForeground(Color.decode("#EF4444"));
        pnlSur.add(lblTotalMorosos, BorderLayout.WEST);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> this.dispose());
        pnlSur.add(btnCerrar, BorderLayout.EAST);
        
        pnlFondo.add(pnlSur, BorderLayout.SOUTH);

        add(pnlFondo);
    }

    private void buscarMorosos() {
        String mesSeleccionado = (String) cbxMes.getSelectedItem();
        
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        int contadorMorosos = 0;

        // Escanear todas las casas
        for (Casa casa : bdCondominio.getListaCasas()) {
            boolean pagoEncontrado = false;

            // Revisar el historial de pagos de esta casa
            for (Pago p : casa.getPagos()) {
                if (p.getMes().equalsIgnoreCase(mesSeleccionado)) {
                    pagoEncontrado = true;
                    break; // Ya pagó, no seguimos buscando
                }
            }

            // Si terminamos de revisar sus pagos y NO encontramos el de este mes, es moroso
            if (!pagoEncontrado) {
                String nombreDueño = casa.tienePropietario() ? casa.getPropietario().getNombreCompleto() : "Sin Asignar";
                String telefono = casa.tienePropietario() ? casa.getPropietario().getTelefono() : "N/A";
                
                Object[] fila = {
                    casa.getNumero(),
                    nombreDueño,
                    telefono,
                    "Pendiente de Pago"
                };
                
                modeloTabla.addRow(fila);
                contadorMorosos++;
            }
        }

        // Actualizar la interfaz
        lblTotalMorosos.setText("Total de morosos detectados: " + contadorMorosos);

        if (contadorMorosos == 0) {
            JOptionPane.showMessageDialog(this, 
                "¡Excelente! Todas las casas están solventes en el mes de " + mesSeleccionado + ".", 
                "Cero Morosidad", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}