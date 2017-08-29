package gr.homedeco.www.homedeco;

import java.util.List;

public interface GetOrderHistoryCallback {
    void done(List<Order> returnedList);
}
