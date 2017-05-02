package gr.homedeco.www.homedeco;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private ImageView imgProductPhoto;
    private TextView tvProductName, tvProductPrice, tvProductDesc;
    private int productID;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getExtras().getInt("productID");

        imgProductPhoto = (ImageView) findViewById(R.id.imgvProductPhoto);
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        localDatabase = new LocalDatabase(this);

        //Server Request for specific product details
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchProductDataInBackground(productID, new GetProductCallback() {
            @Override
            public void done(List<Product> returnedList) {
                Product returnedProduct = returnedList.get(0);
                populateProductDetails(returnedProduct);
            }
        });
    }

    private void populateProductDetails(Product product) {

        String image_url = "http://83.212.101.162/" + product.getImage();

        Picasso.with(this).load(image_url).into(imgProductPhoto);
        tvProductName.setText(product.getName());
        String priceText = String.valueOf(product.getPrice()) + " €";
        tvProductPrice.setText(priceText);
        tvProductDesc.setText(product.getDescription());
    }

    public void addToCart(View view) {
        localDatabase.addToCart(productID);
        Toast.makeText(getApplicationContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
    }

    public void checkout(View view) {
        AlertDialog builder = new AlertDialog.Builder(ProductDetails.this).create();
        LayoutInflater inflater = ProductDetails.this.getLayoutInflater();
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
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void showLoginDialog() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
