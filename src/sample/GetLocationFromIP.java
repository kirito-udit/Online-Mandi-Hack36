package sample;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class GetLocationFromIP extends Thread{
    String key = "c7aa648f81dc784e7729e2babefc2c9458f41104910ac721f5d63a911a436222";
    public LocationUseBean get_ip_Details(String ip) {

        ip = ip.trim();
        LocationUseBean obj_Location_Use_Bean = new LocationUseBean();
        System.out.println("The ip adress is before " + ip + "  split");
        try {
            if (ip.contains(",")) {
                String temp_ip[] = ip.split(",");
                ip = temp_ip[1].trim();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("The ip adress is after " + ip + " split");
        URL url;
        try {
            url = new URL("https://api.ipinfodb.com/v3/ip-city/?key=" + key + "&ip=" + ip);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            String temporaray = "";
            String temp_array[] = null;
            while (null != (strTemp = br.readLine())) {
                temporaray = strTemp;
            }
            temp_array = temporaray.split(";");
            System.out.println("Str length is " + temp_array.length);
            int length = temp_array.length;

            if (length == 11) {
                obj_Location_Use_Bean.setIp_address(ip);
                if (temp_array[3] != null) {
                    obj_Location_Use_Bean.setCountry_code(temp_array[3]);
                }
                if (temp_array[4] != null) {
                    obj_Location_Use_Bean.setCountry(temp_array[4]);
                }
                if (temp_array[5] != null) {
                    obj_Location_Use_Bean.setState(temp_array[5]);
                }
                if (temp_array[6] != null) {
                    obj_Location_Use_Bean.setCity(temp_array[6]);
                }
                if (temp_array[7] != null) {
                    obj_Location_Use_Bean.setZip(temp_array[7]);
                }
                if (temp_array[8] != null) {
                    obj_Location_Use_Bean.setLat(temp_array[8]);
                }
                if (temp_array[9] != null) {
                    obj_Location_Use_Bean.setLon(temp_array[9]);
                }
                if (temp_array[10] != null) {
                    obj_Location_Use_Bean.setUtc_offset(temp_array[10]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj_Location_Use_Bean;
    }
    public static Address getAddress() throws IOException {
        GetLocationFromIP obj_Get_Location_From_IP = new GetLocationFromIP();
        String iap = "";
        InetAddress localhost = InetAddress.getLocalHost();
//        System.out.println("System IP Address : " +
//                (localhost.getHostAddress()).trim());

        // Find public IP address
        String systemipaddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            iap = sc.readLine().trim();
        }
        catch (Exception e)
        {
            systemipaddress = "Cannot Execute Properly";
        }
        // System.out.println("Public IP Address: " + iap +"\n");
        String city = null, zip = null;
        double lat = 0.0,lon = 0.0;

        try{
            //Location_Use_Bean obj_Location_Use_Bean = obj_Get_Location_From_IP.get_ip_Details("49.213.63.255");
            Thread.sleep(2000);
            LocationUseBean obj_Location_Use_Bean = obj_Get_Location_From_IP.get_ip_Details(iap);
            Thread.sleep(2000);
            System.out.println("IP Address--" + obj_Location_Use_Bean.getIp_address());
            Thread.sleep(2000);
            System.out.println("Country Code-- " + obj_Location_Use_Bean.getIp_address());
            Thread.sleep(2000);
            System.out.println("Country--" + obj_Location_Use_Bean.getCountry());
            Thread.sleep(2000);
            System.out.println("State--" + obj_Location_Use_Bean.getState());
            Thread.sleep(2000);
            System.out.println("City--" + obj_Location_Use_Bean.getCity());
            city = obj_Location_Use_Bean.getCity();
            Thread.sleep(2000);
            System.out.println("ZIP--" + obj_Location_Use_Bean.getZip());
            zip = obj_Location_Use_Bean.getZip();
            Thread.sleep(2000);
            System.out.println("Lat--" + obj_Location_Use_Bean.getLat());
            lat = Double.parseDouble(obj_Location_Use_Bean.getLat());
            Thread.sleep(2000);
            System.out.println("Lon--" + obj_Location_Use_Bean.getLon());
            lon = Double.parseDouble(obj_Location_Use_Bean.getLon());
            Thread.sleep(2000);
            System.out.println("Offset--" + obj_Location_Use_Bean.getUtc_offset());
            Thread.sleep(2000);
        }
        catch (Exception e) {

        }
        Address address = null;
        return address = new Address(city,zip,lat,lon);
    }
}


