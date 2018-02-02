package gr.homedeco.www.homedeco.localDatabase;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

import gr.homedeco.www.homedeco.user.User;

public class LocalDatabase {

    private static final String PREFS_NAME = "LocalDatabase";
    private SharedPreferences localDatabase;

    //Define of local Database
    public LocalDatabase(Context context) {
        localDatabase = context.getSharedPreferences(PREFS_NAME, 0);
    }

/* =================================== AUTHENTICATION ============================================= */

    // Set the user as logged in
    public void setLoggedIn(boolean loggedIn, String authToken) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.putString("authToken", authToken);
        spEditor.apply();
    }

    // Retrieve logged user's auth token
    public String getAuthToken() {
        return localDatabase.getString("authToken", "");
    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return localDatabase.getBoolean("loggedIn", false);
    }

/* ===================================== CART ====================================================== */

    // Add a product to cart
    public void addToCart(int productID) {
        String cart = localDatabase.getString("cart", "");
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cart", cart + String.valueOf(productID) + ",");
        spEditor.apply();
    }

    // Remove a product from cart
    public void removeFromCart(int productID) {
        String cart = localDatabase.getString("cart", "");
        if (!cart.isEmpty()) {
            String[] parts = cart.split(",");
            String newCart = "";
            for (String part : parts) {
                if (!Objects.equals(part, String.valueOf(productID))) {
                    newCart += part + ",";
                }
            }
            SharedPreferences.Editor spEditor = localDatabase.edit();
            spEditor.putString("cart", newCart);
            spEditor.apply();
        }
    }

    // Get user's cart
    public String getCart() {
        return localDatabase.getString("cart", "");
    }

    // Set cart's price
    public void setCartPrice(double price) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cartPrice", String.valueOf(price));
        spEditor.apply();
    }

    // Get cart's price
    public double getCartPrice() {
        return Double.parseDouble(localDatabase.getString("cartPrice",""));
    }

    // Empty the cart
    public void clearCart() {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cart", "");
        spEditor.apply();
    }

/* ========================================= USER ================================================== */

    // Set user's details
    public void setUserDetails(User user) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("username", user.getUsername());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("firstName", user.getFirstName());
        spEditor.putString("lastName", user.getLastName());
        spEditor.putString("birthday", user.getBirthday());
        spEditor.putString("address", user.getAddress());
        spEditor.putString("postalCode", user.getPostalCode());
        spEditor.putString("city", user.getCity());
        spEditor.putString("state", user.getState());
        spEditor.putString("country", user.getCountry());
        spEditor.putString("phone", user.getPhone());
        spEditor.putString("mobilePhone", user.getMobilePhone());
        spEditor.apply();
    }

    // Get user's details
    public User getUserDetails() {
        User user = new User();
        user.setUsername(localDatabase.getString("username",""));
        user.setFirstName(localDatabase.getString("firstName",""));
        user.setLastName(localDatabase.getString("lastName",""));
        user.setEmail(localDatabase.getString("email",""));
        user.setPhone(localDatabase.getString("phone",""));
        user.setMobilePhone(localDatabase.getString("mobilePhone",""));
        user.setCountry(localDatabase.getString("country",""));
        user.setState(localDatabase.getString("state",""));
        user.setCity(localDatabase.getString("city",""));
        user.setAddress(localDatabase.getString("address",""));
        user.setPostalCode(localDatabase.getString("postalCode",""));

        return user;
    }

    // Set remember me credentials
    public void setRememberMe(User user) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("usernameRemember", user.getUsername());
        spEditor.putString("passwordRemember", user.getPassword());
        spEditor.apply();
    }

    // Get remember me credentials
    public User getRememberMe() {
        User user = new User();
        user.setUsername(localDatabase.getString("usernameRemember", ""));
        user.setPassword(localDatabase.getString("passwordRemember", ""));
        return user;
    }

/* =========================================== CLEAR =============================================== */

    // Clears local user Database
    public void clearLocalDatabase() {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear().apply();
    }
}
