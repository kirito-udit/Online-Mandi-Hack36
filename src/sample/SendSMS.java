package sample;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import java.net.*;
import java.util.*;
//import java.util.Random;
public class SendSMS {
    public static String OTP()
    {
        Random random = new Random();
        return ""+random.ints(1000, 9999).findFirst().getAsInt();
    }
    public static void sendSms(String num,String number) {
//		System.out.println(message);
//		System.out.println(number);
        String message = "WELCOME TO ONLINE MANDI: " +
                "your One Time Password for verification is: "+num;
        System.out.println(message);
        try {

            String apiKey="p5jxmVU4XP3zagYOFRhqACLTEkMtwrcWG9IivKNJln81bsQDeyiSufGBcJzX65Ml2FrE3hHxmKwWkCnq";
            String sendId="TXTIND";
            //important step...
            message=URLEncoder.encode(message, "UTF-8");
            String language="english";

            String route="p";


            String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            //sending get request using java..

            URL url=new URL(myUrl);

            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();


            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            System.out.println("Wait..............");

            int code=con.getResponseCode();

            System.out.println("Response code : "+code);

            StringBuffer response=new StringBuffer();

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));

            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }

            System.out.println(response);


        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}

