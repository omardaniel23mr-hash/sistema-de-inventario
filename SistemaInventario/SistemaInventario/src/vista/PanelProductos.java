package vista;

import modelo.Categoria;
import modelo.Producto;
import servicio.GestorInventario;
import util.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProductos extends JPanel {
    private GestorInventario gestor;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock, txtStockMin;
    private JComboBox<String> cbCategoria;

    public PanelProductos(GestorInventario gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 4, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        form.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        form.add(txtCodigo);

        form.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        form.add(txtNombre);

        form.add(new JLabel("Categoría:"));
        cbCategoria = new JComboBox<>(new String[]{"Electrónica", "Oficina", "Alimentos", "Ropa"});
        form.add(cbCategoria);

        form.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        form.add(txtPrecio);

        form.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        form.add(txtStock);

        form.add(new JLabel("Stock Mínimo:"));
        txtStockMin = new JTextField();
        form.add(txtStockMin);

        add(form, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{"Código", "Nombre", "Categoría", "Precio", "Stock", "Stock Mín.", "Valor Total"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        add(botones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        refrescarTabla();
    }

    private void agregarProducto() {
        if (Validador.esTextoVacio(txtCodigo.getText()) ||
            Validador.esTextoVacio(txtNombre.getText()) ||
            !Validador.esNumero(txtPrecio.getText()) ||
            !Validador.esEnteroPositivo(txtStock.getText()) ||
            !Validador.esEnteroPositivo(txtStockMin.getText())) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
            return;
        }

        Categoria cat = new Categoria(0, cbCategoria.getSelectedItem().toString(), "");
        Producto p = new Producto(
                txtCodigo.getText(), txtNombre.getText(), cat,
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText()),
                Integer.parseInt(txtStockMin.getText()));

        if (gestor.agregarProducto(p)) {
            JOptionPane.showMessageDialog(this, "Producto agregado");
            refrescarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Código ya existe");
        }
    }

    private void actualizarProducto() {
        if (tabla.getSelectedRow() == -1) return;
        String codigo = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
        if (gestor.actualizarProducto(codigo, txtNombre.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStockMin.getText()))) {
            JOptionPane.showMessageDialog(this, "Actualizado");
            refrescarTabla();
        }
    }

    private void eliminarProducto() {
        if (tabla.getSelectedRow() == -1) return;
        String codigo = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar " + codigo + "?") == JOptionPane.YES_OPTION) {
            gestor.eliminarProducto(codigo);
            refrescarTabla();
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtStockMin.setText("");
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);
        for (Producto p : gestor.getProductos()) {
            modelo.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getCategoria().getNombre(),
                    p.getPrecio(), p.getStock(), p.getStockMinimo(),
                    String.format("%.2f", p.valorTotal())
            });
        }
    }
}