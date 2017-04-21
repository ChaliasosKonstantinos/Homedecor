package gr.homedeco.www.homedeco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private LocalDatabase localDatabase;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        localDatabase = new LocalDatabase(this);
        context = getApplicationContext();
        getCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getCart() {
        // Get cart
        String cart = localDatabase.getCart();
        String[] parts = cart.split(",");

        System.out.println("DIKO MOU!");
        for (String part : parts) {
            System.out.println(part);
        }

        // Mockup - TODO: Delete this
        List<Product> returnedList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductID(1);
        product1.setSKU("123ABC");
        product1.setName("BRIMNES");
        product1.setPrice(119);
        product1.setDiscountPrice(10);
        product1.setWeight(1);
        product1.setDescription("Αυτό το έπιπλο πρέπει να στερεώνεται στον τοίχο, με την βοήθεια του εξαρτήματος στερέωσης στον τοίχο που περιλαμβάνεται στην συσκευασία. Τα διαφορετικά υλικά τοίχου, απαιτούν και διαφορετικούς τύπους μηχανισμών στερέωσης. Χρησιμοποιείτε μηχανισμούς, οι οποίοι να είναι οι κατάλληλοι για τους τοίχους του σπιτιού σας, πωλούνται χωριστά. Μπορεί να συνδυαστεί και με άλλα έπιπλα της σειράς των προϊόντων BRIMNES. ");
        product1.setShortDescription("Οι πόρτες σάς επιτρέπουν να κρύψετε τα πράγματά σας και να τα προστατέψετε από τη σκόνη");
        product1.setImage("http://www.ikea.gr/images/250x250/00300664/brimnes_ntoylapi_me_portes_gyali_mayro_0.jpg?v=1");
        product1.setStock(35);
        product1.setCategoryID(1);

        Product product2 = new Product();
        product2.setProductID(2);
        product2.setSKU("123ABC");
        product2.setName("ALEX");
        product2.setPrice(79.99);
        product2.setDiscountPrice(10);
        product2.setWeight(1);
        product2.setDescription("Σκουπίστε με ένα πανί, ελαφρά βρεγμένο με ένα ήπιο καθαριστικό. Στεγνώστε με ένα καθαρό πανί. ");
        product2.setShortDescription("Τα φρένα για τα συρτάρια εμποδίζουν τα συρτάρια να βγουν πολύ έξω");
        product2.setImage("http://www.ikea.gr/images/250x250/10192824/alex_syrtariera_0.jpg?v=0");
        product2.setStock(20);
        product2.setCategoryID(1);

        Product product3 = new Product();
        product3.setProductID(2);
        product3.setSKU("123ABC");
        product3.setName("BILLY/OXBERG");
        product3.setPrice(149.99);
        product3.setDiscountPrice(10);
        product3.setWeight(1);
        product3.setDescription("Ρυθμιζόμενα ράφια. Προσαρμόστε το χώρο μεταξύ των ραφιών ανάλογα με τις ανάγκες σας.\n" +
                "Η επιφάνεια είναι κατασκευασμένη με όψη φυσικού ξύλου.\n" +
                "Οι ρυθμιζόμενοι μεντεσέδες σάς επιτρέπουν να προσαρμόσετε την πόρτα στη σωστή θέση.\n" +
                "Οι γυάλινες πόρτες προφυλάσσουν τα αγαπημένα σας αντικείμενα από τη σκόνη και επιτρέπουν να είναι ορατά. ");
        product3.setShortDescription("Ρυθμιζόμενα ράφια. Προσαρμόστε το χώρο μεταξύ των ραφιών ανάλογα με τις ανάγκες σας.");
        product3.setImage("http://www.ikea.gr/images/250x250/79020482/billy_oxberg_bibliothiki_skoyro_kafe_0.jpg?v=5");
        product3.setStock(20);
        product3.setCategoryID(1);

        returnedList.add(product1);
        returnedList.add(product2);
        returnedList.add(product3);

        populateCartList(returnedList);
    }

    private void populateCartList(List<Product> returnedList) {

        recyclerView = (RecyclerView) findViewById(R.id.rvCart);
        adapter = new CartAdapter(returnedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void checkout(View view) {
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
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    //Start Login Activity
    public void showLogin(MenuItem item) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void showLoginDialog() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //Start About Us Activity
    public void showAboutUs(MenuItem item) {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }
}
