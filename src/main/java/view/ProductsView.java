package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.MongoDAO;
import model.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsView implements Initializable {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label search_label;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, String> quantityColumn;

    @FXML
    private TableColumn<Product, Double> sellPriceColumn;

    @FXML
    private TableColumn<Product, Double> buyPriceColumn;

    @FXML
    private TextField searchTextField;



    //observalble list to store data
    private final ObservableList<Product> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));

        MongoDAO.connect();

        List<Product> allProducts = MongoDAO.getAllProducts();

        dataList.addAll(allProducts);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(dataList, product -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            // If filter text is empty, display all persons.

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches name.
            } else if (product.getType().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches type.
            } else if (product.getQuantity().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches qty.
            }
            else
                return false; // Does not match.
        }));

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        productTableView.setItems(sortedData);


    }
}
