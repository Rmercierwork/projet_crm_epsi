package fr.epsi.mspr.keunotor.controller;

import fr.epsi.mspr.keunotor.domain.Response;
import fr.epsi.mspr.keunotor.domain.User;
import fr.epsi.mspr.keunotor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/register")
    public void addUser(User user) {
        userService.addUser(user);
    }

    @GetMapping("/admin/users")
    public Response<List<User>> getAllUsers() throws SQLException {
        List<User> galettes = userService.getAllUsers();
        Response<List<User>> response = new Response<>();
        response.setData(galettes);
        return response;
    }

    @GetMapping("/admin/user")
    public Response<User> getUserById(@RequestParam int userId) {
        User user = userService.getUserById(userId);

        Response<User> response = new Response<>();
        response.setData(user);

        return response;
    }

    @PostMapping("/admin/users/modify")
    public Response<User> modifyUser(@RequestBody User modifyUser) {
        User user = userService.modifyUser(modifyUser);
        Response<User> response = new Response<>();
        response.setData(user);
        return response;
    }

    @PostMapping("/admin/users/remove")
    public Response<Void> removeUser(@RequestBody User user) {
        userService.removeUser(user);
        return new Response<>();
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
//        return authentication.getName();
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication1 instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication1.getName();
            return currentUserName;
        }
        return null;
    }

}
