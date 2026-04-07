package ucr.algoritmos.tarea01;

/**
 * Subclase de Conversor para convertir de Dólares a Colones.
 */
public class ConversorDolares extends Conversor {
    // Tipo de cambio fijo para fines del ejemplo
    private static final double TIPO_DE_CAMBIO = 520.0;

    @Override
    public double convertir() {
        return valorDeEntrada * TIPO_DE_CAMBIO;
    }
}
