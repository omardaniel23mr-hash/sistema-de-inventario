package vista;

import modelo.Producto;
import servicio.GestorInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelAlertas extends JPanel {
    private GestorInventario gestor;
    private DefaultTableModel modelo;

    public PanelAlertas(GestorInventario gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Productos con Stock Bajo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{"Código", "Nombre", "Stock Actual", "Stock Mínimo", "Faltante"}, 0);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar Alertas");
        btnRefrescar.addActionListener(e -> refrescar());
        add(btnRefrescar, BorderLayout.SOUTH);

        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Producto p : gestor.productosStockBajo()) {
            modelo.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getStock(),
                    p.getStockMinimo(), p.getStockMinimo() - p.getStock()
            });
        }
    }
}