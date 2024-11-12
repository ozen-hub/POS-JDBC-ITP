import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
            String sql="INSERT INTO customer" +
                    " VALUES('"+id+"','"+name+"','"
                    +address+"','"+salary+"')";
            // create the statement
            Statement statement =
                    con.createStatement();
            int isSaved = statement.executeUpdate(sql);
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
}
