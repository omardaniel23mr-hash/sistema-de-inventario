package persistencia;

import modelo.Categoria;
import modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private static final String ARCHIVO = "productos.txt";

    public void guardar(List<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Producto p : productos) {
                bw.write(p.getCodigo() + ";" + p.getNombre() + ";" +
                         p.getCategoria().getNombre() + ";" + p.getPrecio() + ";" +
                         p.getStock() + ";" + p.getStockMinimo());
                bw.newLine();
            }
        } catch (IOException e) { }
    }

    public List<Producto> cargar() {
        List<Producto> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                Categoria cat = new Categoria(0, partes[2], "");
                Producto p = new Producto(
                        partes[0], partes[1], cat,
                        Double.parseDouble(partes[3]),
                        Integer.parseInt(partes[4]),
                        Integer.parseInt(partes[5]));
                lista.add(p);
            }
        } catch (IOException e) { }
        return lista;
    }
}