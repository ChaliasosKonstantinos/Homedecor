package gr.homedeco.www.homedeco;

public class Order {

    private String shipAddress, shipAddress2, city, state, country,
            mobilePhone, phone, shippingMethod, orderDate, email, fullName;
    private int orderID, userID, postalCode, quantity;
    private float price;

    public Order(int orderID, int userID, String shipAddress, String shipAddress2, int postalCode,
                 String city, String state, String country, String mobilePhone, String phone,
                 String shippingMethod, String orderDate, String email, String fullName, float price,
                 int quantity) {
        this.shipAddress = shipAddress;
        this.shipAddress2 = shipAddress2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.shippingMethod = shippingMethod;
        this.orderDate = orderDate;
        this.email = email;
        this.fullName = fullName;
        this.orderID = orderID;
        this.userID = userID;
        this.postalCode = postalCode;
        this.quantity = quantity;
        this.price = price;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipAddress2() {
        return shipAddress2;
    }

    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
