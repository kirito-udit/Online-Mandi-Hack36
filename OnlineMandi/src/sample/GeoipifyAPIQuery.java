package sample;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
public class GeoipifyAPIQuery {
    public static void main(String[]args){
        String IP = "8.8.8.8";
        String API_KEY = "at_OlwZQUtRfGC83GyQBhrrA863ixHMS";
        String API_URL = "https://geo.ipify.org/api/v1?";
        String url = API_URL + "&apiKey=" + API_KEY +
                "&ipAddress=" + IP;
        try (java.util.Scanner s =
                     new java.util.Scanner(new java.net.URL(url).openStream())) {
            System.out.println(s.useDelimiter("\\A").next());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}