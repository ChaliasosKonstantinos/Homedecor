package gr.homedeco.www.homedeco.product.custom;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;


public class CustomProductPrice extends Fragment {

    private TextView tvProductName, tvProductColorText, tvProductBodyText, tvProductFeetText,
            tvProductPriceText, tvProductVATText, tvProductTotalPriceText;
    private Button btnAddToCart;
    private RelativeLayout relativeLayout;
    private LocalDatabase localDatabase;
    private int productID = 0;

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
        localDatabase = new LocalDatabase(getContext());

        String product = ((CustomProducts) getActivity()).getCustomProduct();
        Log.d("CUSTOM PRODUCT FINAL", product);
        String[] parts = product.split("-");
        double bodyPrice = Double.parseDouble(parts[3]);
        double feetPrice = 10;
        if (parts.length > 4) {
            feetPrice = Double.parseDouble(parts[5]);
        }
        double price = bodyPrice + feetPrice;
        double vat, total;

        switch (parts[0]) {
            case "1":
                tvProductName.setText(R.string.desk);
                break;
            case "2":
                tvProductName.setText(R.string.sofa);
                break;
            case "3":
                tvProductName.setText(R.string.bed);
                break;
            default:
                tvProductName.setText("-");
                break;
        }
        tvProductColorText.setBackgroundColor(Color.parseColor(parts[1]));
        tvProductPriceText.setText(price + "€");
        vat = (price/100)*24;
        vat = Math.floor(vat * 100) / 100;
        tvProductVATText.setText(vat + "€");
        total = price + vat;
        total = Math.floor(total * 100) / 100;
        tvProductTotalPriceText.setText(total + "€");

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: CUSTOM PRODUCT ORDER
                Snackbar snackbar = Snackbar.make(relativeLayout, R.string.cart_added_product, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        return view;
    }
}
