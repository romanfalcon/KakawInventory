package model;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

//POJO for the Products db collection
public class Product{

    private ObjectId id;
    private String name;
    private String type;
    private String quantity;
    private double sellPrice;
    private double buyPrice;

    public Product(ObjectId id, String name, String type, String quantity, double sellPrice, double buyPrice) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }
    public Product(){

    }

    public Product(Document document) {
        this.id = (ObjectId) document.get("_id");
        this.name = (String) document.get("name");
        this.type = (String) document.get("type");
        this.quantity = (String) document.get("quantity");
        this.sellPrice = (Double) document.get("sellPrice");
        this.buyPrice = (Double) document.get("buyPrice");
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", getName());
        document.append("type", getType());
        document.append("quantity", getQuantity());
        document.append("sellPrice", getSellPrice());
        document.append("buyPrice", getBuyPrice());
        return document;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return name +
                ", " + type +
                ", " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.sellPrice, sellPrice) == 0 &&
                Double.compare(product.buyPrice, buyPrice) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(type, product.type) &&
                Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, quantity, sellPrice, buyPrice);
    }
}
