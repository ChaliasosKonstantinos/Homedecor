package gr.homedeco.www.homedeco.product.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import gr.homedeco.www.homedeco.R;

public class CustomProductColor extends Fragment {

    private Button bColor1, bColor2, bColor3;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_color, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        bColor1 = (Button) view.findViewById(R.id.bColor1);
        bColor2 = (Button) view.findViewById(R.id.bColor2);
        bColor3 = (Button) view.findViewById(R.id.bColor3);

        bColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("#3E2723");
            }
        });
        bColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("#000000");
            }
        });
        bColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerChoiceAndReroute("#B71C1C");
            }
        });
        return view;
    }

    // ----------------------------------- HELPERS -------------------------------------------------//

    // Register the choice and routes to next tab
    private void registerChoiceAndReroute(String choice) {
        ((CustomProduct) getActivity()).setCustomProduct(choice);
        Snackbar snackbar = Snackbar.make(linearLayout, "Το χρώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
        snackbar.show();
        ((CustomProduct) getActivity()).getPager().setCurrentItem(2);
    }

}
