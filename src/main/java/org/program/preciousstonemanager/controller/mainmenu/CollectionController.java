package org.program.preciousstonemanager.controller.mainmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import org.program.preciousstonemanager.controller.abstractcontrollers.storage.StorageController;
import org.program.preciousstonemanager.controller.collection.ChangeStoneController;
import org.program.preciousstonemanager.controller.collection.FindByTransparencyCollectionController;
import org.program.preciousstonemanager.database.DatabaseWorker;
import org.program.preciousstonemanager.stones.Stone;
import org.program.preciousstonemanager.stones.Storage;

import java.io.IOException;
import java.util.Objects;

public class CollectionController extends StorageController {
    @Override
    public void initialize() {
        fxmlFileName = "CollectionScene";
        storage = Storage.collection;
    }

    public void findStoneByTransparency(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/program/preciousstonemanager/FindByTransparencyCollectionScene.fxml")));
        loader.load();
        FindByTransparencyCollectionController findByTransparencyCollectionController = loader.getController();
        findByTransparencyCollectionController.switchToThisScene(event);
    }

    public void changeStone(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/program/preciousstonemanager/ChangeStoneScene.fxml")));
        loader.load();
        ChangeStoneController changeStoneController = loader.getController();
        ChangeStoneController.setSelectedStone(stonesTable.getSelectionModel().getSelectedItem());
        changeStoneController.switchToThisScene(event);
    }

    public void deleteStone(ActionEvent event) throws IOException {
        Stone selectedStone = stonesTable.getSelectionModel().getSelectedItem();
        stonesTable.getItems().remove(selectedStone);
        Storage.collection.remove(selectedStone);
        DatabaseWorker.deleteStone(selectedStone);
    }
}
