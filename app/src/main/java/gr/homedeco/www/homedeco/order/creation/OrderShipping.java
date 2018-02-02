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
import android.widget.LinearLayout;
import android.widget.Spinner;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.user.User;
import gr.homedeco.www.homedeco.user.UserController;

public class OrderShipping extends Fragment {

    private EditText etCountry, etState, etCity, etAddress, etPostalCode;
    private Button btnGoToOrderPayment;
    private Spinner spShippingMethod;
    private LinearLayout layout;
    private boolean infosAreValid = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_shipping, container, false);

        layout = (LinearLayout) view.findViewById(R.id.fragment_order_shipping_layout);
        etCountry = (EditText) view.findViewById(R.id.etOrderCountry);
        etState = (EditText) view.findViewById(R.id.etOrderState);
        etCity = (EditText) view.findViewById(R.id.etOrderCity);
        etAddress = (EditText) view.findViewById(R.id.etOrderShippingAddress);
        etPostalCode = (EditText) view.findViewById(R.id.etOrderPostalCode);
        spShippingMethod = (Spinner) view.findViewById(R.id.spOrderShippingMethod);
        btnGoToOrderPayment = (Button) view.findViewById(R.id.btnGoToOrderPayment);

        initListeners();
        populateView();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    // Init UI listeners
    private void initListeners() {
        btnGoToOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infosAreValid) {
                    Order order = ((OrderCreation) getActivity()).getOrderState();
                    order.setCountry(etCountry.getText().toString());
                    order.setState(etState.getText().toString());
                    order.setCity(etCity.getText().toString());
                    order.setShipAddress(etAddress.getText().toString());
                    order.setBillAddress(etAddress.getText().toString());
                    order.setPostalCode(etPostalCode.getText().toString());
                    order.setShippingMethod(spShippingMethod.getSelectedItem().toString());

                    ((OrderCreation) getActivity()).saveOrderState(order);
                    ((OrderCreation) getActivity()).getPager().setCurrentItem(2);
                }
            }
        });
        etCountry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etCountry.getText().toString().isEmpty()) {
                        showErrorIcon(etCountry);
                    } else {
                        showSuccessIcon(etCountry);
                    }
                }
            }
        });
        etState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etState.getText().toString().isEmpty()) {
                        showErrorIcon(etState);
                    } else {
                        showSuccessIcon(etState);
                    }
                }
            }
        });
        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etCity.getText().toString().isEmpty()) {
                        showErrorIcon(etCity);
                    } else {
                        showSuccessIcon(etCity);
                    }
                }
            }
        });
        etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etAddress.getText().toString().isEmpty()) {
                        showErrorIcon(etAddress);
                    } else {
                        showSuccessIcon(etAddress);
                    }
                }
            }
        });
        etPostalCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etPostalCode.getText().toString().isEmpty()
                            || etPostalCode.getText().toString().length() < 5) {
                        showErrorIcon(etPostalCode);
                    } else {
                        showSuccessIcon(etPostalCode);
                    }
                }
            }
        });
    }

    // Populates the View only if user is logged in
    private void populateView() {
        UserController uController = new UserController(getContext());
        if (uController.isUserLoggedIn()) {
            User user = uController.getUserDetails();
            etCountry.setText(user.getCountry());
            etState.setText(user.getState());
            etCity.setText(user.getCity());
            etAddress.setText(user.getAddress());
            etPostalCode.setText(user.getPostalCode());
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
