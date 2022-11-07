package fr.epsi.mspr.keunotor.dao;

import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientSheetDAO {

    @Autowired
    private DataSource ds;

    public int createClientSheet(String firstname, String lastname, String email, String phonenumber, String city, Integer streetnumber, String street, String zipcode) {
        String sql = "insert into client_sheet" +
                "(first_name, last_name, email, mobile_number, city, street_number, street, zip_code) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, phonenumber);
            ps.setString(5, city);
            ps.setInt(6, streetnumber);
            ps.setString(7, street);
            ps.setString(8, zipcode);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("ClientSheet create error"));
    }

    public List<ClientSheet> getAllClientSheet() throws SQLException {
        String sql ="select " +
                "client_sheet.id as id, " +
                "client_sheet.first_name as FirstName, " +
                "client_sheet.last_name as LastName, " +
                "client_sheet.email as Mail, " +
                "client_sheet.mobile_number as PhoneNumber, " +
                "client_sheet.city as City, " +
                "client_sheet.street_number as StreetNumber, " +
                "client_sheet.street as Street, " +
                "client_sheet.zip_code as ZipCode " +
                "from client_sheet;";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<ClientSheet> clientSheets = new ArrayList<>();
            while (rs.next()) {
                ClientSheet clientSheet = new ClientSheet();
                clientSheet.setId(rs.getInt("id"));
                clientSheet.setFirstname(rs.getString("Firstname"));
                clientSheet.setLastname(rs.getString("Lastname"));
                clientSheet.setEmail(rs.getString("Mail"));
                clientSheet.setPhonenumber(rs.getString("PhoneNumber"));
                clientSheet.setCity(rs.getString("City"));
                clientSheet.setStreetnumber(rs.getInt("StreetNumber"));
                clientSheet.setStreet(rs.getString("Street"));
                clientSheet.setZipcode(rs.getString("ZipCode"));
                clientSheets.add(clientSheet);
            }
            return clientSheets;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public ClientSheet getClientSheetById(int clientSheetId) {
        String sql = "SELECT client_sheet.id as id, " +
                    "client_sheet.first_name as FirstName, " +
                    "client_sheet.last_name as LastName, " +
                    "client_sheet.email as Mail, " +
                    "client_sheet.mobile_number as PhoneNumber, " +
                    "client_sheet.city as City, " +
                    "client_sheet.street_number as StreetNumber, " +
                    "client_sheet.street as Street, " +
                    "client_sheet.zip_code as ZipCode " +
                    "FROM client_sheet " +
                    "WHERE client_sheet.id = ?;";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clientSheetId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ClientSheet clientSheet = new ClientSheet();
                clientSheet.setId(rs.getInt("id"));
                clientSheet.setFirstname(rs.getString("Firstname"));
                clientSheet.setLastname(rs.getString("Lastname"));
                clientSheet.setEmail(rs.getString("Mail"));
                clientSheet.setPhonenumber(rs.getString("PhoneNumber"));
                clientSheet.setCity(rs.getString("City"));
                clientSheet.setStreetnumber(rs.getInt("StreetNumber"));
                clientSheet.setStreet(rs.getString("Street"));
                clientSheet.setZipcode(rs.getString("ZipCode"));

                return clientSheet;
            }

            return null;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public ClientSheet modifyClientSheet(ClientSheet newClientSheet) {
        String sql = "UPDATE client_sheet " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "email = ?, " +
                    "mobile_number = ?, " +
                    "city = ?, " +
                    "street_number = ?, " +
                    "street = ?, " +
                    "zip_code = ? " +
                    "WHERE client_sheet.id = ?;";
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, newClientSheet.getFirstname());
            ps.setString(2, newClientSheet.getLastname());
            ps.setString(3, newClientSheet.getEmail());
            ps.setString(4, newClientSheet.getPhonenumber());
            ps.setString(5, newClientSheet.getCity());
            ps.setInt(6, newClientSheet.getStreetnumber());
            ps.setString(7, newClientSheet.getStreet());
            ps.setString(8, newClientSheet.getZipcode());
            ps.setInt(9, newClientSheet.getId());

            ps.executeUpdate();
            return newClientSheet;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public void removeClientSheet(ClientSheet clientSheet) {

        String sql = "DELETE FROM client_sheet WHERE id =?";
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientSheet.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}
