package sample;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

public class GeoipifyAPIQuery {
    public static ArrayList<String> accessLocation() throws IOException {
        String IP = "8.8.8.8";
        String API_KEY = "at_OlwZQUtRfGC83GyQBhrrA863ixHMS";
        String API_URL = "https://geo.ipify.org/api/v1?";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
            IP = sc.readLine().trim();
        } catch (Exception e) {
            IP = "Cannot Execute Properly";
        } String url = API_URL + "&apiKey=" + API_KEY + "&ipAddress=" + IP;
        String rep = "";
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(url).openStream())) {
            rep = s.useDelimiter("\\A").next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String arr[] = new String[18];
        int i = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(rep, ",");
        while (stringTokenizer.hasMoreTokens()) {
            arr[i++] = stringTokenizer.nextToken();
        }
        String lat = arr[4].substring(6);
        String longt = arr[5].substring(6);
        FetchLocationFromCoordinates fetchLocationFromCoordinates = new FetchLocationFromCoordinates();
        fetchLocationFromCoordinates.setLat(lat);
        fetchLocationFromCoordinates.setLon(longt);
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add(lat);
        arrayList.add(longt);
        arrayList.add(fetchLocationFromCoordinates.getCityAndZIP());
        return arrayList;
    }
}