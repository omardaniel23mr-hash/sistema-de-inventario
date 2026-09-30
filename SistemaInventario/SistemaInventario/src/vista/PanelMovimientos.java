package vista;

import modelo.Movimiento;
import modelo.TipoMovimiento;
import servicio.GestorInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelMovimientos extends JPanel {
    private GestorInventario gestor;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtCantidad, txtObservacion;
    private JComboBox<TipoMovimiento> cbTipo;

    public PanelMovimientos(GestorInventario gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Registrar Movimiento"));

        form.add(new JLabel("Código Producto:"));
        txtCodigo = new JTextField();
        form.add(txtCodigo);

        form.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(TipoMovimiento.values());
        form.add(cbTipo);

        form.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        form.add(txtCantidad);

        form.add(new JLabel("Observación:"));
        txtObservacion = new JTextField();
        form.add(txtObservacion);

        add(form, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Producto", "Tipo", "Cantidad", "Fecha", "Observación"}, 0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar Movimiento");
        JButton btnRefrescar = new JButton("Refrescar");
        botones.add(btnRegistrar);
        botones.add(btnRefrescar);
        add(botones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrar());
        btnRefrescar.addActionListener(e -> refrescar());

        refrescar();
    }

    private void registrar() {
        try {
            String cod = txtCodigo.getText();
            TipoMovimiento tipo = (TipoMovimiento) cbTipo.getSelectedItem();
            int cant = Integer.parseInt(txtCantidad.getText());
            String obs = txtObservacion.getText();

            if (gestor.registrarMovimiento(cod, tipo, cant, obs)) {
                JOptionPane.showMessageDialog(this, "Movimiento registrado");
                refrescar();
                txtCodigo.setText("");
                txtCantidad.setText("");
                txtObservacion.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error: producto no existe o stock insuficiente");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Movimiento m : gestor.getMovimientos()) {
            modelo.addRow(new Object[]{
                    m.getId(), m.getProducto().getNombre(), m.getTipo(),
                    m.getCantidad(), m.getFechaFormateada(), m.getObservacion()
            });
        }
    }
}