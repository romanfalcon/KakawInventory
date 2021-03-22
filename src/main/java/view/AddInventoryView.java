package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Cart;
import model.CartItem;
import model.MongoDAO;
import model.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddInventoryView implements Initializable {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, Product> productColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;

    @FXML
    private Button doneButton;

    @FXML
    private TextField productFilter;

    @FXML
    private ListView<Product> productList;


    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        doneButton.setOnAction(event -> {
            for (CartItem item : cartItems) {
                MongoDAO.inventoryFindOneAndUpdate(String.valueOf(item.getProduct().getId()),item.getAmount());
            }
            Stage stage = (Stage) doneButton.getScene().getWindow();
            // do what you have to do
            stage.close();
            //Open another
        });


        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setEditable(true);
        quantityColumn.setOnEditCommit(t -> {
            CartItem cartItem =  t.getTableView().getItems().get(t.getTablePosition().getRow());

            Integer newAmount = t.getNewValue();
            Product p = cartItem.getProduct();
            //if new amount is greater than 0
            if(newAmount > 0){
                Cart.changeAmount(p, newAmount);
            }else{
                Cart.removeItem(p);
            }
            cartItems.clear();
            cartItems.addAll(Cart.cartItems);
        });

        MongoDAO.connect();
        List<Product> allProducts = MongoDAO.getAllProducts();

        productList.setOnMouseClicked(event -> {
            if(Cart.addToCart(productList.getSelectionModel().getSelectedItem(),1)){
                cartItems.clear();
                cartItems.addAll(Cart.cartItems);
            }
        });
        products.addAll(allProducts);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(products, product -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        productFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
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


        // 5. Add sorted (and filtered) data to the table.
        productList.setItems(sortedData);
        cartTable.setItems(cartItems);

    }

}
