package gr.homedeco.www.homedeco.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import gr.homedeco.www.homedeco.Main;
import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.aboutUs.AboutUs;
import gr.homedeco.www.homedeco.user.User;
import gr.homedeco.www.homedeco.user.UserController;

public class UserProfile extends AppCompatActivity {

    private EditText etPassword, etFirstName, etLastName, etEmail, etAddress,
            etTK, etCity, etState, etCountry, etPhone, etMobilePhone;
    private ImageView imgInfoDetails, imgContactDetails, imgAddressDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etTK = (EditText) findViewById(R.id.etTK);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etMobilePhone = (EditText) findViewById(R.id.etMobilePhone);
        imgInfoDetails = (ImageView) findViewById(R.id.imgInfoDetails);
        imgContactDetails = (ImageView) findViewById(R.id.imgContactDetails);
        imgAddressDetails = (ImageView) findViewById(R.id.imgAddressDetails);

        setUpListeners();
        populateView();
    }

/* ========================================= HELPERS =============================================== */

    private void populateView() {
        UserController uController = new UserController(this);
        User user = uController.getUserDetails();
        getSupportActionBar().setTitle(user.getUsername());
        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
        etEmail.setText(user.getEmail());
        etAddress.setText(user.getAddress());
        etTK.setText(user.getPostalCode());
        etCity.setText(user.getCity());
        etState.setText(user.getState());
        etCountry.setText(user.getCountry());
        etPhone.setText(user.getPhone());
        etMobilePhone.setText(user.getMobilePhone());
    }

    public void saveUser(View view) {
        User updatedUser = new User();
        updatedUser.setFirstName(etFirstName.getText().toString());
        updatedUser.setLastName(etLastName.getText().toString());
        updatedUser.setEmail(etEmail.getText().toString());
        updatedUser.setAddress(etAddress.getText().toString());
        updatedUser.setPostalCode(etTK.getText().toString());
        updatedUser.setCity(etFirstName.getText().toString());
        updatedUser.setState(etFirstName.getText().toString());
        updatedUser.setCountry(etFirstName.getText().toString());
        updatedUser.setPhone(etFirstName.getText().toString());
        updatedUser.setMobilePhone(etFirstName.getText().toString());
        updatedUser.setBirthday("23/06/1992");
    }

    private void setUpListeners() {
        imgInfoDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation in = new AlphaAnimation(0.0f, 1.0f);
                in.setDuration(800);

                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(800);
                LinearLayout infoDetailsLayout = (LinearLayout) findViewById(R.id.layoutInfoDetails);
                int vis = infoDetailsLayout.getVisibility();
                if (vis == View.GONE) {
                    infoDetailsLayout.setVisibility(View.VISIBLE);
                    imgInfoDetails.setImageResource(R.drawable.ic_action_arrow_up);
                } else if (vis == View.VISIBLE) {
                    infoDetailsLayout.setVisibility(View.GONE);
                    imgInfoDetails.setImageResource(R.drawable.ic_action_arrow_down);
                }
            }
        });
        imgContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation in = new AlphaAnimation(0.0f, 1.0f);
                in.setDuration(800);

                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(800);
                LinearLayout contactDetailsLayout = (LinearLayout) findViewById(R.id.layoutContactDetails);
                int vis = contactDetailsLayout.getVisibility();
                if (vis == View.GONE) {
                    contactDetailsLayout.setVisibility(View.VISIBLE);
                    imgContactDetails.setImageResource(R.drawable.ic_action_arrow_up);
                } else if (vis == View.VISIBLE) {
                    contactDetailsLayout.setVisibility(View.GONE);
                    imgContactDetails.setImageResource(R.drawable.ic_action_arrow_down);
                }
            }
        });
        imgAddressDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation in = new AlphaAnimation(0.0f, 1.0f);
                in.setDuration(800);

                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(800);
                LinearLayout addressDetailsLayout = (LinearLayout) findViewById(R.id.layoutAddressDetails);
                int vis = addressDetailsLayout.getVisibility();
                if (vis == View.GONE) {
                    addressDetailsLayout.setVisibility(View.VISIBLE);
                    imgAddressDetails.setImageResource(R.drawable.ic_action_arrow_up);
                } else if (vis == View.VISIBLE) {
                    addressDetailsLayout.setVisibility(View.GONE);
                    imgAddressDetails.setImageResource(R.drawable.ic_action_arrow_down);
                }
            }
        });
    }

/* ========================================= MENU =============================================== */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_in_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_userProfile).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_logout:
                UserController uc = new UserController(this);
                uc.logoutUser();
                startActivity(new Intent(this, Main.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
