package source;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MongoDAO;

import java.io.File;
import java.net.URL;

public class Products extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        URL css = new File("src/main/java/css/style.css").toURI().toURL();

        URL productView = new File("src/main/java/view/InventoryView.fxml").toURI().toURL();
        MongoDAO.connect();
        MongoDAO.inventoryFindOneAndUpdate("602aca82141c7740f1d0ce47",2);
        Scene products = new Scene(FXMLLoader.load(productView));
        products.getStylesheets().add(String.valueOf(css));

        stage.setTitle("Products");
        stage.setScene(products);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
