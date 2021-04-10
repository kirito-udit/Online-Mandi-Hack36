package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FetchLocationFromCoordinates {
    private String lat;
    private String lon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    //public static void main(String args[]) throws IOException {
    public String getCityAndZIP() throws IOException {

        String[] arguments = new String[] { "python", "./src/sample/ReverseGeocoding.py", lat, lon };
        Process process = Runtime.getRuntime().exec(arguments);
        try {
            if (process.waitFor() == 1) {
                throw new Exception("Python file has errors");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
            BufferedReader reader = new BufferedReader(new FileReader("./src/sample/Resources/location.txt"));
            String line;
            try{
               while((line = reader.readLine()) != null){
                    return line;
               }
            }catch(IOException e){
                  System.out.println("Exception in reading output"+ e.toString());
            }
    return null;
    }
}
