import javax.swing.*;
import java.sql.*;

public class UserLogin {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(
                "com.mysql.cj.jdbc.Driver");
        Connection con =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/jdbc",
                "root",
                "1234");
        // SQL INJECTION -> admin' OR '1'='1
        String user= JOptionPane.showInputDialog("enter username");
        // password you can type anything
        String password=JOptionPane.showInputDialog("enter password");
       /* Statement statement = con.createStatement();
        ResultSet set =
                statement.
                        executeQuery("SELECT * FROM user " + "WHERE username='" + user + "' AND password='" + password + "'");*/
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM user WHERE username=? AND password=?"
        );
        stm.setObject(1,user);
        stm.setObject(2,password);
        ResultSet set = stm.executeQuery();
        if (set.next()){
            System.out.println("Correct");
        }else{
            System.out.println("Try Again");
        }
        // SELECT, (executeQuery)
        // CREATE, UPDATE, DELETE, (executeUpdate)

    }
}
