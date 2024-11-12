import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.*;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;

    public void initialize(){
        loadAllData();
    }

    private void loadAllData() {
        ObservableList<CustomerTM> tmList =
                FXCollections.observableArrayList();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root","1234");
            String sql="SELECT * FROM customer";
            PreparedStatement statement =
                    con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                tmList.add(
                new CustomerTM(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4))
                );
            }
            tblCustomers.setItems(tmList);
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).show();
        }
    }

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
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setDouble(4, salary);
            int isSaved = statement.executeUpdate();
            if(isSaved>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Saved").show();
                clear();
            }else{
                new Alert(Alert.AlertType.WARNING,
                        "Try Again").show();
            }
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).show();
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
            stm.setInt(1,customerId);
            ResultSet set = stm.executeQuery();
            if(set.next()){
              /*  System.out.println(set.getInt("id"));
                System.out.println(set.getString("address"));
                System.out.println(set.getInt(1));
                System.out.println(set.getString(2));*/
                txtName.setText(set.getString(2));
                txtAddress.setText(set.getString(3));
                txtSalary.setText(String.valueOf(set.getDouble(4)));
            }else{
               new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();

            }
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        int id=Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary =
                Double.parseDouble(txtSalary.getText());
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","1234");
            String sql ="UPDATE customer" +
                    " SET name=?, address=?, salary=? WHERE id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1,name);
            stm.setString(2,address);
            stm.setDouble(3,salary);
            stm.setInt(4,id);
            int isSaved = stm.executeUpdate();
            if(isSaved>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Updated").show();
                clear();
            }else{
                new Alert(Alert.AlertType.INFORMATION,
                        "Try again").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).show();
        }
    }

    private void clear(){
        txtSalary.clear();
        txtAddress.clear();
        txtName.clear();
        txtId.clear();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        int customerId=
                Integer.parseInt(txtId.getText());
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","1234");
            String sql ="DELETE FROM customer WHERE id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1,customerId);
            int isDeleted = stm.executeUpdate();
            if(isDeleted>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Deleted").show();
                clear();
            }else{
                new Alert(Alert.AlertType.INFORMATION,
                        "Try again").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).show();
        }
    }
}
