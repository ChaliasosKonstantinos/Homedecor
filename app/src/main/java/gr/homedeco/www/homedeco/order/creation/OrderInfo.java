package gr.homedeco.www.homedeco.order.creation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.user.User;

public class OrderInfo extends Fragment {

    private EditText etFullname, etEmail, etPhone, etMobilePhone;
    private Button btnGoToOrderShipping;
    private boolean infosAreValid = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);

        etFullname = (EditText) view.findViewById(R.id.etOrderName);
        etEmail = (EditText) view.findViewById(R.id.etOrderEmail);
        etPhone = (EditText) view.findViewById(R.id.etOrderPhone);
        etMobilePhone = (EditText) view.findViewById(R.id.etOrderMobilePhone);
        btnGoToOrderShipping = (Button) view.findViewById(R.id.btnGoToOrderShipping);

        initListeners();
        populateView();
        return view;
    }

//------------------------------------- HELPERS ---------------------------------------------------//

    // Init UI listeners
    private void initListeners() {
        btnGoToOrderShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infosAreValid) {
                    Order order = new Order();
                    order.setFullName(etFullname.getText().toString());
                    order.setEmail(etEmail.getText().toString());
                    order.setPhone(etPhone.getText().toString());
                    order.setMobilePhone(etMobilePhone.getText().toString());
                    ((OrderCreation) getActivity()).saveOrderState(order);
                    ((OrderCreation) getActivity()).getPager().setCurrentItem(1);
                }
            }
        });
        etFullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etFullname.getText().toString().isEmpty()) {
                        showErrorIcon(etFullname);
                    } else {
                        showSuccessIcon(etFullname);
                    }
                }
            }
        });
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etEmail.getText().toString().isEmpty()) {
                        showErrorIcon(etEmail);
                    } else {
                        showSuccessIcon(etEmail);
                    }
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etPhone.getText().toString().isEmpty()
                            || etPhone.getText().toString().length() < 10) {
                        showErrorIcon(etPhone);
                    } else {
                        showSuccessIcon(etPhone);
                    }
                }
            }
        });
        etMobilePhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etMobilePhone.getText().toString().isEmpty()
                            || etMobilePhone.getText().toString().length() < 10) {
                        showErrorIcon(etMobilePhone);
                    } else {
                        showSuccessIcon(etMobilePhone);
                    }
                }
            }
        });
    }

    // Populates the View only if user is logged in
    private void populateView() {
        LocalDatabase localDatabase = new LocalDatabase(getContext());
        if (localDatabase.isLoggedIn()) {
            User user = localDatabase.getUserDetails();
            etFullname.setText(user.getFirstName() + " " + user.getLastName());
            etEmail.setText(user.getEmail());
            etPhone.setText(user.getPhone());
            etMobilePhone.setText(user.getMobilePhone());
        }
    }

    // Show success icon to given EditText
    private void showSuccessIcon(EditText et) {
        Drawable x = ContextCompat.getDrawable(getContext(),R.drawable.ic_action_success);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        et.setCompoundDrawables(null, null, x, null);
        infosAreValid = true;
    }

    // Show error icon to given EditText
    private void showErrorIcon(EditText et) {
        Drawable x = ContextCompat.getDrawable(getContext(),R.drawable.ic_action_edit);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        et.setCompoundDrawables(null, null, x, null);
        infosAreValid = false;
    }
}
