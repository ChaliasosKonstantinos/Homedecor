package gr.homedeco.www.homedeco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONparser {

    public JSONparser() {
    }

//------------------------------------------------------------------------------------------------//
//                                    PRODUCTS
//------------------------------------------------------------------------------------------------//

    /**
     * Returns a product list of all products
     *
     * @param result all products server's response as a string
     * @return all products as an List of Product objects
     */
    public List<Product> toProduct(String result) throws JSONException {

        List<Product> products = new ArrayList<>();
        Product returnedProduct;
        JSONArray jArray = new JSONArray(result);
        int stock = 0;

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);

            if (jObject.length() != 0) {
                int productID = jObject.getInt("ProductID");
                String SKU = jObject.getString("SKU");
                String name = jObject.getString("Name");
                double price = jObject.getDouble("Price");
                double discountPrice = jObject.getDouble("DiscountPrice");
                int weight = jObject.getInt("Weight");
                String desc = jObject.getString("Description");
                String shortDesc = jObject.getString("ShortDescription");
                String image = jObject.getString("Image");
                if (!jObject.isNull("Stock")) {
                    stock = jObject.getInt("Stock");
                }
                String mainCategory = jObject.getString("MainCategory");
                String subCategory = jObject.getString("SubCategory");
                int categoryID = jObject.getInt("CategoryID");

                returnedProduct = new Product();
                returnedProduct.setProductID(productID);
                returnedProduct.setSKU(SKU);
                returnedProduct.setName(name);
                returnedProduct.setPrice(price);
                returnedProduct.setDiscountPrice(discountPrice);
                returnedProduct.setWeight(weight);
                returnedProduct.setDescription(desc);
                returnedProduct.setShortDescription(shortDesc);
                returnedProduct.setImage(image);
                returnedProduct.setStock(stock);
                returnedProduct.setMainCategory(mainCategory);
                returnedProduct.setSubCategory(subCategory);
                returnedProduct.setCategoryID(categoryID);

                products.add(returnedProduct);
            }
        }

        return products;
    }

//------------------------------------------------------------------------------------------------//
//                                    LOGIN
//------------------------------------------------------------------------------------------------//

    /**
     * Returns the server response after user login request
     *
     * @param result server's response as a string
     * @return server's response as a ServerResponse object
     */
    public String loginResponse(String result) throws JSONException {

        String authToken = null;
        JSONObject jObject = new JSONObject(result);
        if (jObject.has("android-token")) {
            authToken = jObject.getString("android-token");
        }
        return authToken;
    }

    /**
     * Returns a JSONObject ready to be send for user login
     *
     * @param user a User object with login details
     * @return login details as a json object
     */
    public JSONObject toLogin(User user) throws JSONException {
        JSONObject json = new JSONObject();

        json.put("username", user.getUsername());
        json.put("password", user.getPassword());

        return json;
    }

//------------------------------------------------------------------------------------------------//
//                                    REGISTER
//------------------------------------------------------------------------------------------------//

    /**
     * Returns the server response after user registration request
     *
     * @param result server's response as a string
     * @return server's response as a ServerResponse object
     */
    public ServerResponse registerResponse(String result) throws JSONException {
        ServerResponse response = new ServerResponse();
        JSONObject jObject = new JSONObject(result);

        if (jObject.has("Message")) {
            response.setMessage(jObject.getString("Message"));
        }

        return response;
    }

    /**
     * Returns a JSONObject ready to be send for user registration
     *
     * @param user a User object with registration details
     * @return user details as a json object
     */
    public JSONObject toRegister(User user) throws JSONException {
        JSONObject json = new JSONObject();
        JSONObject User = new JSONObject();
        JSONObject Userdetail = new JSONObject();

        User.put("Username", user.getUsername());
        User.put("Password", user.getPassword());
        User.put("Email", user.getEmail());

        Userdetail.put("FirstName", user.getFirstName());
        Userdetail.put("LastName", user.getLastName());
        Userdetail.put("Birthday", user.getBirthday());
        Userdetail.put("Address", user.getAddress());
        Userdetail.put("PostalCode", user.getPostalCode());
        Userdetail.put("City", user.getCity());
        Userdetail.put("State", user.getState());
        Userdetail.put("Country", user.getCountry());
        Userdetail.put("Phone", user.getPhone());
        Userdetail.put("MobilePhone", user.getMobilePhone());

        json.put("User", User);
        json.put("Userdetail", Userdetail);

        System.out.println(json);
        return json;
    }

