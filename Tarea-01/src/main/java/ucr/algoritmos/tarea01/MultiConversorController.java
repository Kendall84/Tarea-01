package ucr.algoritmos.tarea01;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controlador de la interfaz de usuario JavaFX para el Multi-Conversor.
 * Aquí manejamos los eventos de los botones y la entrada de datos.
 */
public class MultiConversorController {

    @FXML
    private TextField txtDolares;
    @FXML
    private Label lblResultadoDolares;

    @FXML
    private TextField txtKilometros;
    @FXML
    private Label lblResultadoKilometros;

    @FXML
    private TextField txtCelsius;
    @FXML
    private Label lblResultadoTemperatura;

    /**
     * Acción del botón de conversión para dólares.
     */
    @FXML
    protected void onBtnDolaresClick() {
        procesarConversion(txtDolares, lblResultadoDolares, new ConversorDolares(), " Colones");
    }

    /**
     * Acción del botón de conversión para kilómetros.
     */
    @FXML
    protected void onBtnKilometrosClick() {
        procesarConversion(txtKilometros, lblResultadoKilometros, new ConversorKilometros(), " Metros");
    }

    /**
     * Acción del botón de conversión para temperatura.
     */
    @FXML
    protected void onBtnTemperaturaClick() {
        procesarConversion(txtCelsius, lblResultadoTemperatura, new ConversorTemperatura(), " °F");
    }

    /**
     * Método genérico para procesar cualquier conversión.
     * Aquí aplicamos polimorfismo (pasando el objeto Conversor)
     * y manejo de excepciones.
     *
     * @param textField El campo de texto de entrada.
     * @param label El label donde mostraremos el resultado.
     * @param conversor El objeto conversor específico a utilizar.
     * @param unidad El texto de la unidad resultante (ej. " Colones").
     */
    private void procesarConversion(TextField textField, Label label, Conversor conversor, String unidad) {
        try {
            // Obtenemos el valor del campo de texto
            double valorEntrada = Double.parseDouble(textField.getText());

            // Usamos el objeto conversor (Polimorfismo en acción)
            conversor.setValorDeEntrada(valorEntrada);
            double resultado = conversor.convertir();

            // Mostramos el resultado en el label correspondiente
            label.setText(String.format("%.2f", resultado) + unidad);
            label.setStyle("-fx-text-fill: black;"); // Color normal si tiene éxito

        } catch (NumberFormatException e) {
            // Manejo de excepciones: si el usuario no mete un número, avisamos amablemente.
            label.setText("Error: Ingrese un número válido.");
            label.setStyle("-fx-text-fill: red;"); // Cambiamos el color a rojo para indicar error
        }
    }
}
