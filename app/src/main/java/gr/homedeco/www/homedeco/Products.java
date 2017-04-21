package gr.homedeco.www.homedeco;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private List<Product> products;
    private Spinner spCategory, spSubCategory;
    private ArrayAdapter<String> adapterSpSubcategory;
    private String subCategory[] = {"Υποκατηγορία"};
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);
        context = this;

        getProducts();
        initListeners();

        adapterSpSubcategory = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, subCategory);
        adapterSpSubcategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategory.setAdapter(adapterSpSubcategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Start Login Activity
    public void showLogin(MenuItem item) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //Start About Us Activity
    public void showAboutUs(MenuItem item) {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    // ---------------------------------------- HELPERS  ---------------------------------------------//

    private void populateProductsList(List<Product> returnedList) {

        recyclerView = (RecyclerView) findViewById(R.id.rvProducts);
        adapter = new ProductsAdapter(returnedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Products.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // ------------------------------------- SERVER REQUESTS  ----------------------------------------//
    private void getProducts() {

//        ServerRequests serverRequest = new ServerRequests(this);
//        serverRequest.fetchProductDataInBackground(0, new GetProductCallback() {
//            @Override
//            public void done(List<Product> returnedList) {
//                populateProductsList(returnedList);
//            }
//        });

        // Mockup - TODO: Delete this
        List<Product> returnedList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductID(1);
        product1.setSKU("123ABC");
        product1.setName("MALM");
        product1.setPrice(159.00);
        product1.setDiscountPrice(10);
        product1.setWeight(1);
        product1.setDescription("Μπορείτε να συγκεντρώσετε τα καλώδια και τα πολύπριζά σας στο ράφι κάτω από την επιφάνεια του τραπεζιού, ώστε να μη φαίνονται, αλλά να παραμένουν εύκολα προσβάσιμα.\n" +
                "Μπορεί να τοποθετηθεί και στη μέση ενός δωματίου, καθώς διαθέτει φινίρισμα και στο πίσω μέρος.\n" +
                "Μπορείτε να τοποθετήσετε την αποθηκευτική μονάδα σας, είτε στα δεξιά, είτε στα αριστερά, ανάλογα με το χώρο ή την προτίμησή σας. ");
        product1.setShortDescription("Μπορείτε να συγκεντρώσετε τα καλώδια και τα πολύπριζά σας στο ράφι κάτω από την επιφάνεια του τραπεζιού, ώστε να μη φαίνονται, αλλά να παραμένουν εύκολα προσβάσιμα.");
        product1.setImage("http://www.ikea.gr/images/250x250/00214157/malm_grafeio_0.jpg?v=0");
        product1.setStock(35);
        product1.setCategoryID(1);
        product1.setSubCategoryID(2);

        Product product2 = new Product();
        product2.setProductID(2);
        product2.setSKU("123ABC");
        product2.setName("MICKE");
        product2.setPrice(59.99);
        product2.setDiscountPrice(10);
        product2.setWeight(1);
        product2.setDescription("Τα φρένα για τα συρτάρια εμποδίζουν τα συρτάρια να βγουν πολύ έξω.\n" +
                "Μπορεί να τοποθετηθεί και στη μέση ενός δωματίου, καθώς διαθέτει φινίρισμα και στο πίσω μέρος.\n" +
                "Μπορείτε να προεκτείνετε την επιφάνεια εργασίας σας συνδυάζοντας γραφεία και συρταριέρες. Όλα τα γραφεία και οι συρταριέρες της σειράς MICKE έχουν το ίδιο ύψος. ");
        product2.setShortDescription("Τα φρένα για τα συρτάρια εμποδίζουν τα συρτάρια να βγουν πολύ έξω.");
        product2.setImage("http://www.ikea.gr/images/250x250/80244749/micke_syrtariera_me_rodakia_skoyro_kafe_0.jpg?v=0");
        product2.setStock(20);
        product2.setCategoryID(1);
        product2.setSubCategoryID(1);

        Product product3 = new Product();
        product3.setProductID(3);
        product3.setSKU("123ABC");
        product3.setName("MARKUS");
        product3.setPrice(179.99);
        product3.setDiscountPrice(10);
        product3.setWeight(1);
        product3.setDescription("10 χρόνια δωρεάν εγγύηση. Διαβάστε σχετικά με τους όρους στο φυλλάδιο της εγγύησης.\n" +
                "Κάθεστε με άνεση, καθώς η καρέκλα διαθέτει ρυθμιζόμενο ύψος.\n" +
                "Η ρυθμιζόμενη λειτουργία ανάκλισης που κλειδώνει, αυξάνει τη σταθερότητα και τον έλεγχο στις διάφορες θέσεις καθίσματος.\n" +
                "Παρέχεται στήριξη και επιπλέον ανακούφιση στην πλάτη σας, χάρη στον εργονομικό σχεδιασμό του καθίσματος.\n" +
                "Το δικτυωτό υλικό της πλάτης, επιτρέπει στον αέρα να περνά και να φτάνει στην πλάτη σας, όταν κάθεστε για μεγάλα χρονικά διαστήματα.\n" +
                "Τα ροδάκια διαθέτουν πλαστική επικάλυψη, ώστε να κυλούν ομαλά σε κάθε τύπο δαπέδου. ");
        product3.setShortDescription("10 χρόνια δωρεάν εγγύηση. Διαβάστε σχετικά με τους όρους στο φυλλάδιο της εγγύησης.");
        product3.setImage("http://www.ikea.gr/images/250x250/70261150/markus_peristrefomeni_karekla_vissle_skoyro_gkri_0.jpg?v=3");
        product3.setStock(20);
        product3.setCategoryID(1);
        product3.setSubCategoryID(3);

        Product product4 = new Product();
        product4.setProductID(4);
        product4.setSKU("123ABC");
        product4.setName("BRIMNES");
        product4.setPrice(412.96);
        product4.setDiscountPrice(10);
        product4.setWeight(1);
        product4.setDescription("Αυτή η σύνθεση αποθήκευσης TV έχει επιπλέον χώρο αποθήκευσης και σας διευκολύνει να έχετε οργανωμένο το σαλόνι σας.\n" +
                "Οι έξοδοι καλωδίων σας διευκολύνουν να οδηγήσετε τα καλώδια εκτός του επίπλου στο πίσω μέρος, έτσι ώστε να παραμένουν κρυμμένα, αλλά εύκολα προσβάσιμα όταν τα χρειάζεστε. \n" +
                "Μπορείτε να επιλέξετε να τοποθετήσετε την πόρτα είτε δεξιά, είτε αριστερά, ανάλογα με το τι ταιριάζει καλύτερα στο χώρο σας. \n" +
                "Διαθέτει ρυθμιζόμενα ράφια, ώστε να προσαρμόσετε την αποθήκευση ανάλογα με τις ανάγκες σας. \n" +
                "Εύκολα συρόμενα συρτάρια με στόπερ, για να τα κρατάει στη θέση τους. Οι πόρτες σάς επιτρέπουν να κρύψετε τα πράγματά σας και να τα προστατέψετε από τη σκόνη. ");
        product4.setShortDescription("Ντουλάπι με πόρτες/έπιπλο TV/επίτοιχο ντουλάπι με γυάλινη πόρτα.");
        product4.setImage("http://www.ikea.gr/images/250x250/49184333/brimnes_synthesi_apothikeysis_tv_mayro_0.jpg?v=0");
        product4.setStock(20);
        product4.setCategoryID(2);
        product4.setSubCategoryID(1);

        Product product5 = new Product();
        product5.setProductID(5);
        product5.setSKU("123ABC");
        product5.setName("NOCKEBY");
        product5.setPrice(949.00);
        product5.setDiscountPrice(10);
        product5.setWeight(1);
        product5.setDescription("Σας προσφέρει επιπλέον άνεση και στήριξη, καθώς τα παχιά μαξιλάρια διαθέτουν πυρήνα με ελατήρια σε θήκες και μία πάνω στρώση από αφρό και ίνες πολυεστέρα.\n" +
                "Ο πυρήνας από ελατήρια σε θήκες είναι ιδιαίτερα ανθεκτικός και διατηρεί τη φόρμα του και την άνεση που προσφέρει για μεγάλο χρονικό διάστημα.\n" +
                "Ο ευρύχωρος σχεδιασμός του καναπέ προσφέρει αρκετό χώρο για να καθίσουν όλοι με άνεση.\n" +
                "Βαρύ, ανθεκτικό ύφασμα με δομή από βαμμένα νήματα σε διαφορετικές αποχρώσεις.\n" +
                "Μπορείτε εύκολα να διατηρήσετε το κάλυμμα καθαρό, γιατί αφαιρείται και χρειάζεται στεγνό καθάρισμα.\n" +
                "5 χρόνια δωρεάν εγγύηση. Διαβάστε σχετικά με τους όρους στο φυλλάδιο της εγγύησης. ");
        product5.setShortDescription("Σας προσφέρει επιπλέον άνεση και στήριξη, καθώς τα παχιά μαξιλάρια διαθέτουν πυρήνα με ελατήρια σε θήκες και μία πάνω στρώση από αφρό και ίνες πολυεστέρα.");
        product5.setImage("http://www.ikea.gr/images/250x250/49111054/nockeby_dithesios_kanapes_me_aristeri_sezlongk_0.jpg?v=0");
        product5.setStock(20);
        product5.setCategoryID(2);
        product5.setSubCategoryID(2);

        Product product6 = new Product();
        product6.setProductID(6);
        product6.setSKU("123ABC");
        product6.setName("STOCKHOLM");
        product6.setPrice(269.00);
        product6.setDiscountPrice(10);
        product6.setWeight(1);
        product6.setDescription("Η επιφάνεια του τραπεζιού με επίστρωση ξύλου καρυδιάς, και πόδια από μασίφ καρυδιά, προσδίδουν μια ζεστή, φυσική αίσθηση στο δωμάτιό σας.\n" +
                "Τα χαρακτηριστικά φυσικά σημάδια της όψης καρυδιάς δίνουν σε κάθε τραπέζι ένα μοναδικό χαρακτήρα.\n" +
                "Η καρυδιά είναι ένα φυσικά ανθεκτικό ξύλο. Κάναμε την επιφάνεια του ξύλου ακόμα πιο ανθεκτική με μία επίστρωση λάκας για μεγαλύτερη προστασία.\n" +
                "Μπορείτε να τοποθετήσετε, ώστε να τα βρίσκετε εύκολα, τις εφημερίδες, τα τηλεχειριστήριά σας και άλλα μικρά πράγματα, στο ράφι κάτω από την επιφάνεια του τραπεζιού. ");
        product6.setShortDescription("Η επιφάνεια του τραπεζιού με επίστρωση ξύλου καρυδιάς, και πόδια από μασίφ καρυδιά, προσδίδουν μια ζεστή, φυσική αίσθηση στο δωμάτιό σας.");
        product6.setImage("http://www.ikea.gr/images/250x250/70239710/stockholm_trapezi_mesis_ksylo_karydias_0.jpg?v=0");
        product6.setStock(20);
        product6.setCategoryID(2);
        product6.setSubCategoryID(3);

        Product product7 = new Product();
        product7.setProductID(7);
        product7.setSKU("123ABC");
        product7.setName("UNDREDAL");
        product7.setPrice(399.00);
        product7.setDiscountPrice(10);
        product7.setWeight(1);
        product7.setDescription("Για το προϊόν αυτό χρειάζεται η μεσαία δοκός SKORVA. \n" +
                "Αν αγοράζετε από το κατάστημα θα πρέπει να την παραλάβετε χωριστά, αλλά αν αγοράζετε το κρεβάτι σας διαδικτυακά περιλαμβάνεται στην παραγγελία. \n" +
                "Οι τάβλες, το στρώμα και τα λευκά είδη πωλούνται χωριστά. Δημιουργήστε επιπλέον αποθηκευτικό χώρο κάτω από το κρεβάτι με τα κουτιά VARDÖ. ");
        product7.setShortDescription("Οι ρυθμιζόμενες πλευρές του κρεβατιού σάς επιτρέπουν να χρησιμοποιήσετε στρώματα διαφορετικού πάχους. ");
        product7.setImage("http://www.ikea.gr/images/250x250/99059930/undredal_skeletos_krebatioy_0.jpg?v=0");
        product7.setStock(20);
        product7.setCategoryID(3);
        product7.setSubCategoryID(1);

        Product product8 = new Product();
        product8.setProductID(8);
        product8.setSKU("123ABC");
        product8.setName("PAX");
        product8.setPrice(779.00);
        product8.setDiscountPrice(10);
        product8.setWeight(1);
        product8.setDescription("10 χρόνια δωρεάν εγγύηση. Διαβάστε σχετικά με τους όρους στο φυλλάδιο της εγγύησης.\n" +
                "Μπορείτε εύκολα να προσαρμόσετε τον έτοιμο συνδυασμό PAX/KOMPLEMENT, ανάλογα με τις ανάγκες και το στιλ σας, χρησιμοποιώντας το εργαλείο σχεδιασμού PAX.\n" +
                "Αν θέλετε να οργανώσετε το εσωτερικό μπορείτε να συμπληρώσετε με τα εσωτερικά εξαρτήματα της σειράς KOMPLEMENT.\n" +
                "Ρυθμιζόμενα πόδια, για σταθερότητα ακόμα και σε ένα άνισο δάπεδο. ");
        product8.setShortDescription("10 χρόνια δωρεάν εγγύηση. Διαβάστε σχετικά με τους όρους στο φυλλάδιο της εγγύησης.");
        product8.setImage("http://www.ikea.gr/images/250x250/79029152/pax_ntoylapa_0.jpg?v=0");
        product8.setStock(20);
        product8.setCategoryID(3);
        product8.setSubCategoryID(2);

        Product product9 = new Product();
        product9.setProductID(9);
        product9.setSKU("123ABC");
        product9.setName("UNDREDAL");
        product9.setPrice(299.00);
        product9.setDiscountPrice(10);
        product9.setWeight(1);
        product9.setDescription("Το ενσωματωμένο φρένο, σταματά το συρτάρι και το κλείνει απαλά και αθόρυβα.\n" +
                "Συρτάρια με εύκολο άνοιγμα και κλείσιμο.\n" +
                "Ρυθμιζόμενα πόδια, για σταθερότητα ακόμα και σε ένα άνισο δάπεδο. ");
        product9.setShortDescription("Το ενσωματωμένο φρένο, σταματά το συρτάρι και το κλείνει απαλά και αθόρυβα.");
        product9.setImage("http://www.ikea.gr/images/250x250/90293744/undredal_syrtariera_me_5_syrtaria_mayro_0.jpg?v=1");
        product9.setStock(20);
        product9.setCategoryID(3);
        product9.setSubCategoryID(3);

        returnedList.add(product1);
        returnedList.add(product2);
        returnedList.add(product3);
        returnedList.add(product4);
        returnedList.add(product5);
        returnedList.add(product6);
        returnedList.add(product7);
        returnedList.add(product8);
        returnedList.add(product9);

        products = returnedList;
        populateProductsList(returnedList);
    }

// ---------------------------------------- LISTENERS --------------------------------------------//

    private void initListeners() {

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer selectedPos = spCategory.getSelectedItemPosition();
                switch (selectedPos) {
                    case 0:
                        subCategory = null;
                        break;
                    case 1:
                        subCategory = getResources().getStringArray(R.array.product_filters_subCategory_desk);
                        break;
                    case 2:
                        subCategory = getResources().getStringArray(R.array.product_filters_subCategory_livingRoom);
                        break;
                    case 3:
                        subCategory = getResources().getStringArray(R.array.product_filters_subCategory_bedroom);
                        break;
                    default:
                        break;
                }

                if (subCategory != null) {
                    adapterSpSubcategory = new ArrayAdapter<>(context,
                            android.R.layout.simple_spinner_item, subCategory);
                    spSubCategory.setAdapter(adapterSpSubcategory);
                    spSubCategory.setEnabled(true);
                    List<Product> tmpProducts = new ArrayList<>();
                    for (Product product : products) {
                        if (product.getCategoryID() == selectedPos) {
                            tmpProducts.add(product);
                        }
                    }
                    populateProductsList(tmpProducts);
                } else {
                    populateProductsList(products);
                    spSubCategory.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer catSelectedPos = spCategory.getSelectedItemPosition();
                Integer subCatSelectedPos = spSubCategory.getSelectedItemPosition();

                if (subCatSelectedPos == 0) {
                    if (catSelectedPos != 0) {
                        List<Product> tmpProducts = new ArrayList<>();
                        for (Product product : products) {
                            if (product.getCategoryID() == catSelectedPos) {
                                tmpProducts.add(product);
                            }
                        }
                        populateProductsList(tmpProducts);
                    } else {
                        populateProductsList(products);
                    }
                } else {
                    List<Product> tmpProducts = new ArrayList<>();
                    for (Product product : products) {
                        if (product.getCategoryID() == catSelectedPos
                                && product.getSubCategoryID() == subCatSelectedPos) {
                            tmpProducts.add(product);
                        }
                    }
                    populateProductsList(tmpProducts);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}