//------------------------------------------------------------------------------------------------//
//                                    USER
//------------------------------------------------------------------------------------------------//

    /**
     * Returns a User object from server's JSON response
     *
     * @param result server's JSON result as a string
     * @return user details as User object
     */
    public User toUser(String result) throws JSONException {
        User user = new User();
        JSONObject json = new JSONObject(result);

        user.setUsername(json.getString("Username"));
        user.setEmail(json.getString("Email"));
        user.setFirstName(json.getString("FirstName"));
        user.setLastName(json.getString("LastName"));
        user.setBirthday(json.getString("Birthday"));
        user.setAddress(json.getString("Address"));
        user.setPostalCode(json.getString("PostalCode"));
        user.setCity(json.getString("City"));
        user.setState(json.getString("State"));
        user.setCountry(json.getString("Country"));
        user.setPhone(json.getString("Phone"));
        user.setMobilePhone(json.getString("MobilePhone"));

        return user;
    }

//------------------------------------------------------------------------------------------------//
//                                    CONVERSATION
//------------------------------------------------------------------------------------------------//

    /**
     * Returns a private messages list of user's conversation
     *
     * @param result the whole conversation server's response as a string
     * @return the whole conversation as a List of PrivateMessage objects
     */
    public List<PrivateMessage> toConversation(String result) throws JSONException {

        List<PrivateMessage> conv = new ArrayList<>();
        PrivateMessage returnedMessage;
        JSONArray jArray = new JSONArray(result);

        int length = jArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jObject = jArray.getJSONObject(i);

            if (jObject.length() != 0) {
                int id = jObject.getInt("id");
                String message = jObject.getString("Message");
                String image = jObject.getString("Image");
                int isUser = jObject.getInt("IsUser");
                String date = jObject.getString("Date");

                returnedMessage = new PrivateMessage();
                returnedMessage.setMessageID(id);
                returnedMessage.setMessage(message);
                returnedMessage.setImage(image);
                returnedMessage.setIsUser(isUser);
                returnedMessage.setDate(date);

                conv.add(returnedMessage);
            }
        }

        return conv;
    }

    /**
     * Returns server's response of a private message
     *
     * @param result server's response of a private message
     * @return server's response of a private message
     */
    public String sendMessageResponse(String result) throws JSONException {

        String response = "";
        JSONObject jObject = new JSONObject(result);
        response = jObject.getString("Message");
        return response;
    }

    /**
     * Returns a private message as a JSONObject
     *
     * @param message a PrivateMessage object with either a Message or an Image
     * @return private message as a JSONObject
     */
    public JSONObject toPrivateMessage(PrivateMessage message) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("Message", message.getMessage());
        json.put("Image", "");
        return json;
    }

//------------------------------------------------------------------------------------------------//
//                                    LOGIN
//------------------------------------------------------------------------------------------------//

    /**
     * Returns an order
     *
     * @param order Order Object containing order infos
     * @param productIDs List with Integers containing order's product IDs
     * @return an order as a JSONObject
     */
    public JSONObject toOrder(Order order, List<Integer> productIDs) throws JSONException {

        JSONObject jObject = new JSONObject();
        JSONArray products = new JSONArray();

        jObject.put("ShipAddress",order.getShipAddress());
        jObject.put("BilAddress",order.getBillAddress());
        jObject.put("PostalCode",order.getPostalCode());
        jObject.put("City",order.getCity());
        jObject.put("Country",order.getCountry());
        jObject.put("State",order.getState());
        jObject.put("MobilePhone",order.getMobilePhone());
        jObject.put("Phone",order.getPhone());
        jObject.put("ShippingMethod",order.getShippingMethod());
        jObject.put("Email",order.getEmail());
        jObject.put("FullName",order.getFullName());
        jObject.put("Price",order.getPrice());
        for(int id: productIDs) {
            JSONObject product = new JSONObject();
            product.put("ProductID",id);
            product.put("Quantity",1);
            products.put(product);
        }
        jObject.put("Products",products);

        return jObject;
    }

    /**
     * Returns server's response of an order creation
     *
     * @return a order creation response as a ServerResponse
     */
    public ServerResponse orderResponse(String result) throws JSONException {

        ServerResponse response = new ServerResponse();
        JSONObject jObject = new JSONObject(result);

        if (jObject.has("Message")) {
            response.setMessage(jObject.getString("Message"));
        }

        return response;
    }

}
