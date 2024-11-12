import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public void saveOnAction(ActionEvent actionEvent) {
        int id=Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary =
                Double.parseDouble(txtSalary.getText());
        try{
            // load the driver class
            Class.forName(
                    "com.mysql.cj.jdbc.Driver");
            // create the Connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc",
                    "root","1234"
            );
            // create the query
            String sql="INSERT INTO customer VALUES(?,?,?,?)";
            // create the statement
            PreparedStatement statement =
                    con.prepareStatement(sql);
            int isSaved = statement.executeUpdate();
            if(isSaved>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Saved").show();
            }else{
                new Alert(Alert.AlertType.WARNING,
                        "Try Again").show();
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }

    public void getCustomerOnAction(ActionEvent actionEvent) {
        int customerId=
                Integer.parseInt(txtId.getText());
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","1234");
            String sql ="SELECT * FROM customer WHERE id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet set = stm.executeQuery();
            if(set.next()){
                System.out.println(set);
            }else{
                System.out.println("not found");
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
