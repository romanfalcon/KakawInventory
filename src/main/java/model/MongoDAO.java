package model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MongoDAO {
    private static final String DB_NAME= "KakawDB";
    private static final String KEYS_PATH= "src/main/resources/keys.txt";
    private static String dbConnection;
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Inventory> allInventory = new ArrayList<>();


    public static void connect(){
        readKeysFile();
        //connecting....
        mongoClient = new MongoClient(new MongoClientURI(dbConnection));
        database = mongoClient.getDatabase(DB_NAME);
    }

    private static void readKeysFile() {
        if(dbConnection==null){
            try {
                File file = new File(KEYS_PATH);
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    dbConnection = reader.nextLine();
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public static void close(){
        mongoClient.close();
    }

    public static MongoCollection<Document> getCollection(String collectionName){
       return database.getCollection(collectionName);
    }

    public static List<Product> getAllProducts(){
        if(products.isEmpty()){
            MongoCollection<Document> collection = getCollection(CollectionNames.Products.name());
            MongoCursor<Document> cursor = collection.find().cursor();
            while(cursor.hasNext()){
                Document document = cursor.next();
                Product product = new Product(document);
                products.add(product);
            }
        }
        return products;
    }

    public static List<Inventory> getAllInventory(){
        if(allInventory.isEmpty()){
            MongoCollection<Document> collection = getCollection(CollectionNames.Inventory.name());
            MongoCursor<Document> cursor = collection.find().cursor();
            while(cursor.hasNext()){
                Document document = cursor.next();
                Inventory inventory = new Inventory(document);
                allInventory.add(inventory);
            }
        }
        return allInventory;
    }

    //Actualiza la qty de un objeto en el inventario y tambien actualiza la lista local que hace referencia a la base de datos
    public static void inventoryFindOneAndUpdate(String productId, int toAddInStock){
        MongoCollection<Document> collection = getCollection(CollectionNames.Inventory.name());

        // find one document with new Document
        List<Inventory> inventory = getAllInventory();
        Optional<Inventory> optionalItem = inventory.stream().findFirst().filter(inventory1 -> inventory1.getProductId().equals(new ObjectId(productId)));

        if(optionalItem.isPresent()){
            Inventory toUpdate = optionalItem.get();
            toUpdate.addStock(toAddInStock);
            UpdateResult a = collection.updateOne(new Document("_id", toUpdate.getId()),new Document("$set", new Document("inStock", toUpdate.getInStock())));
        }else{
            Inventory i = new Inventory();
            i.setInStock(toAddInStock);
            i.setProductId(new ObjectId(productId));
            collection.insertOne(i.toDocument());
        }

    }

    public static Product getProduct(ObjectId id){
        MongoCollection<Document> collection = getCollection(CollectionNames.Products.name());
        BsonDocument query = new BsonDocument();
        query.append("_id", new BsonObjectId(id));
        MongoCursor<Document> cursor = collection.find(query).cursor();
        if(cursor.hasNext()){
            Document document = cursor.next();
            return new Product(document);
        }
        return null;
    }

    public static void insertProduct(Document product){
        getCollection(CollectionNames.Products.name()).insertOne(product);
    }

    public static void insertManyProducts(List<Document> products){
        getCollection(CollectionNames.Products.name()).insertMany(products);
    }

}
