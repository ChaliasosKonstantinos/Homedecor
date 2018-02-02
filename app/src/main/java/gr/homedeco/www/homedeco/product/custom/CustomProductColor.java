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

//    private Button bColor1, bColor2, bColor3;
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
//        bColor1 = (Button) view.findViewById(R.id.bColor1);
//        bColor2 = (Button) view.findViewById(R.id.bColor2);
//        bColor3 = (Button) view.findViewById(R.id.bColor3);
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
//        bColor1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerChoiceAndReroute("#3E2723");
//            }
//        });
//        bColor2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerChoiceAndReroute("#000000");
//            }
//        });
//        bColor3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerChoiceAndReroute("#B71C1C");
//            }
//        });
    }

    private void filterProducts() {
        List<CustomProduct> cPFiltered = ((CustomProducts) getActivity()).getFilteredCustomProducts();
        if (cPFiltered.size() > 0) {
            stopReFilter();
            // Filter custom products for color items
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

    private void populateView() {
        // Load images
        String image_url = "http://83.212.101.162/" + cProductsFiltered.get(0).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor1);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(1).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor2);
        image_url = "http://83.212.101.162/" + cProductsFiltered.get(2).getImage();
        Picasso.with(getContext()).load(image_url).fit().into(imgbColor3);
    }

    // Register the choice and routes to next tab
    private void registerChoiceAndReroute(Integer id, double price) {
        CustomProduct.CPart part = ((CustomProducts) getActivity()).getCustomProduct().createCPart();
        part.setId(id);
        part.setPrice(price);
        ((CustomProducts) getActivity()).getCustomProduct().addCPart(part);
        Snackbar snackbar = Snackbar.make(linearLayout, "Το χρώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProducts) getActivity()).getPager().setCurrentItem(2);
    }

    // Re-populates the view because ViewPager pre-renders content when data aren't available
    private void reFilter() {
        handler.postDelayed(runnable, 500);
    }

    private void stopReFilter() {
        handler.removeCallbacks(runnable);
    }

}
