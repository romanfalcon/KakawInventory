package source;

import model.MongoDAO;
import model.Product;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadProducts {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/roman.falcon/Desktop/ProductosKakaw.csv";

        ArrayList<Document> docs = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] rowParts = row.split(",");
            Product p = new Product();
            p.setName(rowParts[0]);
            p.setType(rowParts[1]);
            p.setQuantity(rowParts[2]);
            p.setSellPrice(Double.parseDouble(rowParts[3]));
            p.setBuyPrice(Double.parseDouble(rowParts[4]));
            docs.add(p.toDocument());
        }
        csvReader.close();
        MongoDAO.connect();
        MongoDAO.insertManyProducts(docs);
    }
}
