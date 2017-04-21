package gr.homedeco.www.homedeco;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {

    private final String SERVER_ADDRESS = "http://83.212.107.169/HomeDecoWS/public";
    private HttpURLConnection urlConnection;

    public ServerConnection() {
    }

    //Establishes a HttpURLConnection with GET as a RequestMethod
    public HttpURLConnection openGetConnection(String uri) {

        try {
            URL url = new URL(SERVER_ADDRESS + uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    //Establishes a HttpURLConnection with POST as a RequestMethod
    public HttpURLConnection openPostConnection(String uri) {

        try {
            URL url = new URL(SERVER_ADDRESS + uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            // urlConnection.setChunkedStreamingMode(0);

            urlConnection.setDoInput(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }
}
