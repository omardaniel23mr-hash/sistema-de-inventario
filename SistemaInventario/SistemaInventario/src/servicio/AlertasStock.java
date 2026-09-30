package servicio;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class AlertasStock {
    public List<Producto> obtenerProductosBajos(List<Producto> productos) {
        List<Producto> alertas = new ArrayList<>();
        for (Producto p : productos) {
            if (p.stockBajo()) alertas.add(p);
        }
        return alertas;
    }
}