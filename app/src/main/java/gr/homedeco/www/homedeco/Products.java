package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
    }

    public void showGenericProducts(View view) {
        Intent intent = new Intent(this, GenericProducts.class);
        startActivity(intent);
    }

    public void showCustomProducts(View view) {
        Intent intent = new Intent(this, CustomProduct.class);
        startActivity(intent);
    }
}
