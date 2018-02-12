package gr.homedeco.www.homedeco.order.creation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.cart.CartController;
import gr.homedeco.www.homedeco.order.OrderController;
import gr.homedeco.www.homedeco.product.CustomProduct;
import gr.homedeco.www.homedeco.product.custom.SectionsPageAdapter;

public class OrderCreation extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private OrderController oCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creation);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewAdapter(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        initOrder();
    }
/* ========================================= HELPERS =============================================== */

    /**
     * Fetches intent extra information and initialize the order
     */
    private void initOrder() {
        CartController cController = new CartController(this);
        String type = getIntent().getExtras().getString("type");
        oCon = new OrderController(type);
        oCon.setType(type);
        if (Objects.equals(type, "generic")) {
            oCon.setPrice(cController.getCartPrice(), null);
            oCon.setProducts(cController.getCart(), null);
        } else {
            CustomProduct cp = (CustomProduct)getIntent().getSerializableExtra("customProduct");
            oCon.setPrice(0, cp);
            oCon.setProducts("", cp);
        }
    }

    /**
     * Returns the order controller to the fragment
     *
     * @return an OrderController object
     */
    public OrderController getOrderState() {
        return oCon;
    }

    /**
     * Adds the fragments to the View Pager
     */
    private void setupViewAdapter(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderInfo(), "ΠΛΗΡΟΦΟΡΙΕΣ");
        adapter.addFragment(new OrderShipping(), "ΑΠΟΣΤΟΛΗ");
        adapter.addFragment(new OrderPayment(), "ΠΛΗΡΩΜΗ");
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
}
