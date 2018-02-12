package gr.homedeco.www.homedeco.contact.regular;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.aboutUs.AboutUs;
import gr.homedeco.www.homedeco.user.login.Login;

public class ContactUs extends AppCompatActivity {

    private EditText etName, etEmail, etMessage;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        layout = (LinearLayout) findViewById(R.id.activity_contact_us);
        etName = (EditText) findViewById(R.id.etContactName);
        etEmail = (EditText) findViewById(R.id.etContactEmail);
        etMessage = (EditText) findViewById(R.id.etContactMessage);
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Sends email to the company
     * On SUCCESS: Display success message
     * On ERROR: Display error message
     *
     * @param view the View containing the button that was clicked
     */
    public void sendEmail (View view) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String message = etMessage.getText().toString();
        ContactController cController = new ContactController(this);
        boolean isSent = cController.sendEmail(name, email, message);
        if (isSent) {
            Snackbar snackbar = Snackbar.make(layout, R.string.contact_us_success, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(layout, R.string.contact_us_error, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

/* ========================================= MENU =============================================== */

    /**
     * Creates Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Setting up menu listeners
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_login:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
