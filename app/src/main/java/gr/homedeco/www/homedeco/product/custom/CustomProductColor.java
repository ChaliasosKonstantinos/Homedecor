package gr.homedeco.www.homedeco.product.custom;

import android.os.Bundle;
import android.os.Handler;
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

public class CustomProductColor extends Fragment {

    private ImageButton imgbColor1, imgbColor2, imgbColor3;
    private LinearLayout linearLayout;
    private List<CustomProduct> cProductsFiltered = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_color, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgbColor1 = (ImageButton) view.findViewById(R.id.imgbColor1);
        imgbColor2 = (ImageButton) view.findViewById(R.id.imgbColor2);
        imgbColor3 = (ImageButton) view.findViewById(R.id.imgbColor3);
        runnable = new Runnable() {
            @Override
            public void run() {
                filterProducts();
            }
        };

        setupListeners();
        filterProducts();
        return view;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Setup listeners on custom product color choices
     */
    private void setupListeners() {
        imgbColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(0).getId(), cProductsFiltered.get(0).getPrice());
            }
        });
        imgbColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(1).getId(), cProductsFiltered.get(1).getPrice());
            }
        });
        imgbColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute(cProductsFiltered.get(2).getId(), cProductsFiltered.get(2).getPrice());
            }
        });
    }

    /**
     * Filters all custom product parts to retrieve color choices
     */
    private void filterProducts() {
        List<CustomProduct> cPFiltered = ((CustomProducts) getActivity()).getFilteredCustomProducts();
        if (cPFiltered.size() > 0) {
            stopReFilter();
            for (Integer i=0; i < cPFiltered.size(); i++) {
                CustomProduct cProduct = cPFiltered.get(i);
                String[] parts = cProduct.getPart().split("_");
                if (Objects.equals(parts[1], "color")) {
                    cProductsFiltered.add(cProduct);
                }
            }
            populateView();
        } else {
            reFilter();
        }
    }

    /**
     * Populates the view with custom product parts images
     */
    private void populateView() {
        String image_url = "http://83.212.101.162/" + cProductsFiltered.get(0).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor1);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(1).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor2);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(2).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor3);
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
        Snackbar snackbar = Snackbar.make(linearLayout, "Το χρώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProducts) getActivity()).getPager().setCurrentItem(2);
    }

    /**
     * Re-filters and Re-populates the view because ViewPager pre-renders content when data aren't available
     */
    private void reFilter() {
        handler.postDelayed(runnable, 500);
    }

    /**
     * Stop re-filtering when view is successfully rendered
     */
    private void stopReFilter() {
        handler.removeCallbacks(runnable);
    }

}
