package gr.homedeco.www.homedeco;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class LocalDatabase {

    private static final String PREFS_NAME = "LocalDatabase";
    private SharedPreferences localDatabase;

    //Define of local Database
    public LocalDatabase(Context context) {
        localDatabase = context.getSharedPreferences(PREFS_NAME, 0);
    }

    // Set the user as logged in
    public void setLoggedIn(boolean loggedIn, String authToken) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.putString("authToken", authToken);
        spEditor.apply();
    }

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

    // Get user's username
    public String getUsername() {
        return localDatabase.getString("username", "");
    }

    // Stores a list of all the products
    public void saveProducts(List<Product> products) {

    }

    // Add to user's cart
    public void addToCart(int productID) {
        String cart = localDatabase.getString("cart", "");
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("cart", cart + String.valueOf(productID) + ",");
        spEditor.apply();
    }

    // Get user's cart
    public String getCart() {
        return localDatabase.getString("cart", "");
    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return localDatabase.getBoolean("loggedIn", false);
    }

    // Clears local user Database
    public void clearLocalDatabase() {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear().apply();
    }

    public String getAuthToken() {
        return localDatabase.getString("authToken", "");
    }

    public void setRememberMe(User user) {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("usernameRemember", user.getUsername());
        spEditor.putString("passwordRemember", user.getPassword());
        spEditor.apply();
    }
}
