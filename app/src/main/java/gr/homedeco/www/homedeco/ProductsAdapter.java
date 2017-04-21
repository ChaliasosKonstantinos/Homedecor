package gr.homedeco.www.homedeco;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> dataList;
    private Context myContext;

    public ProductsAdapter(List<Product> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        myContext = parent.getContext();
        View view = layoutInflater.inflate(R.layout.custom_products_row, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        String id = String.valueOf(dataList.get(position).getProductID());
        holder.tvProductsID.setText(id);
        holder.tvProductsDesc.setText(dataList.get(position).getShortDescription());
        holder.tvProductsName.setText(dataList.get(position).getName());
        String price = String.valueOf(dataList.get(position).getPrice()) + " €";
        holder.tvProductsPrice.setText(price);
//        String image_url = "http://83.212.107.169/" + dataList.get(position).getImage();
        String image_url = dataList.get(position).getImage();
        Picasso.with(myContext).load(image_url).into(holder.imgProductsPhoto);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductsDesc, tvProductsName, tvProductsPrice, tvProductsID;
        ImageView imgProductsPhoto;

        ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvProductsID = (TextView) itemView.findViewById(R.id.tvProductsID);
            tvProductsDesc = (TextView) itemView.findViewById(R.id.tvProductsDesc);
            tvProductsName = (TextView) itemView.findViewById(R.id.tvProductsName);
            tvProductsPrice = (TextView) itemView.findViewById(R.id.tvProductsPrice);
            imgProductsPhoto = (ImageView) itemView.findViewById(R.id.imgvProductsPhoto);
        }

        @Override
        public void onClick(View v) {
            int productID;
            TextView tvProductID;

            tvProductID = (TextView) v.findViewById(R.id.tvProductsID);
            productID = Integer.parseInt(tvProductID.getText().toString());
            Intent intent = new Intent(v.getContext(), ProductDetails.class);
            intent.putExtra("productID", productID);
            myContext.startActivity(intent);
        }
    }

}
