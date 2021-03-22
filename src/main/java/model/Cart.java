package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public static ArrayList<CartItem> cartItems = new ArrayList<>();

    public static boolean addToCart(Product product, int amount){
        for (CartItem item : cartItems) {
            if(product.getId().equals(item.getProduct().getId())){
                return false;
            }
        }
        cartItems.add(new CartItem(product,amount));
        return true;
    }

    public static void clearCart(){
        cartItems.clear();
    }

    public static void changeAmount(Product product, int newAmount){
        for (CartItem item: cartItems) {
            if(item.getProduct().getId().equals(product.getId())){
                item.setAmount(newAmount);
                break;
            }
        }
    }

    public static void removeItem(Product product){
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(product.getId()));
    }
}
