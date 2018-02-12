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

    /**
     * Fetches user's cart from Local storage
     *
     * @return user's cart
     */
    public String getCart() {
        return localDatabase.getCart();
    }

    /**
     * Saves user's cart price to Local storage
     */
    public void setCartPrice(double price) {
        localDatabase.setCartPrice(price);
    }

    /**
     * Fetches user's cart price from Local storage
     *
     * @return user's cart price (double)
     */
    public double getCartPrice() {
        return localDatabase.getCartPrice();
    }

    /**
     * Clears user's cart from Local storage
     *
     * @return TRUE if cart was cleared successfully
     *         FALSE if cart was not cleared successfully
     */
    public boolean clearCart() {
        localDatabase.clearCart();
        String cart = localDatabase.getCart();
        return cart.isEmpty();
    }

}
