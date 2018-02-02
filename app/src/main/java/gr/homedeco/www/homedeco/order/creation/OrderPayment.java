package gr.homedeco.www.homedeco.order.creation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.vinaygaba.creditcardview.CreditCardView;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.cart.CartController;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.server.callbacks.GetOrderCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.server.response.ServerResponse;

public class OrderPayment extends Fragment {

    private LinearLayout layout;
    private Spinner spPaymentMethod;
    private LinearLayout creditCardLayout, paypalLayout, cashOnDeliveryLayout, BankTransferLayout;
    private Button btnCompleteOrder;
    private ImageButton btnPaypal;
    private CreditCardView creditCard;
    private static PayPalConfiguration paypalConfig;

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
        btnPaypal = (ImageButton) view.findViewById(R.id.btnPaypal);

        initListeners();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("Paypal Payment", confirm.toJSONObject().toString(4));
                    Snackbar snackbar = Snackbar.make(layout, R.string.order_pay_with_paypal_success,
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                    btnCompleteOrder.setEnabled(true);
                } catch (JSONException e) {
                    Log.e("Paypal Payment", "ERROR: ", e);
                }
            }
        }
    }

    // Paypal service start
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paypalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("AZFF5J19kL26J9ozvxkjXznR3UgV7Ia20Q-BlHUYBSb5kKth9pdsihfb3Ay4CixuVYmDIWYYQJ_HTP8k");
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        getActivity().startService(intent);
    }

    // Paypal service stop
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

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
                        btnCompleteOrder.setEnabled(false);
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

        // Pay with Paypal
        btnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Paypal transaction service
                Order order = ((OrderCreation) getActivity()).getOrderState();
                PayPalPayment payment = new PayPalPayment(new BigDecimal(order.getPrice()), "EUR",
                        order.getFullName(), PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, 0);
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
                                CartController cController = new CartController(getContext());
                                cController.clearCart();
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
