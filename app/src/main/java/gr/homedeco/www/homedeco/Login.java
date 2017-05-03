package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private LocalDatabase localDatabase;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        layout = (LinearLayout) findViewById(R.id.activity_login);
        localDatabase = new LocalDatabase(this);

        // Remembered user
        User user = localDatabase.getRememberMe();
        if (!user.getUsername().isEmpty()) {
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
                    Snackbar snackbar = Snackbar.make(layout, "Σύνδεση επιτυχής", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    localDatabase.setLoggedIn(true, response);
                    if (cbRememberMe.isChecked()) {
                        localDatabase.setRememberMe(userToLogin);
                    }
                    serverRequest.getUserDetails(new GetUserDetailsCallback() {
                        @Override
                        public void done(User returnedUser) {
                            localDatabase.setUserDetails(returnedUser);
                            // Navigates to parent Activity after 1sec
                            new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        NavUtils.navigateUpFromSameTask(Login.this);
                                    }
                                }, 1000);
                        }
                    });
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
