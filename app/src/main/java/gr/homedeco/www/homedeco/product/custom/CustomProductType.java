package gr.homedeco.www.homedeco.product.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.product.CustomProduct;

public class CustomProductType extends Fragment {

    private ImageView imgType1, imgType2, imgType3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_type, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgType1 = (ImageView) view.findViewById(R.id.imgType1);
        imgType2 = (ImageView) view.findViewById(R.id.imgType2);
        imgType3 = (ImageView) view.findViewById(R.id.imgType3);
        ((CustomProducts) getActivity()).getCustomProduct().clearCustomProduct();

        setupListeners();
        return view;
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Setup listeners on custom product type choices
     */
    private void setupListeners() {
        imgType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCategoryId(0)) {
                    ((CustomProducts) getActivity()).getPager().setCurrentItem(1);
                }
            }
        });
        imgType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCategoryId(1)) {
                    ((CustomProducts) getActivity()).getPager().setCurrentItem(1);
                }
            }
        });
        imgType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCategoryId(2)) {
                    ((CustomProducts) getActivity()).getPager().setCurrentItem(1);
                }
            }
        });
    }

    /**
     * Filters custom products to find out the category id
     * On SUCCESS: filters custom products to keep the products from the chosen category
     *
     * @param typeId the chosen type id
     *
     * @return TRUE if the products are filtered correctly
     *         FALSE if no product with the specific category id are found
     */
    private boolean getCategoryId(int typeId) {
        List<CustomProduct> cProducts = ((CustomProducts) getActivity()).getCustomProducts();
        String[] types = {"desk","sofa","bed"};
        Boolean found = false;
        int i = 0;
        while(!found && i < cProducts.size()) {
            String[] parts = cProducts.get(i).getPart().split("_");
            String category = parts[0];
            if (Objects.equals(category, types[typeId])) {
                ((CustomProducts) getActivity()).getCustomProduct().setCategoryId(cProducts.get(i).getCategoryId());
                found = true;
            }
            i++;
        }
        found = filterProductsById(cProducts);
        return found;
    }

    /**
     * Filters custom products to get only the ones with selected category id
     *
     * @param cProducts a list of CustomProduct objects to filter through
     *
     * @return TRUE if the products are filtered correctly
     *         FALSE if no product with the specific category id are found
     */
    private boolean filterProductsById(List<CustomProduct> cProducts) {
        int categoryId = ((CustomProducts) getActivity()).getCustomProduct().getCategoryId();
        List<CustomProduct> cPFiltered = new ArrayList<>();
        for (int i=0; i < cProducts.size(); i++) {
            CustomProduct cProduct = cProducts.get(i);
            if ((Objects.equals(cProduct.getCategoryId(), categoryId))) {
                cPFiltered.add(cProduct);
            }
        }
        if (cPFiltered.size() > 0) {
            ((CustomProducts) getActivity()).setFilteredCustomProducts(cPFiltered);
            return true;
        } else {
            return false;
        }
    }

}
