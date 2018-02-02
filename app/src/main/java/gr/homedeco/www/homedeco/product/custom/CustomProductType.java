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
        return view;
    }

    private boolean getCategoryId(Integer typeId) {
        List<CustomProduct> cProducts = ((CustomProducts) getActivity()).getCustomProducts();
        String[] types = {"desk","sofa","bed"};
        Boolean found = false;
        Integer i = 0;
        // Find category id
        while(!found && i < cProducts.size()) {
            String[] parts = cProducts.get(i).getPart().split("_");
            String category = parts[0];
            if (Objects.equals(category, types[typeId])) {
                ((CustomProducts) getActivity()).getCustomProduct().setCategoryId(cProducts.get(i).getCategoryId());
                found = true;
            }
            i++;
        }
        // Filter products based on category id
        Integer categoryId = ((CustomProducts) getActivity()).getCustomProduct().getCategoryId();
        List<CustomProduct> cPFiltered = new ArrayList<>();
        for (i=0; i < cProducts.size(); i++) {
            CustomProduct cProduct = cProducts.get(i);
            if ((Objects.equals(cProduct.getCategoryId(), categoryId))) {
                cPFiltered.add(cProduct);
            }
        }
        ((CustomProducts) getActivity()).setFilteredCustomProducts(cPFiltered);
        return found;
    }

}
