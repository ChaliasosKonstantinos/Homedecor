package gr.homedeco.www.homedeco.order.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.order.Order;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>{

    private List<Order> dataList;

    public OrderHistoryAdapter(List<Order> dataList) {
        this.dataList = dataList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_order_history_row, parent, false);
        return new OrderViewHolder(view);
    }

    /**
     * Populates the view with order's details
     *
     * @param holder an OrderViewHolder to populate
     * @param position the position of the holder on the list
     */
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        String id = String.valueOf(dataList.get(position).getOrderID());
        holder.tvOrderID.setText("# " + id);
        holder.tvOrderStatus.setText(dataList.get(position).getStatus());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataList.get(position).getDate());
        String date = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH)+1)
                + "/" + cal.get(Calendar.YEAR);
        holder.tvOrderDate.setText(date);
        String price = String.valueOf(dataList.get(position).getPrice()) + " €";
        holder.tvOrderPrice.setText(price);
        holder.tvOrderShippingMethod.setText(dataList.get(position).getShippingMethod());
        holder.tvOrderShipAddress.setText(dataList.get(position).getShipAddress() + ", "
                + dataList.get(position).getPostalCode());
        holder.tvOrderPhone.setText(dataList.get(position).getPhone() + ", "
                + dataList.get(position).getMobilePhone());
    }

    /**
     * Returns the number of items on the list
     *
     * @return the number of items on the list
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Converting the view holder into a recycler view
     */
    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvOrderID, tvOrderDate, tvOrderPrice, tvOrderShipAddress, tvOrderPhone, tvOrderShippingMethod,
                tvOrderStatus;
        RelativeLayout detailsLayout;
        ImageView imgExpandIcon;

        OrderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvOrderID = (TextView) itemView.findViewById(R.id.tvOrderID);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDate = (TextView) itemView.findViewById(R.id.tvOrderDate);
            tvOrderPrice = (TextView) itemView.findViewById(R.id.tvOrderPriceText);
            tvOrderShipAddress = (TextView) itemView.findViewById(R.id.tvOrderShipAddressText);
            tvOrderPhone = (TextView) itemView.findViewById(R.id.tvOrderPhoneText);
            tvOrderShippingMethod = (TextView) itemView.findViewById(R.id.tvOrderShippingMethodText);
            detailsLayout = (RelativeLayout) itemView.findViewById(R.id.layoutOrderDetails);
            imgExpandIcon = (ImageView) itemView.findViewById(R.id.imgOrderDetails);
        }

        /**
         * Setup listeners on expandable order's details card views
         */
        @Override
        public void onClick(View v) {
            final Animation in = new AlphaAnimation(0.0f, 1.0f);
            in.setDuration(800);

            final Animation out = new AlphaAnimation(1.0f, 0.0f);
            out.setDuration(800);

            int vis = detailsLayout.getVisibility();
            if (vis == View.GONE) {
                detailsLayout.setVisibility(View.VISIBLE);
                imgExpandIcon.setImageResource(R.drawable.ic_action_arrow_up);
            } else if (vis == View.VISIBLE) {
                detailsLayout.setVisibility(View.GONE);
                imgExpandIcon.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }
    }
}
