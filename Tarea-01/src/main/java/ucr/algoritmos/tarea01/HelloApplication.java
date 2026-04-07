package ucr.algoritmos.tarea01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargamos la nueva vista del Multi-Conversor
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multi-conversor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 450);
        stage.setTitle("Multi-Conversor de Grupo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
