package es.joseluisgs.dam.examenmayo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HogwartsApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HogwartsApplication.class.getResource("views/hogwarts-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Bienvenido/a a Hogwarts");
        stage.getIcons().add(new Image(Objects.requireNonNull(HogwartsApplication.class.getResource("images/hogwarts.png")).toString()));
        stage.setScene(scene);
        stage.show();
    }
}