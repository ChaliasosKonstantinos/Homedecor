package gr.homedeco.www.homedeco.contact.regular;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import gr.homedeco.www.homedeco.R;

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

    // Sends email to Administrator
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
}
