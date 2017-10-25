package gr.homedeco.www.homedeco.order.creation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import gr.homedeco.www.homedeco.R;

public class OrderPayment extends Fragment {

    private LinearLayout layout;
    private Spinner spPaymentMethod;
    private LinearLayout creditCardLayout, paypalLayout, cashOnDeliveryLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_payment, container, false);

        layout = (LinearLayout) view.findViewById(R.id.fragment_order_payment_layout);
        creditCardLayout = (LinearLayout) view.findViewById(R.id.orderPaymentCreditCardLayout);
        paypalLayout = (LinearLayout) view.findViewById(R.id.orderPaymentPayPalLayout);
        cashOnDeliveryLayout = (LinearLayout) view.findViewById(R.id.orderPaymentCODLayout);
        spPaymentMethod = (Spinner) view.findViewById(R.id.spOrderPaymentMethod);

        initListeners();
        return view;
    }

    //------------------------------------- HELPERS ---------------------------------------------------//

    private void initListeners() {

        spPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Integer selectedPos = spPaymentMethod.getSelectedItemPosition();
                switch (selectedPos) {
                    case 0:
                        creditCardLayout.setVisibility(View.GONE);
                        paypalLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        break;
                    case 1:
                        creditCardLayout.setVisibility(View.VISIBLE);
                        paypalLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        paypalLayout.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        break;
                    case 3:
                        cashOnDeliveryLayout.setVisibility(View.VISIBLE);
                        paypalLayout.setVisibility(View.GONE);
                        creditCardLayout.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
