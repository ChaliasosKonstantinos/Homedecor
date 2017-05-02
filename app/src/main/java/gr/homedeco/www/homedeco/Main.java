package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localDatabase = new LocalDatabase(this);
    }

    //Show Login Activity
    public void showLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void showProducts(View view) {
        Intent intent = new Intent(this, Products.class);
        startActivity(intent);
    }

    public void showCart(View view) {
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
    }

    public void showChat(View view) {
        if (localDatabase.isLoggedIn()) {
            Intent intent = new Intent(this, Chat.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);
        }

    }
}
