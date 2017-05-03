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

public class GenericProducts extends AppCompatActivity {

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
        setContentView(R.layout.activity_generic_products);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);
        context = this;

        getProducts();

        adapterSpSubcategory = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, subCategory);
        adapterSpSubcategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategory.setAdapter(adapterSpSubcategory);
    }

    // ---------------------------------------- MENU  ------------------------------------------------//

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GenericProducts.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // ------------------------------------- SERVER REQUESTS  ----------------------------------------//
    private void getProducts() {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchProductDataInBackground(0, new GetProductCallback() {
            @Override
            public void done(List<Product> returnedList) {
                products = returnedList;
                populateProductsList(returnedList);
                initListeners();
            }
        });
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
                        String cat = product.getSKU();
                        if (Integer.parseInt(cat.substring(0,1)) == selectedPos) {
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
                            String cat = product.getSKU();
                            if (Integer.parseInt(cat.substring(0,1)) == catSelectedPos) {
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
                        String cat = product.getSKU();
                        if (Integer.parseInt(cat.substring(0,1)) == catSelectedPos
                                && Integer.parseInt(cat.substring(1,2)) == subCatSelectedPos) {
                            tmpProducts.add(product);
                        }
                    }
                    populateProductsList(tmpProducts);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
