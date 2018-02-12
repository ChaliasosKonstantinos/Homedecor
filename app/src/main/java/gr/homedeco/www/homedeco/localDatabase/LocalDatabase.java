package gr.homedeco.www.homedeco.localDatabase;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

import gr.homedeco.www.homedeco.user.User;

public class LocalDatabase {

    private static final String PREFS_NAME = "LocalDatabase";
    private SharedPreferences localDatabase;

    public LocalDatabase(Context context) {
        localDatabase = context.getSharedPreferences(PREFS_NAME, 0);
    }

/* =================================== AUTHENTICATION ============================================= */

    /**
     * Logs in / out a user and saves his/her authentication token
     *
     * @param loggedIn TRUE if user will be logged in
     *                 FALSE if user will be logged out
     * @param authToken an alphanumeric authentication token
     *                  SET EMPTY if user will be logged out
     */
    public void setLoggedIn(boolean loggedIn, String authToken) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.putString("authToken", authToken);
        spEditor.apply();
    }

    /**
     * Returns user's authentication token
     *
     * @return user's authentication token
     */
    public String getAuthToken() {
        return localDatabase.getString("authToken", "");
    }

    /**
     * Checks if a user is logged in
     *
     * @return TRUE if user is logged in
     *         FALSE if user is logged out
     */
    public boolean isLoggedIn() {
        return localDatabase.getBoolean("loggedIn", false);
    }

/* ===================================== CART ====================================================== */

    /**
     * Add a product to cart
     *
     * @param productID product's ID
     */
    public void addToCart(int productID) {
        String cart = localDatabase.getString("cart", "");
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cart", cart + String.valueOf(productID) + ",");
        spEditor.apply();
    }

    /**
     * Removes a product from cart
     *
     * @param productID product's ID
     */
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

    /**
     * Returns user's cart
     *
     * @return user's cart
     */
    public String getCart() {
        return localDatabase.getString("cart", "");
    }

    /**
     * Set user's cart price
     *
     * @param price cart's price
     */
    public void setCartPrice(double price) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cartPrice", String.valueOf(price));
        spEditor.apply();
    }

    /**
     * Returns user's cart price
     *
     * @return cart's price
     */
    public double getCartPrice() {
        return Double.parseDouble(localDatabase.getString("cartPrice",""));
    }

    /**
     * Clear user's cart
     */
    public void clearCart() {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cart", "");
        spEditor.apply();
    }

/* ========================================= USER ================================================== */

    /**
     * Set user's details
     *
     * @param user a User object containing the user's details
     */
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

    /**
     * Returns user's details
     *
     * @return a User object containing the user's details
     */
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

    /**
     * Saves user's login credentials
     *
     * @param user a User object containing the user's login credentials
     */
    public void setRememberMe(User user) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("usernameRemember", user.getUsername());
        spEditor.putString("passwordRemember", user.getPassword());
        spEditor.apply();
    }

    /**
     * Returns user's login credentials
     *
     * @return  a User object containing the user's login credentials
     */
    public User getRememberMe() {
        User user = new User();
        user.setUsername(localDatabase.getString("usernameRemember", ""));
        user.setPassword(localDatabase.getString("passwordRemember", ""));
        return user;
    }

/* =========================================== CLEAR =============================================== */

    /**
     * Clears Local storage - USE IT WISELY!!
     */
    public void clearLocalDatabase() {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear().apply();
    }
}
