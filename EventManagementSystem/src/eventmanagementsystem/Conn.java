package eventmanagementsystem;

import java.sql.*;
public class Conn {
    Connection c;
    Statement s;
    Conn(){
        try{
        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "05aanchal18");
        s = c.createStatement();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
