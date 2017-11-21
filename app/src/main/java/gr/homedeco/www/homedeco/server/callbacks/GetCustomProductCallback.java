package gr.homedeco.www.homedeco.server.callbacks;

import java.util.List;

import gr.homedeco.www.homedeco.product.CustomProduct;

public interface GetCustomProductCallback {
    void done(List<CustomProduct> returnedList);
}
