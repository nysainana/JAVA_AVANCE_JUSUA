package com.java.avance.javaavancem12024;

import com.java.avance.javaavancem12024.ui.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static StackPane root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/main.fxml"));
        App.root = fxmlLoader.load();
        Main mainController = fxmlLoader.getController();
        Scene scene = new Scene(App.root, 1024, 750);
        stage.setTitle("Java avancée");
        stage.setScene(scene);
        mainController.getLabelAppName().textProperty().bind(stage.titleProperty());

        stage.setOnCloseRequest((event) -> {
            event.consume();
            mainController.quiter();
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}