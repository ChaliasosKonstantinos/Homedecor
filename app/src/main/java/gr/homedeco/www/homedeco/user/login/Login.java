package gr.homedeco.www.homedeco.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.helpers.Helpers;
import gr.homedeco.www.homedeco.server.callbacks.GetLoginCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetUserDetailsCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.user.User;
import gr.homedeco.www.homedeco.user.UserController;
import gr.homedeco.www.homedeco.user.register.Register;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private LinearLayout layout;
    private UserController uController = new UserController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        layout = (LinearLayout) findViewById(R.id.activity_login);

        // Remembered user
        if (uController.isRemembered()) {
            User user = uController.getRemembered();
            etUsername.setText(user.getUsername());
            etPassword.setText(user.getPassword());
            cbRememberMe.setChecked(true);
        }
    }

    public void logIn(View view) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        Helpers helper = new Helpers();
        helper.hideKeyboard(Login.this);
        final User userToLogin = new User(username, password);

        final ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.login(userToLogin, new GetLoginCallback() {
            @Override
            public void done(String response) {
                if (response != null) {
                    boolean isLoggedIn = uController.loginUser(response);
                    if (isLoggedIn) {
                        Snackbar snackbar = Snackbar.make(layout, "Σύνδεση επιτυχής", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        if (cbRememberMe.isChecked()) {
                            uController.setRemembered(userToLogin);
                        }
                        serverRequest.getUserDetails(new GetUserDetailsCallback() {
                            @Override
                            public void done(User returnedUser) {
                                uController.setUserDetails(returnedUser);
                                // Navigates to parent Activity after 1sec
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                NavUtils.navigateUpFromSameTask(Login.this);
                                            }
                                        }, 1000);
                            }
                        });
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(layout, "Λάθος συνδυασμός όνομα χρήστη/κωδικού", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    //Show Register Activity
    public void showRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
