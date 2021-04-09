import sample.SignUpController;

import java.sql.*;
public class jdbc01 {
    String url = ""; //INSERT THE URL
    String username = "root";
    String pass = "";
    void insert_name(String value) throws  Exception
    {
        String query1 = "insert into 'table'"+value;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,username,pass);
        Statement st = con.createStatement();
        st.execute(query1);
    }

    void insert_pass(String value) throws  Exception
    {
        String query1 = "insert into 'table'"+value;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,username,pass);
        Statement st = con.createStatement();
        st.execute(query1);
    }
    void insert_email(String value) throws  Exception
    {
        String query1 = "insert into 'table'"+value;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,username,pass);
        Statement st = con.createStatement();
        st.execute(query1);
    }
    public static void main(String []args) throws Exception
    {
        SignUpController obj = new SignUpController();
        String usern = obj.getname();
        String pass7 = obj.getpass();
        String mail7 = obj.getmail();
        jdbc01 obj2 = new jdbc01();
        try {
            obj2.insert_name(usern);
            obj2.insert_pass(pass7);
            obj2.insert_email(mail7);
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }
}
