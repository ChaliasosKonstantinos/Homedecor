package gr.homedeco.www.homedeco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomProductType extends Fragment {

    private ImageView imgType1, imgType2, imgType3;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_product_type, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        imgType1 = (ImageView) view.findViewById(R.id.imgType1);
        imgType2 = (ImageView) view.findViewById(R.id.imgType2);
        imgType3 = (ImageView) view.findViewById(R.id.imgType3);
        ((CustomProduct) getActivity()).clearCustomProduct();

        imgType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CustomProduct) getActivity()).setCustomProduct("1");
                ((CustomProduct) getActivity()).getPager().setCurrentItem(1);
            }
        });
        imgType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CustomProduct) getActivity()).setCustomProduct("2");
                ((CustomProduct) getActivity()).getPager().setCurrentItem(1);
            }
        });
        imgType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CustomProduct) getActivity()).setCustomProduct("3");
                ((CustomProduct) getActivity()).getPager().setCurrentItem(1);
            }
        });
        return view;
    }

}
