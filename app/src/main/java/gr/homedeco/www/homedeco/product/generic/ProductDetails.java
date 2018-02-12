package gr.homedeco.www.homedeco.product.generic;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.product.Product;
import gr.homedeco.www.homedeco.server.callbacks.GetProductCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;

public class ProductDetails extends AppCompatActivity {

    private ImageView imgProductPhoto;
    private TextView tvProductName, tvProductPrice, tvProductDesc;
    private int productID;
    private LocalDatabase localDatabase;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getExtras().getInt("productID");

        imgProductPhoto = (ImageView) findViewById(R.id.imgvProductPhoto);
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        layout = (LinearLayout) findViewById(R.id.activity_product_details);
        localDatabase = new LocalDatabase(this);

        getProductDetails(productID);
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Fetches product's data from the server
     */
    private void getProductDetails(int productID) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchProductDataInBackground(productID, new GetProductCallback() {
            @Override
            public void done(List<Product> returnedList) {
                Product returnedProduct = returnedList.get(0);
                populateView(returnedProduct);
            }
        });
    }

    /**
     * Populates the view with the returned product
     *
     * @param product a Product object
     */
    private void populateView(Product product) {
        String image_url = "http://83.212.101.162/" + product.getImage();
        Picasso.with(this).load(image_url).into(imgProductPhoto);
        tvProductName.setText(product.getName());
        String priceText = String.valueOf(product.getPrice()) + " €";
        tvProductPrice.setText(priceText);
        tvProductDesc.setText(product.getDescription());
    }

    /**
     * Adds product to cart
     * On SUCCESS: Display success message
     *
     * @param view the View containing the button that was clicked
     */
    public void addToCart(View view) {
        localDatabase.addToCart(productID);
        Snackbar snackbar = Snackbar.make(layout, R.string.cart_added_product, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
