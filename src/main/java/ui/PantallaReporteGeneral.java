package ui;

import model.Casa;
import model.Condominio;
import model.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Pantalla 7: Reporte General
 * Escanea las 30 casas y muestra los ingresos totales de un mes específico.
 */
public class PantallaReporteGeneral extends JFrame {

    private Condominio bdCondominio;
    private JComboBox<String> cbxMes;
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalRecaudado;

    public PantallaReporteGeneral(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Reporte General de Ingresos");
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
        
        JLabel lblTitulo = new JLabel("Reporte de Ingresos Mensuales", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#10B981"));
        pnlNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFiltro.add(new JLabel("Seleccione el Mes: "));
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbxMes = new JComboBox<>(meses);
        pnlFiltro.add(cbxMes);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.putClientProperty("JButton.buttonType", "roundRect");
        btnGenerar.putClientProperty("FlatLaf.styleClass", "default");
        btnGenerar.addActionListener(e -> generarReporte());
        pnlFiltro.add(btnGenerar);

        pnlNorte.add(pnlFiltro, BorderLayout.CENTER);
        pnlFondo.add(pnlNorte, BorderLayout.NORTH);

        // --- TABLA DE DATOS ---
        String[] columnas = {"No. Casa", "Propietario", "Estado", "Monto Pagado (Q)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueo de edición manual
            }
        };
        
        tablaReportes = new JTable(modeloTabla);
        tablaReportes.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        pnlFondo.add(scrollPane, BorderLayout.CENTER);

        // --- PIE DE PÁGINA (TOTALES Y CIERRE) ---
        JPanel pnlSur = new JPanel(new BorderLayout());
        
        lblTotalRecaudado = new JLabel("Total Recaudado: Q0.00");
        lblTotalRecaudado.putClientProperty("FlatLaf.styleClass", "h3");
        lblTotalRecaudado.setForeground(Color.decode("#10B981"));
        pnlSur.add(lblTotalRecaudado, BorderLayout.WEST);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> this.dispose());
        pnlSur.add(btnCerrar, BorderLayout.EAST);
        
        pnlFondo.add(pnlSur, BorderLayout.SOUTH);

        add(pnlFondo);
    }

    private void generarReporte() {
        String mesSeleccionado = (String) cbxMes.getSelectedItem();
        
        // 1. Limpiar tabla y variables
        modeloTabla.setRowCount(0);
        double totalSuma = 0.0;
        int contadorPagos = 0;

        // 2. Escanear toda la base de datos (Las 30 casas)
        for (Casa casa : bdCondominio.getListaCasas()) {
            for (Pago p : casa.getPagos()) {
                // Si encontramos un pago que coincida con el mes buscado
                if (p.getMes().equalsIgnoreCase(mesSeleccionado)) {
                    
                    String nombreDueño = casa.tienePropietario() ? casa.getPropietario().getNombreCompleto() : "Sin Registrar";
                    
                    Object[] fila = {
                        casa.getNumero(),
                        nombreDueño,
                        p.getEstado(),
                        String.format("%.2f", p.getMonto())
                    };
                    
                    modeloTabla.addRow(fila);
                    totalSuma += p.getMonto(); // Vamos acumulando el dinero
                    contadorPagos++;
                    break; // Pasamos a la siguiente casa
                }
            }
        }

        // 3. Actualizar la interfaz con los resultados
        if (contadorPagos == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron pagos registrados para el mes de " + mesSeleccionado + ".", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            lblTotalRecaudado.setText("Total Recaudado: Q0.00");
        } else {
            lblTotalRecaudado.setText("Total Recaudado: Q" + String.format("%.2f", totalSuma));
        }
    }
}