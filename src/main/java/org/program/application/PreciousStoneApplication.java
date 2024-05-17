package org.program.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.program.execution.DatabaseWorker;
import org.program.stones.Stone;
import org.program.stones.Storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PreciousStoneApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/program/application/MainMenuScene.fxml")));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Objects.requireNonNull(PreciousStoneApplication.class.getResourceAsStream("/org/program/files/images/icon.png"))));
        stage.setTitle("Precious Stone Controller");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(PreciousStoneApplication.class.getClassLoader().getResourceAsStream("log4j.properties"));
        Logger logger = Logger.getLogger(DatabaseWorker.class);
        logger.info("Початок роботи програми");

        Storage.initializeStorage();

        launch(args);
    }
}