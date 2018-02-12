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

import gr.homedeco.www.homedeco.Main;
import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.aboutUs.AboutUs;
import gr.homedeco.www.homedeco.order.creation.OrderCreation;
import gr.homedeco.www.homedeco.order.history.OrderHistory;
import gr.homedeco.www.homedeco.product.Product;
import gr.homedeco.www.homedeco.server.callbacks.GetProductCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.user.UserController;
import gr.homedeco.www.homedeco.user.login.Login;

public class Cart extends AppCompatActivity {

    private double totalPriceBeforeVAT = 0;
    private TextView tvProductPrice, tvProductVAT, tvProductTotalPrice, tvCartIsEmpty;
    private RelativeLayout rlCartPrices;
    private LinearLayout layout;
    private RecyclerView rvCart;
    private CartController controller;
    private UserController uController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        controller = new CartController(this);
        uController = new UserController(this);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductVAT = (TextView) findViewById(R.id.tvProductVAT);
        tvProductTotalPrice = (TextView) findViewById(R.id.tvProductTotalPrice);
        tvCartIsEmpty = (TextView) findViewById(R.id.tvCartIsEmpty);
        rlCartPrices = (RelativeLayout) findViewById(R.id.rlCartPrices);
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        layout = (LinearLayout) findViewById(R.id.activity_cart);
        toggleOrderHistory();
        getCart();
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Display order's history button when user is logged in
     */
    private void toggleOrderHistory() {
        if (uController.isUserLoggedIn()) {
            Button btnOrderHistory = (Button) findViewById(R.id.btnOrderHistory);
            btnOrderHistory.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Fetches product's data from the server
     */
    private void getProducts() {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchProductDataInBackground(0, new GetProductCallback() {
            @Override
            public void done(List<Product> returnedList) {
                String cart = controller.getCart();
                String[] parts = cart.split(",");
                List<Integer> cartIDs = new ArrayList<>();
                // Save parts into a productID list
                for (String part : parts) {
                    cartIDs.add(Integer.parseInt(part));
                }
                createCartProductList(returnedList, cartIDs);
                rlCartPrices.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Fetches user's cart from Local storage
     */
    private void getCart() {
        String cart = controller.getCart();
        if (!cart.isEmpty()) {
            getProducts();
        } else {
            tvCartIsEmpty.setVisibility(View.VISIBLE);
            rlCartPrices.setVisibility(View.GONE);
        }
    }

    /**
     * Creates the cart's product list
     *
     * @param products a list of Products
     * @param IDs a list of product IDs
     */
    private void createCartProductList(List<Product> products, List<Integer> IDs) {
        List<Product> returnedProducts = new ArrayList<>();

        for(int i=0; i < IDs.size(); i++) {
            int id = IDs.get(i);
            returnedProducts.add(products.get(id-3));
        }
        calculateCartPrice(returnedProducts);
    }

    /**
     * Calculates the cart price before VAT
     *
     * @param cartProducts a list of Products
     */
    private void calculateCartPrice(List<Product> cartProducts) {
        for (Product pr : cartProducts) {
            totalPriceBeforeVAT += pr.getPrice();
        }
        populateCartList(cartProducts);
    }

    /**
     * Populates the cart view with given products
     *
     * @param products a list of Product Objects
     */
    private void populateCartList(List<Product> products) {

        CartAdapter adapter = new CartAdapter(products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setAdapter(adapter);
        tvProductPrice.setText(totalPriceBeforeVAT + "€");
        double VAT = (totalPriceBeforeVAT / 100) * 24;
        VAT = Math.floor(VAT * 100) / 100;
        tvProductVAT.setText(VAT + "€");
        double totalPriceAfterVAT = totalPriceBeforeVAT + VAT;
        totalPriceAfterVAT = Math.floor(totalPriceAfterVAT * 100) / 100;
        tvProductTotalPrice.setText(totalPriceAfterVAT + "€");
        controller.setCartPrice(totalPriceAfterVAT);

    }

/* ========================================= LISTENERS =============================================== */

    /**
     * Show a dialog where user can choose if they want to check as guest or registered users
     * On GUEST: Launches checkout activity
     * On REGISTERED USER: Launches login activity
     *
     * @param view the View containing the button that was clicked
     */
    public void checkout(View view) {
        if (uController.isUserLoggedIn()) {
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

    /**
     * Launches Login activity
     */
    private void showLoginDialog() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * Launches Checkout activity
     */
    private void showCheckout() {
        Intent intent = new Intent(this, OrderCreation.class);
        intent.putExtra("type", "generic");
        startActivity(intent);
    }

    /**
     * Launches Order History activity
     */
    public void showOrderHistory(View view) {
        Intent intent = new Intent(this, OrderHistory.class);
        startActivity(intent);
    }

/* ========================================= MENU =============================================== */

    /**
     * Creates Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu_logged_in, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Hides unnecessary menu options
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("USER IS LOGGED IN: " + uController.isUserLoggedIn());
        if (uController.isUserLoggedIn()) {
            menu.findItem(R.id.action_login).setVisible(false);
        } else {
            menu.findItem(R.id.action_logout).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Setting up menu listeners
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_delete_cart:
                boolean isDeleted = controller.clearCart();
                if (isDeleted) {
                    tvCartIsEmpty.setVisibility(View.VISIBLE);
                    rlCartPrices.setVisibility(View.GONE);
                    rvCart.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(layout, R.string.cart_deleted, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
            case R.id.action_login:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.action_logout:
                UserController uc = new UserController(this);
                uc.logoutUser();
                startActivity(new Intent(this, Main.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
