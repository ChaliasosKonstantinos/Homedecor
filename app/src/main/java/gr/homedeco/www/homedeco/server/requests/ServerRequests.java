package gr.homedeco.www.homedeco.server.requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gr.homedeco.www.homedeco.contact.chat.PrivateMessage;
import gr.homedeco.www.homedeco.helpers.JSONparser;
import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;
import gr.homedeco.www.homedeco.order.Order;
import gr.homedeco.www.homedeco.product.CustomProduct;
import gr.homedeco.www.homedeco.product.Product;
import gr.homedeco.www.homedeco.server.callbacks.GetConversationCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetCustomProductCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetLoginCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetMessageCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetOrderCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetOrderHistoryCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetProductCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetRegisterCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetUserDetailsCallback;
import gr.homedeco.www.homedeco.server.connection.ServerConnection;
import gr.homedeco.www.homedeco.server.response.ServerResponse;
import gr.homedeco.www.homedeco.user.User;

public class ServerRequests {
    private ProgressDialog progressDialog;
    private BufferedReader reader = null;
    private LocalDatabase localDatabase;


    public ServerRequests(Context context) {
        localDatabase = new LocalDatabase(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }

//------------------------------------------------------------------------------------------------//
//                                    PRODUCTS
//------------------------------------------------------------------------------------------------//

    /**
     * Fetches product's data from the server in the background (Runs on UI thread)
     *
     * @param productID SET to the product ID of a specific product to be retrieved
     *                  SET 0 to retrieve all products data
     * @param productCallback the callback to fire when request is completed
     */
    public void fetchProductDataInBackground(int productID, GetProductCallback productCallback) {
        new FetchProductDataAsyncTask(productID, productCallback).execute();
    }

    /**
     * AsyncTask that fetches the product's data from the server
     */
    private class FetchProductDataAsyncTask extends AsyncTask<Void, Void, List<Product>> {
        int productID;
        GetProductCallback productCallback;

        private FetchProductDataAsyncTask(int productID, GetProductCallback productCallback) {
            this.productID = productID;
            this.productCallback = productCallback;
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            List<Product> products = new ArrayList<>();
            ServerConnection connection = new ServerConnection();
            HttpURLConnection urlConnection;

            if (productID != 0) {
                urlConnection = connection.openGetConnection("/product/" + productID);
            } else {
                urlConnection = connection.openGetConnection("/product");
            }


            try {
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONparser parser = new JSONparser();
                products = parser.toProduct(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            progressDialog.dismiss();
            productCallback.done(products);
            super.onPostExecute(products);
        }
    }

    /**
     * Fetches custom products data from the server in the background (Runs on UI thread)
     *
     * @param customProductCallback the callback to fire when request is completed
     */
    public void fetchCustomProductDataInBackground(GetCustomProductCallback customProductCallback) {
        new FetchCustomProductDataAsyncTask(customProductCallback).execute();
    }

    /**
     * AsyncTask that fetches custom products data from the server
     */
    private class FetchCustomProductDataAsyncTask extends AsyncTask<Void, Void, List<CustomProduct>> {
        GetCustomProductCallback customProductCallback;

        private FetchCustomProductDataAsyncTask(GetCustomProductCallback customProductCallback) {
            this.customProductCallback = customProductCallback;
        }

        @Override
        protected List<CustomProduct> doInBackground(Void... params) {
            List<CustomProduct> customProducts = new ArrayList<>();
            ServerConnection connection = new ServerConnection();
            HttpURLConnection urlConnection;
            urlConnection = connection.openGetConnection("/custom_product");

            try {
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder buffer = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONparser parser = new JSONparser();
                customProducts = parser.toCustomProduct(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return customProducts;
        }

        @Override
        protected void onPostExecute(List<CustomProduct> customProducts) {
            progressDialog.dismiss();
            customProductCallback.done(customProducts);
            super.onPostExecute(customProducts);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                     LOGIN
//------------------------------------------------------------------------------------------------//

    /**
     * Try to login the user in the background (Runs on UI thread)
     *
     * @param user a User object containing the user's credentials to be logged in
     * @param loginCallback the callback to fire when request is completed
     */
    public void login(User user, GetLoginCallback loginCallback) {
        new loginAsyncTask(user, loginCallback).execute();
    }

    /**
     * AsyncTask that try to login the user in
     */
    private class loginAsyncTask extends AsyncTask<User, Void, String> {
        User user = new User();
        GetLoginCallback loginCallback;

        private loginAsyncTask(User user, GetLoginCallback loginCallback) {
            this.user = user;
            this.loginCallback = loginCallback;
        }

        @Override
        protected String doInBackground(User... params) {
            ServerConnection connection = new ServerConnection();
            String authToken = null;
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openPostConnection("/login");
            urlConnection.setRequestProperty("android", "true");

            try {

                urlConnection.connect();

                JSONObject json = parser.toLogin(user);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(json.toString());
                out.close();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                authToken = parser.loginResponse(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return authToken;
        }

        @Override
        protected void onPostExecute(String authToken) {
            loginCallback.done(authToken);
            super.onPostExecute(authToken);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                  USER DETAILS
//------------------------------------------------------------------------------------------------//

    /**
     * Fetches user's data from the server in the background (Runs on UI thread)
     *
     * @param userDetailsCallback the callback to fire when request is completed
     */
    public void getUserDetails(GetUserDetailsCallback userDetailsCallback) {
        new getUserDetailsAsyncTask(userDetailsCallback).execute();
    }

    /**
     * AsyncTask that fetches user's data from the server
     */
    private class getUserDetailsAsyncTask extends AsyncTask<Void, Void, User> {
        GetUserDetailsCallback userDetailsCallback;

        private getUserDetailsAsyncTask(GetUserDetailsCallback userDetailsCallback) {
            this.userDetailsCallback = userDetailsCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection();
            User returnedUser = new User();
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openGetConnection("/user/self");
            urlConnection.setRequestProperty("android", "true");
            urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

            try {

                urlConnection.connect();

                int status = urlConnection.getResponseCode();
                System.out.println(status);

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                returnedUser = parser.toUser(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userDetailsCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    /**
     * Update user's data on the server in the background (Runs on UI thread)
     *
     * @param user a User object containing the user's updated information
     * @param messageCallback the callback to fire when request is completed
     */
    public void updateUserDetails(User user, GetMessageCallback messageCallback) {
        new updateUserDetailsAsyncTask(user, messageCallback).execute();
    }

    /**
     * AsyncTask that update user's data on the server
     */
    private class updateUserDetailsAsyncTask extends AsyncTask<User, Void, String> {
        User user = new User();
        GetMessageCallback messageCallback;

        private updateUserDetailsAsyncTask(User user, GetMessageCallback messageCallback) {
            this.user = user;
            this.messageCallback = messageCallback;
        }

        @Override
        protected String doInBackground(User... params) {
            ServerConnection connection = new ServerConnection();
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openPutConnection("/user/self");
            urlConnection.setRequestProperty("android", "true");
            urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

            try {

                urlConnection.connect();

                JSONObject json = parser.toUpdateUser(user);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(json.toString());
                out.close();

//                InputStream is = urlConnection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(is));
//
//                StringBuilder strBuilder = new StringBuilder();
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    strBuilder.append(line);
//                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            messageCallback.done(result);
            super.onPostExecute(result);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    REGISTER
//------------------------------------------------------------------------------------------------//

    /**
     * Register a user in the background (Runs on UI thread)
     *
     * @param user a User object containing the user to be registered
     * @param registerCallback the callback to fire when request is completed
     */
    public void register(User user, GetRegisterCallback registerCallback) {
        new registerAsyncTask(user, registerCallback).execute();
    }

    /**
     * AsyncTask that register a user
     */
    private class registerAsyncTask extends AsyncTask<User, Void, ServerResponse> {
        User user = new User();
        GetRegisterCallback registerCallback;

        private registerAsyncTask(User user, GetRegisterCallback registerCallback) {
            this.user = user;
            this.registerCallback = registerCallback;
        }

        @Override
        protected ServerResponse doInBackground(User... params) {
            ServerConnection connection = new ServerConnection();
            ServerResponse response = new ServerResponse();
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openPostConnection("/user");

            try {

                urlConnection.connect();

                JSONObject json = parser.toRegister(user);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(json.toString());
                out.close();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                response = parser.registerResponse(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(ServerResponse response) {
            progressDialog.dismiss();
            registerCallback.done(response);
            super.onPostExecute(response);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    CHAT
//------------------------------------------------------------------------------------------------//

    /**
     * Fetches user's chat conversation from the server in the background (Runs on UI thread)
     *
     * @param conversationCallback the callback to fire when request is completed
     */
    public void getConversation(GetConversationCallback conversationCallback) {
        new getConversationAsyncTask(conversationCallback).execute();
    }

    /**
     * AsyncTask that fetches user's chat conversation from the server
     */
    private class getConversationAsyncTask extends AsyncTask<Void, Void, List<PrivateMessage>> {
        GetConversationCallback conversationCallback;

        private getConversationAsyncTask(GetConversationCallback conversationCallback) {
            this.conversationCallback = conversationCallback;
        }

        @Override
        protected List<PrivateMessage> doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection();
            List<PrivateMessage> returnedList = new ArrayList<>();
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openGetConnection("/message");
            urlConnection.setRequestProperty("android", "true");
            urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

            try {
                urlConnection.connect();

                int status = urlConnection.getResponseCode();
                System.out.println(status);

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                returnedList = parser.toConversation(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return returnedList;
        }

        @Override
        protected void onPostExecute(List<PrivateMessage> returnedList) {
            progressDialog.dismiss();
            conversationCallback.done(returnedList);
            super.onPostExecute(returnedList);
        }
    }

    /**
     * Sends user's chat message to the server in the background (Runs on UI thread)
     *
     * @param messageToSend a PrivateMessage object containing the message to be send
     * @param messageCallback the callback to fire when request is completed
     */
    public void sendMessage(PrivateMessage messageToSend, GetMessageCallback messageCallback) {
        new sendMessageAsyncTask(messageToSend, messageCallback).execute();
    }

    /**
     * AsyncTask that sends user's chat message to the server
     */
    private class sendMessageAsyncTask extends AsyncTask<PrivateMessage, Void, String> {
        PrivateMessage messageToSend = new PrivateMessage();
        GetMessageCallback messageCallback;

        private sendMessageAsyncTask(PrivateMessage messageToSend, GetMessageCallback messageCallback) {
            this.messageToSend = messageToSend;
            this.messageCallback = messageCallback;
        }

        @Override
        protected String doInBackground(PrivateMessage... params) {
            ServerConnection connection = new ServerConnection();
            HttpURLConnection urlConnection;
            String response = "";
            JSONparser parser = new JSONparser();

            urlConnection = connection.openPostConnection("/message");
            urlConnection.setRequestProperty("android", "true");
            urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

            try {
                urlConnection.connect();

                JSONObject json = parser.toPrivateMessage(messageToSend);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(json.toString());
                out.close();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                response = parser.sendMessageResponse(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            progressDialog.dismiss();
            messageCallback.done(response);
            super.onPostExecute(response);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    ORDERS
//------------------------------------------------------------------------------------------------//

    /**
     * Sends an order to the server in the background (Runs on UI thread)
     *
     * @param order an Order object containing the order to be send
     * @param orderCallback the callback to fire when request is completed
     */
    public void createOrder(Order order, GetOrderCallback orderCallback) {
        new createOrderAsyncTask(order, orderCallback).execute();
    }

    /**
     * AsyncTask that sends an order to the server
     */
    private class createOrderAsyncTask extends AsyncTask<Order, Void, ServerResponse> {
        Order order = new Order();
        GetOrderCallback orderCallback;

        private createOrderAsyncTask(Order order, GetOrderCallback orderCallback) {
            this.order = order;
            this.orderCallback = orderCallback;
        }

        @Override
        protected ServerResponse doInBackground(Order... params) {
            ServerConnection connection = new ServerConnection();
            HttpURLConnection urlConnection;
            ServerResponse response = new ServerResponse();
            JSONparser parser = new JSONparser();

            if (Objects.equals(order.getType(), "generic")) {
                urlConnection = connection.openPostConnection("/order");
            } else {
                urlConnection = connection.openPostConnection("/custom_order");
            }

            if (localDatabase.isLoggedIn()) {
                urlConnection.setRequestProperty("android", "true");
                urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());
            }

            try {
                urlConnection.connect();

                JSONObject json = parser.toOrder(order);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(json.toString());
                out.close();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                response = parser.orderResponse(result);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(ServerResponse response) {
            progressDialog.dismiss();
            orderCallback.done(response);
            super.onPostExecute(response);
        }
    }

    /**
     * Fetches user's order history from the server in the background (Runs on UI thread)
     *
     * @param orderHistoryCallback the callback to fire when request is completed
     */
    public void getOrderHistory(GetOrderHistoryCallback orderHistoryCallback) {
        new getOrderHistoryAsyncTask(orderHistoryCallback).execute();
    }

    /**
     * AsyncTask that fetches user's order history from the server
     */
    private class getOrderHistoryAsyncTask extends AsyncTask<Void, Void, List<Order>> {
        GetOrderHistoryCallback orderHistoryCallback;

        private getOrderHistoryAsyncTask(GetOrderHistoryCallback orderHistoryCallback) {
            this.orderHistoryCallback = orderHistoryCallback;
        }

        @Override
        protected List<Order> doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection();
            List<Order> returnedList = new ArrayList<>();
            HttpURLConnection urlConnection;
            JSONparser parser = new JSONparser();

            urlConnection = connection.openGetConnection("/order/self");
            urlConnection.setRequestProperty("android", "true");
            urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

            try {
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder strBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                String result = strBuilder.toString();
                returnedList = parser.toOrderHistory(result);

                urlConnection = connection.openGetConnection("/custom_order/self");
                urlConnection.setRequestProperty("android", "true");
                urlConnection.setRequestProperty("android-token", localDatabase.getAuthToken());

                urlConnection.connect();

                is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                strBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }

                result = strBuilder.toString();
                returnedList.addAll(parser.toOrderHistory(result));


            } catch (IOException | JSONException | ParseException e) {
                e.printStackTrace();
            }
            return returnedList;
        }

        @Override
        protected void onPostExecute(List<Order> returnedList) {
            progressDialog.dismiss();
            orderHistoryCallback.done(returnedList);
            super.onPostExecute(returnedList);
        }
    }
}

