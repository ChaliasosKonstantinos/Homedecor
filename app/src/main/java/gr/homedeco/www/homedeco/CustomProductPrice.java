package gr.homedeco.www.homedeco;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CustomProductPrice extends Fragment {

    private TextView tvProductName, tvProductColorText, tvProductBodyText, tvProductFeetText,
            tvProductPriceText, tvProductVATText, tvProductTotalPriceText;
    private Button btnAddToCart;
    private RelativeLayout relativeLayout;

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

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Save product to cart
                Snackbar snackbar = Snackbar.make(relativeLayout, "Προστέθηκε στο καλάθι!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        return view;
    }
}
