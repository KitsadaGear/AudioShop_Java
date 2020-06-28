package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDatabase {
    public static Connection ConnectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sa";
            Connection con= DriverManager.getConnection(url,"root","");
            return con;
        }catch (Exception e){
            System.err.println("StaffLoginDB : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
