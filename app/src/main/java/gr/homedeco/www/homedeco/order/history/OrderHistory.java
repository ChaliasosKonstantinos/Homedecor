package gr.homedeco.www.homedeco.order.history;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.server.callbacks.GetOrderHistoryCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;

public class OrderHistory extends AppCompatActivity {

    private List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getOrders();
    }

//------------------------------------- LISTENERS -------------------------------------------------//

    // Get user's order
    private void getOrders() {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.getOrderHistory(new GetOrderHistoryCallback() {
            @Override
            public void done(List<Order> returnedList) {
                orders = returnedList;
                Collections.reverse(orders);
                populateView(orders);
            }
        });
    }

    // Populates the view with given user's order
    private void populateView(List<Order> orders) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvOrders);
        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderHistory.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
