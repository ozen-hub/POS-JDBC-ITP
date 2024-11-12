import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static PreparedStatement createStatement(
            String sql, Object... params
    ) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().
                getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stm.setObject((i + 1), params[i]);
        }
        return stm;
    }

    public static boolean executeUpdate(
            String sql, Object... params
    ) throws SQLException, ClassNotFoundException {
        return createStatement(sql, params).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(
            String sql, Object... params
    ) throws SQLException, ClassNotFoundException {
        return createStatement(sql, params).executeQuery();
    }
}
