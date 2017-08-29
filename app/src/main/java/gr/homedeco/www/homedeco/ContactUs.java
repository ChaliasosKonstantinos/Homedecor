package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
        String message = "Όνομα: " + name + "\n\n";
        message += "Email: " + email + "\n\n\n";
        message += "Μήνυμα:\n\n" + etMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android App | Επικοινωνία");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setData(Uri.parse("mailto:chaliasos@gmail.com"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
            Snackbar snackbar = Snackbar.make(layout, R.string.contact_us_success, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar snackbar = Snackbar.make(layout, R.string.contact_us_error, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
