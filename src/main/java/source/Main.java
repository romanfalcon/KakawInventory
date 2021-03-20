package source;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        URL url = new File("src/main/java/view/productView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        // create a scene
        Scene sc = new Scene(root);
        URL css = new File("src/main/java/css/style.css").toURI().toURL();

        sc.getStylesheets().add(String.valueOf(css));

        stage.setTitle("KAKAW MID");
        stage.setScene(sc);

//        DB.connect();
//
//
//        List<Product> allProducts = DB.getAllProducts();
//        List<Inventory> allInventory = DB.getAllInventory();
//        allInventory.forEach(inventory -> inventory.setProduct(ProductUtils.filterById(allInventory.get(0).getProductId(),allProducts).get(0)));
//        List<Product> tempProducts = ProductUtils.filterByName("popa",allProducts);
//
//
//        Product p1 = new Product(null,"TEST","TEST","TEST",10,9);
//        Product p2 = DB.getProduct(new ObjectId("60280f8087c4983729e1760e"));
////        DB.insertProduct(p1.toDocument());

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
