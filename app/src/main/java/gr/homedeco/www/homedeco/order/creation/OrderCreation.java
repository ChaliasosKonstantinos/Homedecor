package gr.homedeco.www.homedeco.order.creation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.product.custom.SectionsPageAdapter;

public class OrderCreation extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creation);

        LocalDatabase localDatabase = new LocalDatabase(this);
        order = new Order();
        order.setPrice(localDatabase.getCartPrice());
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewAdapter(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adds the fragments to the View Pager
    private void setupViewAdapter(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderInfo(), "ΠΛΗΡΟΦΟΡΙΕΣ");
        adapter.addFragment(new OrderShipping(), "ΑΠΟΣΤΟΛΗ");
        adapter.addFragment(new OrderPayment(), "ΠΛΗΡΩΜΗ");
        viewPager.setAdapter(adapter);
    }

    // Let the fragment getPager
    public ViewPager getPager() {
        return viewPager;
    }

    // Let the fragment to save order state
    public void saveOrderState(Order order) {
        this.order = order;
    }

    // Returned order saved state to fragment
    public Order getOrderState() {
        return order;
    }
}
