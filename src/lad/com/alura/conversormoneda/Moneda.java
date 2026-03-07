package lad.com.alura.conversormoneda;

public class Moneda {

    private String monedaBase;
    private String monedaDestino;
    private double monto;
    private double resultado;

    public Moneda(String monedaBase, String monedaDestino, double monto, double resultado) {
        this.monedaBase = monedaBase;
        this.monedaDestino = monedaDestino;
        this.monto = monto;
        this.resultado = resultado;
    }

    public String getMonedaBase() {
        return monedaBase;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public double getResultado() {
        return resultado;
    }
}