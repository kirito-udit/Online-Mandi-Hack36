package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GeoipifyAPIQuery {
    public static void main(String[] args) throws IOException {
        String IP;
        String API_KEY = "at_OlwZQUtRfGC83GyQBhrrA863ixHMS";
        String API_URL = "https://geo.ipify.org/api/v1?";
        URL url_name = new URL("http://bot.whatismyipaddress.com");

        BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
        IP = sc.readLine().trim();
        String url = API_URL + "&apiKey=" + API_KEY + "&ipAddress=" + IP;
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(url).openStream())) {
            System.out.println(s.useDelimiter("\\A").next());
        } catch (Exception ex) {
            ex.printStackTrace();
        } //https://geo.ipify.org/api/v1?apiKey=at_OlwZQUtRfGC83GyQBhrrA863ixHMS&ipAddress=8.8.8.8

    }
}
