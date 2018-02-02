package gr.homedeco.www.homedeco.product.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.product.CustomProduct;

public class CustomProductBody extends Fragment {

    private ImageButton imgbBody1, imgbBody2, imgbBody3;
    private LinearLayout linearLayout;
    private List<CustomProduct> cProductsFiltered = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_body, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgbBody1 = (ImageButton) view.findViewById(R.id.imgbBody1);
        imgbBody2 = (ImageButton) view.findViewById(R.id.imgbBody2);
        imgbBody3 = (ImageButton) view.findViewById(R.id.imgbBody3);

        setupListeners();
        filterProducts();
        populateView();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    private void setupListeners() {
        imgbBody1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(0).getId(), cProductsFiltered.get(0).getPrice());
            }
        });
        imgbBody2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(1).getId(), cProductsFiltered.get(1).getPrice());
            }
        });
        imgbBody3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(2).getId(), cProductsFiltered.get(2).getPrice());
            }
        });
    }

    private void filterProducts() {
        // Filter custom products for body items
        List<CustomProduct> cPFiltered = ((CustomProducts) getActivity()).getFilteredCustomProducts();
        for (Integer i=0; i < cPFiltered.size(); i++) {
            CustomProduct cProduct = cPFiltered.get(i);
            String[] parts = cProduct.getPart().split("_");
            if (Objects.equals(parts[1], "body")) {
                cProductsFiltered.add(cProduct);
            }
        }
    }

    private void populateView() {
        // Load images
        String image_url = "http://83.212.101.162/" + cProductsFiltered.get(0).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbBody1);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(1).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbBody2);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(2).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbBody3);
    }

    // Register the choice and routes to next tab
    private void registerChoiceAndReroute(Integer id, double price) {
        CustomProduct.CPart part = ((CustomProducts) getActivity()).getCustomProduct().createCPart();
        part.setId(id);
        part.setPrice(price);
        ((CustomProducts) getActivity()).getCustomProduct().addCPart(part);
        Snackbar snackbar = Snackbar.make(linearLayout, "Το σώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProducts) getActivity()).getPager().setCurrentItem(3);
    }

}
