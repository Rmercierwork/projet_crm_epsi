package fr.epsi.mspr.keunotor.service;

import fr.epsi.mspr.keunotor.dao.UserDAO;
import fr.epsi.mspr.keunotor.domain.User;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails user = userDAO.getUserbylogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found : " + username);
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new TechnicalException(e);
        }
    }

    public void addUser(User user) {
        try {
            userDAO.addUser(user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new TechnicalException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = userDAO.getAllUsers();
        return users;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public User modifyUser(User newUser) {
        userDAO.modifyUser(newUser);
        return newUser;
    }

    public void removeUser(User user) {
        userDAO.removeUser(user);
    }
}


