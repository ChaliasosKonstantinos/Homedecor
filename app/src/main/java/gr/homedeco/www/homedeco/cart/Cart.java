package gr.homedeco.www.homedeco.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.aboutUs.AboutUs;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.order.creation.OrderCreation;
import gr.homedeco.www.homedeco.order.history.OrderHistory;
import gr.homedeco.www.homedeco.product.Product;
import gr.homedeco.www.homedeco.server.callbacks.GetProductCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.user.login.Login;

public class Cart extends AppCompatActivity {

    private LocalDatabase localDatabase;
    private double totalPriceBeforeVAT = 0;
    private TextView tvProductPrice, tvProductVAT, tvProductTotalPrice, tvCartIsEmpty;
    private RelativeLayout rlCartPrices;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        localDatabase = new LocalDatabase(this);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductVAT = (TextView) findViewById(R.id.tvProductVAT);
        tvProductTotalPrice = (TextView) findViewById(R.id.tvProductTotalPrice);
        tvCartIsEmpty = (TextView) findViewById(R.id.tvCartIsEmpty);
        rlCartPrices = (RelativeLayout) findViewById(R.id.rlCartPrices);
        layout = (LinearLayout) findViewById(R.id.activity_cart);
        toggleOrderHistory();
        getProducts();
    }

//------------------------------------- MENU -------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu_logged_in, menu);
        return super.onCreateOptionsMenu(menu);
    }

//------------------------------------- HELPERS -------------------------------------------------//

    // Toggles enable/disable Order History button based on if user is logged in
    private void toggleOrderHistory() {
        if (localDatabase.isLoggedIn()) {
            Button btnOrderHistory = (Button) findViewById(R.id.btnOrderHistory);
            btnOrderHistory.setEnabled(true);
        }
    }

    // Retrieves product from the server
    private void getProducts() {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchProductDataInBackground(0, new GetProductCallback() {
            @Override
            public void done(List<Product> returnedList) {
                getCart(returnedList);
            }
        });
    }

    // Retrieves the cart from local database
    private void getCart(List<Product> products) {
        String cart = localDatabase.getCart();
        if (!cart.isEmpty()) {
            String[] parts = cart.split(",");
            List<Integer> cartIDs = new ArrayList<>();
            // Save parts into a productID list
            for (String part : parts) {
                cartIDs.add(Integer.parseInt(part));
            }
            makeProductList(products, cartIDs);
            rlCartPrices.setVisibility(View.VISIBLE);
        } else {
            tvCartIsEmpty.setVisibility(View.VISIBLE);
            rlCartPrices.setVisibility(View.GONE);
        }
    }

    // Creates a list with cart's given product IDs
    private void makeProductList(List<Product> products, List<Integer> IDs) {
        List<Product> returnedProducts = new ArrayList<>();

        for(int i=0; i < IDs.size(); i++) {
            int id = IDs.get(i);
            returnedProducts.add(products.get(id-3));
            totalPriceBeforeVAT += products.get(id-3).getPrice();
        }
        populateCartList(returnedProducts);
    }

    // Populates the UI
    private void populateCartList(List<Product> products) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvCart);
        CartAdapter adapter = new CartAdapter(products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        tvProductPrice.setText(totalPriceBeforeVAT + "€");
        double VAT = (totalPriceBeforeVAT / 100) * 24;
        VAT = Math.floor(VAT * 100) / 100;
        tvProductVAT.setText(VAT + "€");
        double totalPriceAfterVAT = totalPriceBeforeVAT + VAT;
        totalPriceAfterVAT = Math.floor(totalPriceAfterVAT * 100) / 100;
        tvProductTotalPrice.setText(totalPriceAfterVAT + "€");
        localDatabase.setCartPrice(totalPriceAfterVAT);

    }

//------------------------------------- LISTENERS -------------------------------------------------//

    // Checkout
    public void checkout(View view) {
        if (localDatabase.isLoggedIn()) {
            showCheckout();
        } else {
            AlertDialog builder = new AlertDialog.Builder(Cart.this).create();
            LayoutInflater inflater = Cart.this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.activity_checkout_dialog, null));
            builder.setButton(AlertDialog.BUTTON_NEUTRAL, "Χρηστης",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showLoginDialog();
                        }
                    });
            builder.setButton(AlertDialog.BUTTON_POSITIVE, "Επισκεπτης",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showCheckout();
                        }
                    });
            builder.show();
        }
    }

    // Dialog for checkout
    private void showLoginDialog() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    // Navigates to cart
    private void showCheckout() {
        Intent intent = new Intent(this, OrderCreation.class);
        startActivity(intent);
    }

    // Navigates to order history
    public void showOrderHistory(View view) {
        Intent intent = new Intent(this, OrderHistory.class);
        startActivity(intent);
    }

    // MENU: Login
    public void showLogin(MenuItem item) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    // MENU: Logout
    public void logout(MenuItem item) {
        localDatabase.setLoggedIn(false, "");
    }

    // MENU: Delete cart
    public void deleteCart(MenuItem item) {
        localDatabase.clearCart();
        Snackbar snackbar = Snackbar.make(layout, R.string.cart_deleted, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    // MENU: About us
    public void showAboutUs(MenuItem item) {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }
}
