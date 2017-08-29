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

import gr.homedeco.www.homedeco.R;

public class CustomProductBody extends Fragment {

    private ImageButton imgbBody1, imgbBody2, imgbBody3;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_body, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgbBody1 = (ImageButton) view.findViewById(R.id.imgbBody1);
        imgbBody2 = (ImageButton) view.findViewById(R.id.imgbBody2);
        imgbBody3 = (ImageButton) view.findViewById(R.id.imgbBody3);

        String product = ((CustomProduct) getActivity()).getCustomProduct();
        System.out.println("Product");
        System.out.println(product);
        String[] parts = product.split("-");
        System.out.println("PRODUCT TYPE");
        System.out.println(parts[0]);
        switch (parts[0]) {
            case "1":
                imgbBody1.setImageResource(R.drawable.swma_grafeiou_1);
                imgbBody2.setImageResource(R.drawable.swma_grafeiou_2);
                imgbBody3.setImageResource(R.drawable.swma_grafeiou_3);
                break;
            case "2":
                imgbBody1.setImageResource(R.drawable.swma_kanape_1);
                imgbBody2.setImageResource(R.drawable.swma_kanape_2);
                imgbBody3.setImageResource(R.drawable.swma_kanape_3);
                break;
            case "3":
                imgbBody1.setImageResource(R.drawable.swma_krevatiou_1);
                imgbBody2.setImageResource(R.drawable.swma_krevatiou_2);
                imgbBody3.setImageResource(R.drawable.swma_krevatiou_3);
                break;
            default:
                break;
        }

        imgbBody1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("1");
            }
        });
        imgbBody2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("2");
            }
        });
        imgbBody3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("3");
            }
        });
        return view;
    }

    // ----------------------------------- HELPERS -------------------------------------------------//

    // Register the choice and routes to next tab
    private void registerChoiceAndReroute(String choice) {
        ((CustomProduct) getActivity()).setCustomProduct(choice);
        Snackbar snackbar = Snackbar.make(linearLayout, "Το σώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProduct) getActivity()).getPager().setCurrentItem(3);
    }

}
