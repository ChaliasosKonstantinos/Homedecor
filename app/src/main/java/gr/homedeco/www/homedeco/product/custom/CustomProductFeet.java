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

public class CustomProductFeet extends Fragment {

    private ImageButton imgbFeet1, imgbFeet2, imgbFeet3;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_feet, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgbFeet1 = (ImageButton) view.findViewById(R.id.imgbFeet1);
        imgbFeet2 = (ImageButton) view.findViewById(R.id.imgbFeet2);
        imgbFeet3 = (ImageButton) view.findViewById(R.id.imgbFeet3);

        String product = ((CustomProduct) getActivity()).getCustomProduct();
        String[] parts = product.split("-");
        switch (parts[0]) {
            case "1":
                imgbFeet1.setImageResource(R.drawable.podia_grafeiou_1);
                imgbFeet2.setImageResource(R.drawable.podia_grafeiou_2);
                imgbFeet3.setImageResource(R.drawable.podia_grafeiou_3);
                break;
            case "2":
                imgbFeet1.setImageResource(R.drawable.kalymma_kanape_1);
                imgbFeet2.setImageResource(R.drawable.kalymma_kanape_2);
                imgbFeet3.setImageResource(R.drawable.kalymma_kanape_3);
                break;
            case "3":
                imgbFeet1.setImageResource(R.drawable.podia_krevatiou_1);
                imgbFeet2.setImageResource(R.drawable.podia_krevatiou_2);
                imgbFeet3.setImageResource(R.drawable.podia_krevatiou_3);
                break;
            default:
                break;
        }

        imgbFeet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("1");
            }
        });
        imgbFeet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("2");
            }
        });
        imgbFeet3.setOnClickListener(new View.OnClickListener() {
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
        Snackbar snackbar = Snackbar.make(linearLayout, "Το προιόν ολοκληρώθηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProduct) getActivity()).getPager().setCurrentItem(4);
    }
}
