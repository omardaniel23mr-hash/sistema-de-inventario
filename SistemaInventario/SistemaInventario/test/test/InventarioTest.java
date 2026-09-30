package test;

import modelo.*;
import org.junit.Before;
import org.junit.Test;
import servicio.GestorInventario;

import static org.junit.Assert.*;

public class InventarioTest {

    private GestorInventario gestor;
    private Categoria categoria;

    @Before
    public void setUp() {
        gestor = new GestorInventario();
        categoria = new Categoria(1, "Prueba", "Categoría de prueba");
        gestor.agregarProducto(new Producto("P001", "Producto A", categoria, 10.0, 20, 5));
    }

    @Test
    public void testAgregarProducto() {
        assertTrue(gestor.agregarProducto(new Producto("P002", "Producto B", categoria, 15.0, 10, 3)));
    }

    @Test
    public void testNoDuplicarCodigo() {
        assertFalse(gestor.agregarProducto(new Producto("P001", "Dup", categoria, 20.0, 5, 2)));
    }

    @Test
    public void testBuscarProducto() {
        assertNotNull(gestor.buscarProducto("P001"));
        assertNull(gestor.buscarProducto("NOEXISTE"));
    }

    @Test
    public void testEntradaAumentaStock() {
        gestor.registrarMovimiento("P001", TipoMovimiento.ENTRADA, 10, "Compra");
        assertEquals(30, gestor.buscarProducto("P001").getStock());
    }

    @Test
    public void testSalidaDisminuyeStock() {
        gestor.registrarMovimiento("P001", TipoMovimiento.SALIDA, 5, "Venta");
        assertEquals(15, gestor.buscarProducto("P001").getStock());
    }

    @Test
    public void testSalidaSinStockSuficiente() {
        assertFalse(gestor.registrarMovimiento("P001", TipoMovimiento.SALIDA, 100, "Exceso"));
    }

    @Test
    public void testStockBajo() {
        Producto p = gestor.buscarProducto("P001");
        p.setStock(3);
        assertTrue(p.stockBajo());
    }

    @Test
    public void testValorTotalInventario() {
        assertEquals(200.0, gestor.valorTotalInventario(), 0.01);
    }

    @Test
    public void testEliminarProducto() {
        assertTrue(gestor.eliminarProducto("P001"));
        assertNull(gestor.buscarProducto("P001"));
    }
}