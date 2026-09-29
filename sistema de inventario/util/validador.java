package util;

public class Validador {
    public static boolean esNumero(String texto) {
        try { Double.parseDouble(texto); return true; }
        catch (NumberFormatException e) { return false; }
    }

    public static boolean esEnteroPositivo(String texto) {
        try {
            int n = Integer.parseInt(texto);
            return n > 0;
        } catch (NumberFormatException e) { return false; }
    }

    public static boolean esTextoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}