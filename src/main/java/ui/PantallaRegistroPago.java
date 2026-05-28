package ui;

import model.Casa;
import model.Condominio;
import model.Pago;

import javax.swing.*;
import java.awt.*;

public class PantallaRegistroPago extends JFrame {

    private Condominio bdCondominio;

    private JComboBox<Integer> cbxNumeroCasa;
    private JComboBox<String> cbxMes;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public PantallaRegistroPago(Condominio bdCondominio) {
        this.bdCondominio = bdCondominio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Pago Mensual");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel pnlFondo = new JPanel(new BorderLayout(10, 10));
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("Registrar Pago de Cuota", SwingConstants.CENTER);
        lblTitulo.putClientProperty("FlatLaf.styleClass", "h2");
        lblTitulo.setForeground(Color.decode("#10B981"));
        pnlFondo.add(lblTitulo, BorderLayout.NORTH);

        // --- FORMULARIO CENTRAL ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 15, 5);

        // 1. Número de Casa
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        pnlForm.add(new JLabel("Número de Casa:"), gbc);
        
        Integer[] numerosCasas = new Integer[30];
        for (int i = 0; i < 30; i++) { numerosCasas[i] = i + 1; }
        cbxNumeroCasa = new JComboBox<>(numerosCasas);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.6;
        pnlForm.add(cbxNumeroCasa, gbc);

        // 2. Mes a pagar
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Mes a Pagar:"), gbc);
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbxMes = new JComboBox<>(meses);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(cbxMes, gbc);

        // Información de Monto Fijo
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel lblMontoInfo = new JLabel("Monto fijo establecido: Q1,500.00", SwingConstants.CENTER);
        lblMontoInfo.setForeground(Color.GRAY);
        pnlForm.add(lblMontoInfo, gbc);

        pnlFondo.add(pnlForm, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> this.dispose());

        btnRegistrar = new JButton("Registrar Pago");
        btnRegistrar.putClientProperty("JButton.buttonType", "roundRect");
        btnRegistrar.putClientProperty("FlatLaf.styleClass", "default");
        btnRegistrar.addActionListener(e -> procesarPago());

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnRegistrar);

        pnlFondo.add(pnlBotones, BorderLayout.SOUTH);
        add(pnlFondo);
    }

    private void procesarPago() {
        int numCasa = (Integer) cbxNumeroCasa.getSelectedItem();
        String mesSeleccionado = (String) cbxMes.getSelectedItem();
        double monto = 1500.00; // Monto fijo automatizado

        Casa casa = bdCondominio.buscarCasaPorNumero(numCasa);

        // Validación: ¿La casa tiene dueño?
        if (!casa.tienePropietario()) {
            JOptionPane.showMessageDialog(this, "La casa #" + numCasa + " no tiene propietario. No se pueden recibir pagos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // --- NUEVO BLOQUE: VALIDACIÓN DE PAGOS A FUTURO ---
        // Asumiendo que tu JComboBox de meses se llama cbxMes
        String[] ordenMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                               "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        int indiceSeleccionado = cbxMes.getSelectedIndex();
        
        // Verificamos que todos los meses anteriores al seleccionado ya estén pagados
        for (int i = 0; i < indiceSeleccionado; i++) {
            String mesRequerido = ordenMeses[i];
            boolean mesPagado = false;
            
            // Buscamos en el historial de la casa si ya existe el pago de ese mes
            for (Pago p : casa.getPagos()) {
                if (p.getMes().equalsIgnoreCase(mesRequerido)) {
                    mesPagado = true;
                    break;
                }
            }
            
            // Si encontramos un mes anterior que NO está pagado, bloqueamos la operación
            if (!mesPagado) {
                JOptionPane.showMessageDialog(this, 
                    "Bloqueo de Sistema: No se puede registrar la cuota de " + cbxMes.getSelectedItem() + ".\n" +
                    "La residencia aún tiene pendiente la cuota de " + mesRequerido + ".", 
                    "Cobro a Futuro Inválido", 
                    JOptionPane.ERROR_MESSAGE);
                return; // Corta la ejecución aquí, no guarda el pago ni manda el correo
            }
        }
        // --- FIN DEL BLOQUE NUEVO ---
        // Validación: ¿Ya pagó este mes?
        for (Pago p : casa.getPagos()) {
            if (p.getMes().equalsIgnoreCase(mesSeleccionado)) {
                JOptionPane.showMessageDialog(this, "El pago de " + mesSeleccionado + " para la casa #" + numCasa + " ya fue registrado.", "Pago Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Crear el registro de pago
        Pago nuevoPago = new Pago(mesSeleccionado, 2026, monto, "Pagado");
        casa.getPagos().add(nuevoPago);

        JOptionPane.showMessageDialog(this, "Pago de Q" + monto + " registrado exitosamente para " + mesSeleccionado + ".", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
       // 1. Guardar el nuevo pago en el archivo .dat (Persistencia)
        logic.GestorDatos.guardar(bdCondominio);

        // 2. Enviar el correo al propietario (Puntos Extra)
        if (casa.tienePropietario()) {
            String correoDueño = casa.getPropietario().getCorreo();
            
            // Ejecutamos el envío en un hilo separado (Thread) para que el sistema 
            // no se quede "congelado" mientras carga y manda el correo por internet.
            new Thread(() -> {
                logic.ServicioCorreo.enviarComprobante(
                    correoDueño, 
                    numCasa, 
                    mesSeleccionado, 
                    2026, 
                    monto
                );
            }).start();
        }

        // 3. Mostrar confirmación en pantalla
        JOptionPane.showMessageDialog(this, 
            "Pago de Q" + monto + " registrado exitosamente.\nSe está enviando el comprobante por correo.", 
            "Registro Exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
            
        this.dispose();
        
        logic.GestorDatos.guardar(bdCondominio);
        this.dispose();
    }
}