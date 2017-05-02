package gr.homedeco.www.homedeco;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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
    }

    public void logIn(View view) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        final User userToLogin = new User(username, password);

        final ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.login(userToLogin, new GetLoginCallback() {
            @Override
            public void done(String response) {
                if (response != null) {
                    Snackbar snackbar = Snackbar.make(layout, "Σύνδεση επιτυχής", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.d("LOGIN RE", "DONE");
                    localDatabase.setLoggedIn(true, response);
                    if (cbRememberMe.isChecked()) {
                        localDatabase.setRememberMe(userToLogin);
                    }
                    serverRequest.getUserDetails(new GetUserDetailsCallback() {
                        @Override
                        public void done(User returnedUser) {
                            Log.d("GET-USER-DETAILS", "DONE");
                        }
                    });
                } else {
                    Log.d("LOGIN RE", "FAILED");
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
