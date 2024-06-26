package org.program.preciousstonemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.program.preciousstonemanager.controllers.abstractcontrollers.SceneWithTablesAndGoBackController;
import org.program.preciousstonemanager.models.Stone;
import org.program.preciousstonemanager.models.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FindByTransparencyController extends SceneWithTablesAndGoBackController {
    @FXML
    private TextField lowerBorderTextField, upperBorderTextField;

    @FXML
    public void initialize() {
        fxmlFileName = "FindByTransparencyScene";
        logger.info("Ініціалізація сцени: " + fxmlFileName);
    }

    /**
     * @param event
     * @throws IOException
     */
    @Override
    public void switchToThisScene(ActionEvent event) throws IOException {
        logger.info("перехід на сцену: " + fxmlFileName);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/program/preciousstonemanager/" + fxmlFileName + ".fxml")));
        root = loader.load();
        FindByTransparencyController controller = loader.getController();
        controller.setStorage(this.storage);
        controller.showStones();
        controller.setPreviousSceneName(this.previousSceneName);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     */
    public void findByTransparency() {
        logger.info("Знаходження каменів за прозорістю");
        int lowerBorder = Integer.parseInt(lowerBorderTextField.getText());
        int upperBorder = Integer.parseInt(upperBorderTextField.getText());
        List<Stone> tempStorage = findStones(storage, lowerBorder, upperBorder);
        tempStorage.sort(Comparator.comparingInt(Stone::getTransparency));
        stonesTable.setItems(FXCollections.observableList(tempStorage));
    }

    /**
     * @param event
     * @throws IOException
     */
    @Override
    public void goBack(ActionEvent event) throws IOException {
        logger.info("Перехід на попередню сцену: " + fxmlFileName);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/program/preciousstonemanager/" + previousSceneName + ".fxml")));
        loader.load();
        SceneWithTablesAndGoBackController sceneWithTablesAndGoBackController = loader.getController();
        sceneWithTablesAndGoBackController.setStorage(Storage.getCollection());
        sceneWithTablesAndGoBackController.switchToThisScene(event);
    }

    /**
     * @param storage
     * @param lowerBorder
     * @param upperBorder
     * @return
     */
    private List<Stone> findStones(List<Stone> storage, int lowerBorder, int upperBorder) {
        logger.info("Пошук потрібних каменів");
        List<Stone> tempStorage = new ArrayList<>();
        for (Stone stone : storage) {
            if (stone.getTransparency() >= lowerBorder && stone.getTransparency() <= upperBorder) {
                tempStorage.add(stone);
            }
        }
        return tempStorage;
    }
}
