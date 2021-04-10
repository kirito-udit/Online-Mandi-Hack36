# Online-Mandi-Hack36
Online Mandi: A desktop application which helps trade of crops between two parties to ease the trade for farmers and contribute to E-Administration 


![WhatsApp Image 2021-04-10 at 23 44 18](https://user-images.githubusercontent.com/66271037/114282223-39ab5600-9a60-11eb-8f76-318a44844a42.jpeg)

# Team: Pings With Things:<br>><br>
Teammates:<br>
<a href="https://github.com/kirito-udit">UDIT KOTECHA</a><br>
<a href="https://github.com/kartikeyasri23">KARTIKEY SRIVASTAVA</a><br>
<a href="https://github.com/mrdinosaurabh">SAURABH SINGH</a><br>
<a href="https://github.com/shubhamdixit760">SHUBHAM DIXIT</a><br><br>
Public Links<br>
Video Presentation: https://youtu.be/EDNZ7KvfUxo<br>


After the introduction of new farm reforms in September 2020, we introduce this app as a suggestion to the
Government which helps in E administration of the crop market.It also helps the farmers use the new reforms in
the most profitable way, as they can have a platform to braodcast their products all over the country, can involve in 
contract farming etc.



Installation Details :

1. Make sure you have latest version of JDK (JDK 11 or above).

2. Download the zip of the project from the repository and 
     and unzip in the location wherever you want to. 

3. Open the folder in your IDE, and add the following jar files
    in your referenced libraries or modules (depends upon your IDE
    for example, in VS code you have references libraries and in intellij, you
   add jars in your modules as well as global libraries)
     => 8 jars in the lib folder of javafx-sdk-15.0.1 inside the dependencies folder of the project
     => 1 jar for sqlite JDBC connector in the dependencies folder

4. The project also has a location access feature for which you need to have 
      some python dependencies installed in your system. (Do this only if you want 
     to have location access feature) .
     => Make sure if you have python installed in your system. 
     => Run the command "pip install geopy" in your terminal or cmd.
     => This will install geopy reverse geocoding libraries in your system which 
            are required to have location access feature in your project.

5. First run "server.java" and then "main.java" 




Basic features : 

1. Login and signUp for every user on the app along with hashing of password.

2. Update profile pic, password and address details.

3. Forgot password feature in case of user forgets his password.
     Note : For this, phoneNumber will be used to send an OTP to change the password.

4. User can add an offer (cropName, price/100kg, start and end dates along with the description) 
    in the list of offers which will be broadcasted to other users.

5. User can modify or update the details of an offer by going through all his offers
     and update the price, start and end dates as well as the description of his offer.
    Note : The price of an offer can't be updated after the start date has passed.

6. A user can see the list of  all the available offers along with their details and
    place the order by entering the quantity. 

7. There will also be a contract provision for those users who want to make themselves
     a part of contract or for those who want to assign contracts to others
     => A user can add himself to the contract list along with the EMP/100kg (Expected minimum price)
     => A user can go through the list of all the users available for contract and approach
           any of them for a contract.
Note : Since contract is a legal issue so it is recommended to have it signed in presence of  a legal advisor
on both ends. 

8. Multithreaded end to end chat system implemented. 

Advanced Feature : 

1. Self build geo-positioning system (GPS) for location access.

2. Map based positioning system.

3. Self build java web Browser. 

3. OTP verification is done to authenticate phone number.

4. Along with OTP verification, Java SMTP Email verification is also there to ensure further
    authentication.

5. Search users on his chat page by  phone number or name.

6. Client-server socket interface. 

7. AutoComplete feature using Trie Data structure (Dynamic searching) 

8. Offers can be sorted on the basis of
a)end-date
b)start-date
c)price
d)distance(closest first)

9. Filter can be applied on offers on the basis of price range(a minimum and a maximum price).

10. User can see the distance between him/her and the corresponding seller along with the path and time to reach the seller.

11. Transaction Rollback has been applied which acts as a remedy if a transaction is interrupted in between and it in turn make no changes to the database.

12. Used Task from JavaFX concurrent package to make the UI function properly while doing a heavy process by assigning the process to an alternate thread.


Features yet to be implemented : 

1. Payment Gateway is to implemented inorder to facilate the farmers to online transactions.
2. Voice and Video Calling with EnableX
3. AIML Chatbot


Working Screenshots<br>
![](https://i.ibb.co/dLHVT55/ind2ex.jpg)
![](https://i.ibb.co/T2XPw4c/index4.jpg)
![](https://i.ibb.co/cNz4syT/index3.jpg)
![](https://i.ibb.co/H2G92Pz/index5.jpg)
![](https://i.ibb.co/Jz8ZsBY/index.jpg)

