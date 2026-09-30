package vista;

import servicio.GestorInventario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestorInventario gestor;

    public VentanaPrincipal() {
        gestor = new GestorInventario();
        cargarDatosDemo();

        setTitle("Sistema de Inventario / Almacén");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Productos", new PanelProductos(gestor));
        pestañas.addTab("Movimientos", new PanelMovimientos(gestor));
        pestañas.addTab("Alertas", new PanelAlertas(gestor));
        add(pestañas, BorderLayout.CENTER);

        JLabel estado = new JLabel(" Total inventario: S/ " +
                String.format("%.2f", gestor.valorTotalInventario()));
        estado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(estado, BorderLayout.SOUTH);
    }

    private void cargarDatosDemo() {
        modelo.Categoria cat1 = new modelo.Categoria(1, "Electrónica", "Dispositivos");
        modelo.Categoria cat2 = new modelo.Categoria(2, "Oficina", "Útiles de oficina");
        gestor.agregarProducto(new modelo.Producto("P001", "Mouse Logitech", cat1, 45.0, 20, 5));
        gestor.agregarProducto(new modelo.Producto("P002", "Teclado Mecánico", cat1, 150.0, 8, 10));
        gestor.agregarProducto(new modelo.Producto("P003", "Cuaderno A4", cat2, 5.5, 50, 15));
        gestor.agregarProducto(new modelo.Producto("P004", "Lapicero Azul", cat2, 2.0, 3, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}