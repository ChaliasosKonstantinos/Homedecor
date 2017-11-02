package gr.homedeco.www.homedeco.order.creation;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.vinaygaba.creditcardview.CreditCardView;

import java.util.HashMap;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.server.callbacks.GetOrderCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.server.response.ServerResponse;

public class OrderPayment extends Fragment {

    private LinearLayout layout;
    private Spinner spPaymentMethod;
    private LinearLayout creditCardLayout, paypalLayout, cashOnDeliveryLayout, BankTransferLayout;
    private Button btnCompleteOrder;
    private CreditCardView creditCard;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_payment, container, false);

        layout = (LinearLayout) view.findViewById(R.id.fragment_order_payment_layout);
        creditCardLayout = (LinearLayout) view.findViewById(R.id.orderPaymentCreditCardLayout);
        paypalLayout = (LinearLayout) view.findViewById(R.id.orderPaymentPayPalLayout);
        BankTransferLayout = (LinearLayout) view.findViewById(R.id.orderPaymentBankTransfer);
        cashOnDeliveryLayout = (LinearLayout) view.findViewById(R.id.orderPaymentCODLayout);
        spPaymentMethod = (Spinner) view.findViewById(R.id.spOrderPaymentMethod);
        btnCompleteOrder = (Button) view.findViewById(R.id.btnCompleteOrder);
        creditCard = (CreditCardView) view.findViewById(R.id.ccOrderCreditCard);

        initListeners();
        return view;
    }

    //------------------------------------- HELPERS ---------------------------------------------------//

    private void initListeners() {

        // Payment method spinner
        spPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Integer selectedPos = spPaymentMethod.getSelectedItemPosition();
                switch (selectedPos) {
                    case 1:
                        creditCardLayout.setVisibility(View.VISIBLE);
                        paypalLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        BankTransferLayout.setVisibility(View.GONE);
                        btnCompleteOrder.setEnabled(true);
                        break;
                    case 2:
                        paypalLayout.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        BankTransferLayout.setVisibility(View.GONE);
                        btnCompleteOrder.setEnabled(true);
                        break;
                    case 3:
                        BankTransferLayout.setVisibility(View.VISIBLE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        paypalLayout.setVisibility(View.GONE);
                        creditCardLayout.setVisibility(View.GONE);
                        btnCompleteOrder.setEnabled(true);
                        break;
                    case 4:
                        cashOnDeliveryLayout.setVisibility(View.VISIBLE);
                        BankTransferLayout.setVisibility(View.GONE);
                        paypalLayout.setVisibility(View.GONE);
                        creditCardLayout.setVisibility(View.GONE);
                        btnCompleteOrder.setEnabled(true);
                        break;
                    default:
                        creditCardLayout.setVisibility(View.GONE);
                        paypalLayout.setVisibility(View.GONE);
                        cashOnDeliveryLayout.setVisibility(View.GONE);
                        BankTransferLayout.setVisibility(View.GONE);
                        btnCompleteOrder.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // Send order
        btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spPaymentMethod.getSelectedItemPosition() != 0) {
                    Order order = ((OrderCreation) getActivity()).getOrderState();
                    if (spPaymentMethod.getSelectedItemPosition() == 1) {
                        order.setPaymentMethod(spPaymentMethod.getSelectedItem().toString());
                        HashMap<String, String> creditCardInfo = new HashMap<>();
                        creditCardInfo.put("number", creditCard.getCardNumber());
                        creditCardInfo.put("expire", creditCard.getExpiryDate());
                        creditCardInfo.put("name", creditCard.getCardName());
                        order.setCreditCard(creditCardInfo);
                    } else {
                        order.setPaymentMethod(spPaymentMethod.getSelectedItem().toString());
                    }

                    ServerRequests serverRequests = new ServerRequests(getContext());
                    serverRequests.createOrder(order, new GetOrderCallback() {
                        @Override
                        public void done(ServerResponse response) {
                            if (!response.getMessage().isEmpty()) {
                                Snackbar snackbar = Snackbar.make(layout, R.string.order_created, Snackbar.LENGTH_LONG);
                                snackbar.show();
                                LocalDatabase localDatabase = new LocalDatabase(getContext());
                                localDatabase.clearCart();
                                // TODO: Redirect to Order history
                            } else {
                                Snackbar snackbar = Snackbar.make(layout, R.string.order_creation_failed, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    });
                }
            }
        });
    }
}
