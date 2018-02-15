package gr.homedeco.www.homedeco.product.generic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.product.custom.CustomProducts;

public class Products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
    }


    /**
     * Launches Generic Products Activity
     *
     * @param view the View containing the button that was clicked
     */
    public void showGenericProducts(View view) {
        Intent intent = new Intent(this, GenericProducts.class);
        startActivity(intent);
    }

    /**
     * Launches Custom Products Activity
     *
     * @param view the View containing the button that was clicked
     */
    public void showCustomProducts(View view) {
        Intent intent = new Intent(this, CustomProducts.class);
        startActivity(intent);
    }
}
