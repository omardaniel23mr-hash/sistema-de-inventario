package servicio;

import modelo.Movimiento;
import modelo.Producto;
import modelo.TipoMovimiento;

import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    private List<Producto> productos;
    private List<Movimiento> movimientos;
    private int contadorMovimientos;

    public GestorInventario() {
        this.productos = new ArrayList<>();
        this.movimientos = new ArrayList<>();
        this.contadorMovimientos = 1;
    }

    public boolean agregarProducto(Producto p) {
        if (buscarProducto(p.getCodigo()) != null) return false;
        productos.add(p);
        return true;
    }

    public boolean eliminarProducto(String codigo) {
        Producto p = buscarProducto(codigo);
        if (p == null) return false;
        productos.remove(p);
        return true;
    }

    public boolean actualizarProducto(String codigo, String nombre,
                                      double precio, int stockMinimo) {
        Producto p = buscarProducto(codigo);
        if (p == null) return false;
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setStockMinimo(stockMinimo);
        return true;
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) return p;
        }
        return null;
    }

    public List<Producto> getProductos() { return productos; }

    public boolean registrarMovimiento(String codigoProducto,
                                       TipoMovimiento tipo,
                                       int cantidad,
                                       String observacion) {
        Producto p = buscarProducto(codigoProducto);
        if (p == null || cantidad <= 0) return false;
        if (tipo == TipoMovimiento.SALIDA && p.getStock() < cantidad) return false;

        if (tipo == TipoMovimiento.ENTRADA) {
            p.setStock(p.getStock() + cantidad);
        } else {
            p.setStock(p.getStock() - cantidad);
        }

        Movimiento m = new Movimiento(contadorMovimientos++, p, tipo, cantidad, observacion);
        movimientos.add(m);
        return true;
    }

    public List<Movimiento> getMovimientos() { return movimientos; }

    public double valorTotalInventario() {
        double total = 0;
        for (Producto p : productos) total += p.valorTotal();
        return total;
    }

    public List<Producto> productosStockBajo() {
        return new AlertasStock().obtenerProductosBajos(productos);
    }
}