package gr.homedeco.www.homedeco.server.callbacks;

import java.util.List;

import gr.homedeco.www.homedeco.order.Order;

public interface GetOrderHistoryCallback {
    void done(List<Order> returnedList);
}
