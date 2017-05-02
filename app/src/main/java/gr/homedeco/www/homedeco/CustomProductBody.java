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

import com.squareup.picasso.Picasso;

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
        String image_url = "http://www.ikea.gr/images/110x110/20276941/tidafors-dithesios-kanapes-0.jpg";
        Picasso.with(getContext()).load(image_url).into(imgbBody1);
        image_url = "http://www.ikea.gr/images/110x110/20276941/tidafors-dithesios-kanapes-0.jpg";
        Picasso.with(getContext()).load(image_url).into(imgbBody2);
        image_url = "http://www.ikea.gr/images/110x110/20276941/tidafors-dithesios-kanapes-0.jpg";
        Picasso.with(getContext()).load(image_url).into(imgbBody3);

        imgbBody1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(linearLayout, "Το σώμα αποθηκεύτηκε", Snackbar.LENGTH_LONG);
                snackbar.show();
                ((CustomProduct) getActivity()).getPager().setCurrentItem(2);
            }
        });
        return view;
    }

}
