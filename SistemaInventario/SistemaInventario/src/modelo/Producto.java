package modelo;

public class Producto {
    private String codigo;
    private String nombre;
    private Categoria categoria;
    private double precio;
    private int stock;
    private int stockMinimo;

    public Producto(String codigo, String nombre, Categoria categoria,
                    double precio, int stock, int stockMinimo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean stockBajo() { return stock <= stockMinimo; }
    public double valorTotal() { return stock * precio; }

    @Override
    public String toString() { return codigo + " - " + nombre; }
}