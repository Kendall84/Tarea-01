package ucr.algoritmos.tarea01;

/**
 * Subclase de Conversor para convertir Kilómetros a Metros.
 */
public class ConversorKilometros extends Conversor {
    private static final double KILOMETROS_A_METROS = 1000.0;

    @Override
    public double convertir() {
        return valorDeEntrada * KILOMETROS_A_METROS;
    }
}
