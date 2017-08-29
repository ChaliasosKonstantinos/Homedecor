package gr.homedeco.www.homedeco.server.callbacks;

import java.util.List;

import gr.homedeco.www.homedeco.product.Product;

public interface GetProductCallback {
    void done(List<Product> returnedList);
}
