package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Inventory;
import model.MongoDAO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class InventoryView implements Initializable {

    @FXML
    private ListView<Inventory> inventoryList;

    @FXML
    private Button addToInventory;

    //observalble list to store data
    private final ObservableList<Inventory> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MongoDAO.connect();

        List<Inventory> allInventory = MongoDAO.getAllInventory();
        observableList.setAll(allInventory.stream().filter(inventory -> inventory.getInStock()>0).collect(Collectors.toList()));
        inventoryList.setItems(observableList);

//        Open a window where you can select products and add to inventory
//        addToInventory.setOnAction(e->);
    }
}
