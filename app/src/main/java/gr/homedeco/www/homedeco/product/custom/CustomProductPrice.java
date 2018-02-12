package gr.homedeco.www.homedeco.product.custom;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.order.creation.OrderCreation;
import gr.homedeco.www.homedeco.product.CustomProduct;


public class CustomProductPrice extends Fragment {

    private TextView tvProductName, tvProductColorText, tvProductBodyText, tvProductFeetText,
            tvProductPriceText, tvProductVATText, tvProductTotalPriceText;
    private Button btnAddToCart;
    private RelativeLayout relativeLayout;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_price, container, false);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductColorText = (TextView) view.findViewById(R.id.tvProductColorText);
        tvProductBodyText = (TextView) view.findViewById(R.id.tvProductBodyText);
        tvProductFeetText = (TextView) view.findViewById(R.id.tvProductFeetText);
        tvProductPriceText = (TextView) view.findViewById(R.id.tvProductPriceText);
        tvProductVATText = (TextView) view.findViewById(R.id.tvProductVATText);
        tvProductTotalPriceText = (TextView) view.findViewById(R.id.tvProductTotalPriceText);
        btnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);

        runnable = new Runnable() {
            @Override
            public void run() {
                populateView();
            }
        };
        setupListeners();
        populateView();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Trying to calculates product's price
     * On SUCCESS: Populates the view
     * On ERROR: Retrying to calculate product's price
     */
    private void populateView() {
        CustomProduct cp = ((CustomProducts) getActivity()).getCustomProduct();
        if (cp.getCParts().size() == 3) {
            stopRerender();
            double bodyPrice = cp.getCParts().get(1).getPrice();
            double feetPrice = cp.getCParts().get(2).getPrice();
            double price = bodyPrice + feetPrice;
            double vat, total;

//        switch (parts[0]) {
//            case "1":
//                tvProductName.setText(R.string.desk);
//                break;
//            case "2":
//                tvProductName.setText(R.string.sofa);
//                break;
//            case "3":
//                tvProductName.setText(R.string.bed);
//                break;
//            default:
//                tvProductName.setText("-");
//                break;
//        }
//        tvProductColorText.setBackgroundColor(Color.parseColor(parts[1]));
            tvProductPriceText.setText(price + "€");
            vat = (price/100)*24;
            vat = Math.floor(vat * 100) / 100;
            tvProductVATText.setText(vat + "€");
            total = price + vat;
            total = Math.floor(total * 100) / 100;
            tvProductTotalPriceText.setText(total + "€");
        } else {
            startRerender();
        }
    }

    /**
     * Setup listeners on checkout button
     */
    private void setupListeners() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderCreation.class);
                intent.putExtra("type", "custom");
                intent.putExtra("customProduct", ((CustomProducts) getActivity()).getCustomProduct());
                startActivity(intent);
            }
        });
    }

    /**
     * Re-populates the view because ViewPager pre-renders content when data aren't available
     */
    private void startRerender() {
        handler.postDelayed(runnable, 500);
    }

    /**
     * Stop re-populating when view is successfully rendered
     */
    private void stopRerender() {
        handler.removeCallbacks(runnable);
    }
}
