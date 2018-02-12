package gr.homedeco.www.homedeco.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import gr.homedeco.www.homedeco.product.CustomProduct;

public class OrderController {

    final private Order order = new Order();

    /**
     * Creates an order controller with the given type of order
     *
     * @param type SET GENERIC to create a generic order
     *             SET CUSTOM to create a custom order
     */
    public OrderController(String type) {
        this.order.setType(type);
    }

    /**
     * Returns the order state
     *
     * @return an Order object with order's current state
     */
    public Order getOrder() {
        return this.order;
    }

    /**
     * Sets order's contact information
     *
     * @param fullname customer's fullname
     * @param email customer's email
     * @param phone customer's phone
     * @param mobilePhone customer's mobile phone
     */
    public void setOrderInfo(String fullname, String email, String phone, String mobilePhone) {
        this.order.setFullName(fullname);
        this.order.setEmail(email);
        this.order.setPhone(phone);
        this.order.setMobilePhone(mobilePhone);
    }

    /**
     * Sets order's shipping information
     *
     * @param country customer's country
     * @param state customer's state
     * @param city customer's city
     * @param shipAddress customer's ship address
     * @param billAddress customer's bill address
     * @param postalCode customer's postal code
     * @param shippingMethod customer's shipping method
     */
    public void setOrderShipping(String country, String state, String city, String shipAddress,
                                 String billAddress, String postalCode, String shippingMethod) {
        this.order.setCountry(country);
        this.order.setState(state);
        this.order.setCity(city);
        this.order.setShipAddress(shipAddress);
        this.order.setBillAddress(billAddress);
        this.order.setPostalCode(postalCode);
        this.order.setShippingMethod(shippingMethod);
    }

    /**
     * Sets order's payment information
     *
     * @param method payment method
     * @param creditCard credit cards info in a HashMap
     *                   SET NULL if the payment method is not credit card
     */
    public void setPayment(String method, HashMap<String, String> creditCard) {

        if (Objects.equals(method, "Πιστωτική/Χρεωστική")) {
            this.order.setPaymentMethod(method);
            this.order.setCreditCard(creditCard);
        } else {
            this.order.setPaymentMethod(method);
        }
    }

    /**
     * Sets order's price
     *
     * @param price order's price (double)
     *              SET 0 if order type is "custom"
     * @param cp a CustomProduct object containing chose parts
     *           SET NULL if order type is "generic"
     */
    public void setPrice(double price, CustomProduct cp) {

        if (Objects.equals(getType(), "generic")) {
            this.order.setPrice(price);
        } else {
            price = 0;
            for (CustomProduct.CPart pr : cp.getCParts()) {
                price += pr.getPrice();
            }
            this.order.setPrice(price);
        }
    }

    /**
     * Sets order's products
     *
     * @param cart user's cart
     *             SET NULL if order type is "custom"
     * @param cp a CustomProduct object containing chose parts
     *           SET NULL if order type is "generic"
     */
    public void setProducts(String cart, CustomProduct cp) {

        List<Integer> products = new ArrayList<>();
        if (Objects.equals(getType(), "generic")) {
            if (!cart.isEmpty()) {
                String[] parts = cart.split(",");
                for (String part : parts) {
                    products.add(Integer.parseInt(part));
                }
            }
        } else {
            for (CustomProduct.CPart pr : cp.getCParts()) {
                products.add(pr.getId());
            }
        }
        this.order.setProductsID(products);
    }

    /**
     * Sets order's products
     *
     * @param type SET "generic" if order's type is generic
     *             SET "custom" if order's type is custom products
     */
    public void setType(String type) {
        this.order.setType(type);
    }

    /**
     * Returns order's type
     *
     * @return order's type
     */
    public String getType() {
        return this.order.getType();
    }

    /**
     * Returns order's price
     *
     * @return order's price (double)
     */
    public double getPrice() {
        return this.order.getPrice();
    }

    /**
     * Returns order's recipient full name
     *
     * @return order's recipient full name
     */
    public String getFullName() {
        return this.order.getFullName();
    }
}
