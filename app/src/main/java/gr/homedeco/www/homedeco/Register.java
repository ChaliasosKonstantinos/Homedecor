package gr.homedeco.www.homedeco;

import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private EditText etUsername, etPassword, etFirstName, etLastName, etEmail, etAddress,
            etTK, etCity, etState, etCountry, etPhone, etMobilePhone;
    private ScrollView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etRegUsername);
        etPassword = (EditText) findViewById(R.id.etRegPassword);
        etFirstName = (EditText) findViewById(R.id.etRegFirstName);
        etLastName = (EditText) findViewById(R.id.etRegLastName);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etAddress = (EditText) findViewById(R.id.etRegAddress);
        etTK = (EditText) findViewById(R.id.etRegTK);
        etCity = (EditText) findViewById(R.id.etRegCity);
        etState = (EditText) findViewById(R.id.etRegState);
        etCountry = (EditText) findViewById(R.id.etRegCountry);
        etPhone = (EditText) findViewById(R.id.etRegPhone);
        etMobilePhone = (EditText) findViewById(R.id.etRegMobilePhone);
        layout = (ScrollView) findViewById(R.id.activity_register);
    }

    public void registerUser(View view) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String tk = etTK.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String country = etCountry.getText().toString();
        String phone = etPhone.getText().toString();
        String mobilePhone = etMobilePhone.getText().toString();

        User userToRegister = new User(username, password);
        userToRegister.setFirstName(firstName);
        userToRegister.setLastName(lastName);
        userToRegister.setEmail(email);
        userToRegister.setAddress(address);
        userToRegister.setPostalCode(tk);
        userToRegister.setCity(city);
        userToRegister.setState(state);
        userToRegister.setCountry(country);
        userToRegister.setPhone(phone);
        userToRegister.setMobilePhone(mobilePhone);
        userToRegister.setBirthday("23/06/1992");

        Helpers helper = new Helpers();
        helper.hideKeyboard(Register.this);

        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.register(userToRegister, new GetRegisterCallback() {
            @Override
            public void done(ServerResponse response) {
                if (!response.getMessage().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(layout, "Εγγραφή επιτυχής", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    // Navigates to parent Activity after 1sec
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    NavUtils.navigateUpFromSameTask(Register.this);
                                }
                            }, 1000);
                } else {
                    Snackbar snackbar = Snackbar.make(layout, "Η εγγραφή απέτυχε, προσπαθήστε ξανά.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
}
