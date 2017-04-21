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

public class ProductDetails extends AppCompatActivity {

    private ImageView imgProductPhoto;
    private TextView tvProductName, tvProductPrice, tvProductDesc;
    private int productID;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

//        productID = getIntent().getExtras().getInt("productID");

        // Mockup - TODO: Delete it
        Product product = new Product();
        product.setProductID(2);
        product.setSKU("123ABC");
        product.setName("BILLY/OXBERG");
        product.setPrice(149.99);
        product.setDiscountPrice(10);
        product.setWeight(1);
        product.setDescription("Ρυθμιζόμενα ράφια. Προσαρμόστε το χώρο μεταξύ των ραφιών ανάλογα με τις ανάγκες σας.\n" +
                "Η επιφάνεια είναι κατασκευασμένη με όψη φυσικού ξύλου.\n" +
                "Οι ρυθμιζόμενοι μεντεσέδες σάς επιτρέπουν να προσαρμόσετε την πόρτα στη σωστή θέση.\n" +
                "Οι γυάλινες πόρτες προφυλάσσουν τα αγαπημένα σας αντικείμενα από τη σκόνη και επιτρέπουν να είναι ορατά. \n\n" +
                "Ρυθμιζόμενα ράφια. Προσαρμόστε το χώρο μεταξύ των ραφιών ανάλογα με τις ανάγκες σας.\n" +
                "Η επιφάνεια είναι κατασκευασμένη με όψη φυσικού ξύλου.\n" +
                "Οι ρυθμιζόμενοι μεντεσέδες σάς επιτρέπουν να προσαρμόσετε την πόρτα στη σωστή θέση.\n" +
                "Οι γυάλινες πόρτες προφυλάσσουν τα αγαπημένα σας αντικείμενα από τη σκόνη και επιτρέπουν να είναι ορατά.");
        product.setShortDescription("Ρυθμιζόμενα ράφια. Προσαρμόστε το χώρο μεταξύ των ραφιών ανάλογα με τις ανάγκες σας.");
        product.setImage("http://www.ikea.gr/images/250x250/79020482/billy_oxberg_bibliothiki_skoyro_kafe_0.jpg?v=5");
        product.setStock(20);
        product.setCategoryID(1);

        imgProductPhoto = (ImageView) findViewById(R.id.imgvProductPhoto);
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        localDatabase = new LocalDatabase(this);

        populateProductDetails(product);

        //Server Request for specific product details
//        ServerRequests serverRequest = new ServerRequests(this);
//        serverRequest.fetchProductDataInBackground(productID, new GetProductCallback() {
//            @Override
//            public void done(List<Product> returnedList) {
//                Product returnedProduct = returnedList.get(0);
//                populateProductDetails(returnedProduct);
//            }
//        });
    }

    private void populateProductDetails(Product product) {

//        String image_url = "http://83.212.107.169/" + product.getImage();
        String image_url = product.getImage();

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
