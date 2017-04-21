package gr.homedeco.www.homedeco;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> dataList;
    private Context myContext;

    public CartAdapter(List<Product> dataList) {
            this.dataList = dataList;
            }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        myContext = parent.getContext();
        View view = layoutInflater.inflate(R.layout.custom_cart_row, parent, false);
        System.out.println("DIKO MOU!");
        System.out.println(dataList);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {
        String id = String.valueOf(dataList.get(position).getProductID());
        holder.tvProductsID.setText(id);
        holder.tvProductsName.setText(dataList.get(position).getName());
        String price = String.valueOf(dataList.get(position).getPrice()) + " €";
        holder.tvProductsPrice.setText(price);
        // MOCKUP TODO: DELETE IT
        // String image_url = "http://83.212.107.169/" + dataList.get(position).getImage();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductsName, tvProductsPrice, tvProductsID;

        CartViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvProductsID = (TextView) itemView.findViewById(R.id.tvProductsID);
            tvProductsName = (TextView) itemView.findViewById(R.id.tvProductsName);
            tvProductsPrice = (TextView) itemView.findViewById(R.id.tvProductsPrice);
        }

        @Override
        public void onClick(View v) {
            int productID;
            TextView tvProductID;

            tvProductID = (TextView) v.findViewById(R.id.tvProductsID);
            productID = Integer.parseInt(tvProductID.getText().toString());
            // TODO: Remove item from cart
            Toast.makeText(myContext, "Product removed from cart", Toast.LENGTH_SHORT).show();
        }
    }
}
