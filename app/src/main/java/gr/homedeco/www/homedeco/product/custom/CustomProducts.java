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

/* ========================================= HELPERS =============================================== */

    /**
     * Adds the fragments to the View Pager
     */
    private void setupViewAdapter(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomProductType(), "ΤΥΠΟΣ");
        adapter.addFragment(new CustomProductColor(), "ΧΡΩΜΑ");
        adapter.addFragment(new CustomProductBody(), "ΣΩΜΑ");
        adapter.addFragment(new CustomProductFeet(), "ΠΟΔΙΑ");
        adapter.addFragment(new CustomProductPrice(), "ΣΥΝΟΨΗ");
        viewPager.setAdapter(adapter);
    }

    /**
     * Returns the viewPager to the fragment
     *
     * @return a ViewPager object to the fragment
     */
    public ViewPager getPager() {
        return viewPager;
    }


/* =================================== CUSTOM PRODUCT ============================================== */

    /**
     * Returns the custom product
     *
     * @return a CustomProduct object
     */
    public CustomProduct getCustomProduct() {
        return customProduct;
    }

/* =================================== CUSTOM PRODUCTS ============================================= */

    /**
     * Retrieves custom products from the server
     */
    private void getCustomProductsFromServer() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchCustomProductDataInBackground(new GetCustomProductCallback() {
            @Override
            public void done(List<CustomProduct> returnedList) {
                cProducts = returnedList;
            }
        });
    }

    /**
     * Returns the list of custom products
     *
     * @return a list of CustomProduct objects
     */
    public List<CustomProduct> getCustomProducts() {
        return cProducts;
    }

    /**
     * Set the list of CustomProduct objects
     */
    public void setFilteredCustomProducts(List<CustomProduct> cProductsFiltered) {
        this.cProductsFiltered = cProductsFiltered;
    }

    /**
     * Returns the filtered list of custom products
     *
     * @return a filtered list of CustomProduct objects
     */
    public List<CustomProduct> getFilteredCustomProducts() {
        return cProductsFiltered;
    }

}
