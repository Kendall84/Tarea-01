package ucr.algoritmos.tarea01;

/**
 * Subclase de Conversor para convertir grados Celsius a Fahrenheit.
 */
public class ConversorTemperatura extends Conversor {
    private static final double CELSIUS_A_FAHRENHEIT_FACTOR = 9.0 / 5.0;
    private static final double CELSIUS_A_FAHRENHEIT_OFFSET = 32.0;

    @Override
    public double convertir() {
        return (valorDeEntrada * CELSIUS_A_FAHRENHEIT_FACTOR) + CELSIUS_A_FAHRENHEIT_OFFSET;
    }
}
