import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCode {
    public boolean save(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO customer VALUES(?,?,?,?)",
                c.getId(),c.getName(),c.getAddress(),c.getSalary());
    }

    public Customer find(int id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery("SELECT * FROM customer WHERE id=?",id);
        if (set.next()) {
            return new Customer(set.getInt(1),
                    set.getString(2),
                    set.getString(3),
                    set.getDouble(4)
            );
        } else {return null;}
    }

    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(
                "UPDATE customer SET name=?, address=?, salary=? WHERE id=?",
                c.getName(),c.getAddress(),c.getSalary(),c.getId()
        );
    }

    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM customer WHERE id=?",id);
    }

    public List<Customer> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM customer");
        List<Customer> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(new Customer(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4))
            );
        }
        return list;
    }
}
