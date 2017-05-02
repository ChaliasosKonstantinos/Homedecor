package gr.homedeco.www.homedeco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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

        imgbFeet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(linearLayout, "Το προιόν ολοκληρώθηκε", Snackbar.LENGTH_LONG);
                snackbar.show();
                ((CustomProduct) getActivity()).getPager().setCurrentItem(4);
            }
        });
        return view;
    }
}
