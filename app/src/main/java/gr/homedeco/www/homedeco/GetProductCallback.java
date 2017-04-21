package gr.homedeco.www.homedeco;

import java.util.List;

public interface GetProductCallback {
    void done(List<Product> returnedList);
}
