package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.homedeco.www.homedeco.cart.Cart;
import gr.homedeco.www.homedeco.contact.chat.Chat;
import gr.homedeco.www.homedeco.contact.regular.ContactUs;
import gr.homedeco.www.homedeco.product.generic.Products;
import gr.homedeco.www.homedeco.user.UserController;
import gr.homedeco.www.homedeco.user.login.Login;
import gr.homedeco.www.homedeco.user.profile.UserProfile;

public class Main extends AppCompatActivity {

    private UserController uController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uController = new UserController(this);
    }

    public void showLogin(View view) {
        if (uController.isUserLoggedIn()) {
            Intent intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
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
        if (uController.isUserLoggedIn()) {
            Intent intent = new Intent(this, Chat.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);
        }

    }
}
