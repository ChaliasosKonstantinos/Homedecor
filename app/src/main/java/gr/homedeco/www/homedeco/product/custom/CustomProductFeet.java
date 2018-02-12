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

public class CustomProductFeet extends Fragment {

    private ImageButton imgbFeet1, imgbFeet2, imgbFeet3;
    private LinearLayout linearLayout;
    private List<CustomProduct> cProductsFiltered = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_feet, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgbFeet1 = (ImageButton) view.findViewById(R.id.imgbFeet1);
        imgbFeet2 = (ImageButton) view.findViewById(R.id.imgbFeet2);
        imgbFeet3 = (ImageButton) view.findViewById(R.id.imgbFeet3);

        setupListeners();
        filterProducts();
        populateView();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Setup listeners on custom product feet choices
     */
    private void setupListeners() {
        imgbFeet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(0).getId(), cProductsFiltered.get(0).getPrice());
            }
        });
        imgbFeet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(1).getId(), cProductsFiltered.get(1).getPrice());
            }
        });
        imgbFeet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(2).getId(), cProductsFiltered.get(2).getPrice());
            }
        });
    }

    /**
     * Filters all custom product parts to retrieve feet choices
     */
    private void filterProducts() {
        // Filter custom products for feet item
        List<CustomProduct> cPFiltered = ((CustomProducts) getActivity()).getFilteredCustomProducts();
        for (Integer i=0; i < cPFiltered.size(); i++) {
            CustomProduct cProduct = cPFiltered.get(i);
            String[] parts = cProduct.getPart().split("_");
            if (Objects.equals(parts[1], "leg")) {
                cProductsFiltered.add(cProduct);
            }
        }
    }

    /**
     * Populates the view with custom product parts images
     */
    private void populateView() {
        String image_url = "http://83.212.101.162/" + cProductsFiltered.get(0).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbFeet1);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(1).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbFeet2);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(2).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbFeet3);
    }

    /**
     * Registers user choice and reroutes the user to the next section
     *
     * @param id the id of the chosen part
     * @param price the price of the chosen part
     */
    private void registerChoiceAndReroute(Integer id, double price) {
        CustomProduct.CPart part = ((CustomProducts) getActivity()).getCustomProduct().createCPart();
        part.setId(id);
        part.setPrice(price);
        ((CustomProducts) getActivity()).getCustomProduct().addCPart(part);
        Snackbar snackbar = Snackbar.make(linearLayout, "Το προιόν ολοκληρώθηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProducts) getActivity()).getPager().setCurrentItem(4);
    }
}
