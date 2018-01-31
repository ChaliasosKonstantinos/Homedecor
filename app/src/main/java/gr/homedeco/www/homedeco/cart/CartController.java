package gr.homedeco.www.homedeco.cart;

import android.content.Context;

import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;

public class CartController {

    private Context context;
    private LocalDatabase localDatabase;

    public CartController(Context context) {
        this.context = context;
        localDatabase = new LocalDatabase(context);
    }

    String getCart() {
        return localDatabase.getCart();
    }

    public void setCartPrice(double price) {
        localDatabase.setCartPrice(price);
    }

    public double getCartPrice() {
        return localDatabase.getCartPrice();
    }

    public boolean clearCart() {
        localDatabase.clearCart();
        String cart = localDatabase.getCart();
        return cart.isEmpty();
    }

}
