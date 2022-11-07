package fr.epsi.mspr.keunotor.dao;

import fr.epsi.mspr.keunotor.domain.Order;
import fr.epsi.mspr.keunotor.domain.ProductSheet;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import fr.epsi.mspr.keunotor.service.ProductSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAO {

    @Autowired
    private DataSource ds;

    @Autowired
    private ProductSheetService productSheetService;

    public int createOrder(String vendorName, int clientId) {
        String sql = "INSERT INTO order_detail (vendorname, client_sheet_id) values (?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                 ps.setString(1, vendorName);
                 ps.setInt(2, clientId);
                 ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (SQLException e) {
                 throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Order create error"));
    }

    public void addProductToOrder(int orderId, int productId) {
        String sql = "insert into order_detail_has_product_sheet " +
                "(order_detail_id, product_sheet_id) values (?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void addClientToOrder(int orderId, int clientId) {
        String sql = "insert into order_detail_has_client_sheet " +
                "(order_detail_id, client_sheet_id) values (?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, clientId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public List<Order> getAllOrders() {
        String sql ="SELECT order_detail.id as orderId, " +
                    "order_detail.vendorname as vendorName, " +
                    "order_detail.status as status, " +
                    "GROUP_CONCAT(product_sheet.id) as products, " +
                    "order_detail.client_sheet_id as clientId, " +
                    "client_sheet.first_name as clientFirstName, " +
                    "client_sheet.last_name as clientLastName " +
                    "from order_detail " +
                    "right join order_detail_has_product_sheet " +
                    "on order_detail_has_product_sheet.order_detail_id = order_detail.id " +
                    "left join product_sheet " +
                    "on order_detail_has_product_sheet.product_sheet_id = product_sheet.id " +
                    "LEFT JOIN client_sheet " +
                    "ON client_sheet.id = order_detail.client_sheet_id " +
                    "group by order_detail.id;";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                String products = rs.getString("products");

                List<ProductSheet> productSheetList = new ArrayList<>();
                for (String product : products.split(",")) {
                    ProductSheet productSheetPojo = new ProductSheet();
                    productSheetPojo.setId(Integer.parseInt(product));
                    productSheetList.add(productSheetPojo);
                }
                order.setProductSheets(productSheetList);
                order.setId(rs.getInt("orderId"));
                order.setVendorName(rs.getString("vendorName"));
                order.setClientId(rs.getInt("clientId"));
                order.setClientFirstName(rs.getString("clientFirstName"));
                order.setClientLastName(rs.getString("clientLastName"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void removeOrder(Order order) {
        //Transactional request won't work
        String sqlOrder =
                "DELETE FROM order_detail_has_product_sheet \n " +
                "WHERE order_detail_has_product_sheet.order_detail_id = ?; \n ";
        String sqlOrderProduct =
                "DELETE FROM order_detail \n " +
                "WHERE order_detail.id = ?; \n ";

        try (Connection conn = ds.getConnection();
             PreparedStatement psOrder = conn.prepareStatement(sqlOrder);
            PreparedStatement psProduct = conn.prepareStatement(sqlOrderProduct)) {

            psOrder.setInt(1, order.getId());
            psProduct.setInt(1, order.getId());
            psOrder.executeUpdate();
            psProduct.executeUpdate();

        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public List<Order> getAllOrderByClientId(int clientSheetId) {
        String sql ="SELECT order_detail.id as id, " +
                    "order_detail.date as orderDate, " +
                    "order_detail.status as status " +
                    "FROM order_detail  " +
                    "WHERE order_detail.client_sheet_id = ? " +
                    "ORDER BY order_detail.id;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientSheetId);

            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setDate(rs.getTimestamp("orderDate").getTime());
                order.setStatus(rs.getString("status"));
                orders.add(order);

            }
            return orders;
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT " +
                "order_detail.id AS orderId, " +
                "order_detail.date AS orderDate, " +
                "GROUP_CONCAT(product_sheet.id ORDER BY product_sheet.id ASC) AS products " +
                "FROM order_detail " +
                "RIGHT JOIN order_detail_has_product_sheet " +
                "ON order_detail_has_product_sheet.order_detail_id = order_detail.id " +
                "LEFT JOIN product_sheet " +
                "ON order_detail_has_product_sheet.product_sheet_id = product_sheet.id " +
                "WHERE order_detail.id = ? " +
                "GROUP BY order_detail.id;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("orderId"));

                order.setProductSheets(new ArrayList<>());
                String productSheetList = rs.getString("products");
                if (productSheetList == null || productSheetList.length() == 0) {
                    return order;
                }
                for (String productSheetId : productSheetList.split(",")) {
                    if (productSheetId.length() == 0) {
                        continue;
                    }
                    ProductSheet productSheet = productSheetService.getProductSheetById(Integer.parseInt(productSheetId));
                    order.getProductSheets().add(productSheet);
                }
                return order;
            }
            return null;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void removeProduct(int orderId, int productId) {
        String sql = "DELETE FROM order_detail_has_product_sheet " +
                "WHERE order_detail_id = ? " +
                "AND product_sheet_id = ?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

}
