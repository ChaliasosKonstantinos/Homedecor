package gr.homedeco.www.homedeco.product.custom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.product.CustomProduct;
import gr.homedeco.www.homedeco.server.callbacks.GetCustomProductCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;

public class CustomProducts extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    final private CustomProduct customProduct = new CustomProduct();
    private List<CustomProduct> cProducts = new ArrayList<>(), cProductsFiltered = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_product);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewAdapter(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getCustomProductsFromServer();
    }

    // Adds the fragments to the View Pager
    private void setupViewAdapter(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomProductType(), "ΤΥΠΟΣ");
        adapter.addFragment(new CustomProductColor(), "ΧΡΩΜΑ");
        adapter.addFragment(new CustomProductBody(), "ΣΩΜΑ");
        adapter.addFragment(new CustomProductFeet(), "ΠΟΔΙΑ");
        adapter.addFragment(new CustomProductPrice(), "ΣΥΝΟΨΗ");
        viewPager.setAdapter(adapter);
    }

    // Let the fragment getPager
    public ViewPager getPager() {
        return viewPager;
    }


/* =================================== CUSTOM PRODUCT ============================================== */

    // Returns the Custom Product
    public CustomProduct getCustomProduct() {
        return customProduct;
    }

/* =================================== CUSTOM PRODUCTS ============================================= */

    // Get custom products from the server
    private void getCustomProductsFromServer() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchCustomProductDataInBackground(new GetCustomProductCallback() {
            @Override
            public void done(List<CustomProduct> returnedList) {
                cProducts = returnedList;
            }
        });
    }

    // Returns custom products
    public List<CustomProduct> getCustomProducts() {
        return cProducts;
    }

    // Set filtered products
    public void setFilteredCustomProducts(List<CustomProduct> cProductsFiltered) {
        this.cProductsFiltered = cProductsFiltered;
    }

    // Returns filtered custom products
    public List<CustomProduct> getFilteredCustomProducts() {
        return cProductsFiltered;
    }

}
