package kel4;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    public static void changeScene(Stage stage, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error saat mengganti scene: " + e.getMessage());
        }
    }
}

