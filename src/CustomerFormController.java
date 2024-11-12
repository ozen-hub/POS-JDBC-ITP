import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadAllData() {
        ObservableList<CustomerTM> tmList =
                FXCollections.observableArrayList();
        try{
            String sql="SELECT * FROM customer";
            PreparedStatement statement =
                    DbConnection.getInstance()
                            .getConnection().prepareStatement(sql);
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
        Customer c= new Customer(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        try{
            boolean isSaved = new DatabaseCode().save(c);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Saved").show();
                clear();
                loadAllData();
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
            Customer selectedCustomer =
                    new DatabaseCode().find(customerId);
            if(null!=selectedCustomer){
                txtName.setText(selectedCustomer.getName());
                txtAddress.setText(selectedCustomer.getAddress());
                txtSalary.setText(String.valueOf(selectedCustomer.getSalary()));
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
           String sql ="UPDATE customer" +
                    " SET name=?, address=?, salary=? WHERE id=?";
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().prepareStatement(sql);
            stm.setString(1,name);
            stm.setString(2,address);
            stm.setDouble(3,salary);
            stm.setInt(4,id);
            int isSaved = stm.executeUpdate();
            if(isSaved>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Updated").show();
                clear();
                loadAllData();
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

            String sql ="DELETE FROM customer WHERE id=?";
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().prepareStatement(sql);
            stm.setInt(1,customerId);
            int isDeleted = stm.executeUpdate();
            if(isDeleted>0){
                new Alert(Alert.AlertType.INFORMATION,
                        "Customer Deleted").show();
                clear();
                loadAllData();
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
