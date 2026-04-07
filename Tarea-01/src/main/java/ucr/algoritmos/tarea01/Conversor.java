package ucr.algoritmos.tarea01;

/**
 * Clase abstracta base para todos los conversores.
 * Demuestra el concepto de abstracción y herencia.
 */
public abstract class Conversor {
    protected double valorDeEntrada;

    public void setValorDeEntrada(double valorDeEntrada) {
        this.valorDeEntrada = valorDeEntrada;
    }

    /**
     * Método abstracto que debe ser implementado por las subclases
     * para realizar la lógica de conversión específica.
     * @return El valor convertido.
     */
    public abstract double convertir();
}
