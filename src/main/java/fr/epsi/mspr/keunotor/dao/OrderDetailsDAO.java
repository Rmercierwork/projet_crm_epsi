package fr.epsi.mspr.keunotor.dao;

import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class OrderDetailsDAO {

    @Autowired
    private DataSource ds;

    public int createOrderDetail(String vendorName) {
        String sql = "insert into order_detail (vendorName) values (?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vendorName);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Pizza create error"));
    }
}
