package gr.homedeco.www.homedeco.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.product.Product;

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
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {
        String id = String.valueOf(dataList.get(position).getProductID());
        holder.tvProductsID.setText(id);
        holder.tvProductsName.setText(dataList.get(position).getName());
        String price = String.valueOf(dataList.get(position).getPrice()) + " €";
        holder.tvProductsPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductsName, tvProductsPrice, tvProductsID;
        ImageButton btnCartRemoveProduct;

        CartViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvProductsID = (TextView) itemView.findViewById(R.id.tvProductsID);
            tvProductsName = (TextView) itemView.findViewById(R.id.tvProductsName);
            tvProductsPrice = (TextView) itemView.findViewById(R.id.tvProductsPrice);
            btnCartRemoveProduct = (ImageButton) itemView.findViewById(R.id.btnCartRemoveProduct);
        }

        @Override
        public void onClick(View v) {
            int productID;
            TextView tvProductID = (TextView) v.findViewById(R.id.tvProductsID);
            productID = Integer.parseInt(tvProductID.getText().toString());

            if (productID > 0) {
                LocalDatabase localDatabase = new LocalDatabase(myContext);
                localDatabase.removeFromCart(productID);
                Toast.makeText(myContext, R.string.cart_removed_product , Toast.LENGTH_LONG).show();
            }
        }
    }
}
