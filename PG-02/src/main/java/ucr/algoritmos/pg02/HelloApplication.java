package ucr.algoritmos.pg02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // La ruta del recurso debe incluir el paquete si los archivos FXML
        // están dentro de una subcarpeta en resources.
        URL fxmlLocation = HelloApplication.class.getResource("/ucr/algoritmos/pg02/main.fxml");

        if (fxmlLocation == null) {
            throw new RuntimeException("No se encontró main.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);

        URL cssLocation = HelloApplication.class.getResource("/ucr/algoritmos/pg02/styles.css");
        if (cssLocation != null) {
            scene.getStylesheets().add(cssLocation.toExternalForm());
        }

        stage.setTitle("PG-02");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
