package fr.epsi.mspr.keunotor.dao;

import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.domain.User;
import fr.epsi.mspr.keunotor.domain.UserRole;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDAO {

    @Autowired
    private DataSource ds;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserbylogin(String login) throws SQLException {
        String sql =
                "select login, password, role, id " +
                        "from user " +
                        "where login = '" + login + "'";

        Statement stmt = ds.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            User user = new User();
            user.setLogin(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setRole(UserRole.valueOf(rs.getString(3)));
            user.setId(rs.getInt(4));
            return user;
        } else {
            return null;
        }
    }

    public void addUser(User user) throws SQLException {
        String password = passwordEncoder.encode(user.getPassword());
        String sql =
                "insert into user (login, password, role) " +
                        "values ('" + user.getLogin() + "', '" + password + "', '" + user.getRole() + "')";

        Statement stmt = ds.getConnection().createStatement();
        stmt.executeUpdate(sql);
    }

    public List<User> getAllUsers() throws SQLException {
        String sql ="SELECT " +
                "user.login as Login, " +
                "user.role as Role " +
                "FROM `user` " +
                "GROUP BY login;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("Login"));
                user.setRole(UserRole.valueOf(rs.getString("Role")));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public User getUserById(int userId) {
        String sql = "select user.id as userId " +
                "from user " +
                "where user.id = ?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("userid"));
                return user;
            }

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Get user by id error"));
    }

    public void modifyUser(User user) {
        String sql = "UPDATE user " +
                "SET role = '?', " +
                "WHERE user.login = '?';";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, String.valueOf(user.getRole()));
            ps.setString(2, user.getLogin());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }

    public void removeUser(User user) {
        String sql = "DELETE FROM user " +
                    "WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}