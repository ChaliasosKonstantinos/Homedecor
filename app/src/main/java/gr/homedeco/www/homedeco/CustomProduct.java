package gr.homedeco.www.homedeco;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomProduct extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_product);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewAdapter(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adds the fragments to the View Pager
    private void setupViewAdapter(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
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


}
