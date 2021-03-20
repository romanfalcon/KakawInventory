package model;

import org.bson.Document;
import org.bson.types.ObjectId;

//POJO for the Inventory db collection
public class Inventory{

    private ObjectId id;
    private ObjectId productId;
    private Product product;
    private int inStock;

    public Inventory(ObjectId id, ObjectId productId, int inStock, Product product) {
        this.id = id;
        this.productId = productId;
        this.inStock = inStock;
        this.product = product;
    }
    public Inventory(){
    }

    public Inventory(Document document) {
        this.id = (ObjectId) document.get("_id");
        this.productId = (ObjectId) document.get("productId");
        this.inStock = (int) document.get("inStock");
        this.product = MongoDAO.getAllProducts().stream().filter(product1 -> product1.getId().equals(productId)).findFirst().get();
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("productId", getProductId());
        document.append("inStock", getInStock());
        return document;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void addStock(int newStock) {
        this.inStock += newStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return product.getName() + " - Cantidad : " + inStock;
    }

}
