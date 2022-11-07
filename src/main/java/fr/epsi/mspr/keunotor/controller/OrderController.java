package fr.epsi.mspr.keunotor.controller;

import fr.epsi.mspr.keunotor.domain.Order;
import fr.epsi.mspr.keunotor.domain.Response;
import fr.epsi.mspr.keunotor.domain.User;
import fr.epsi.mspr.keunotor.exception.BusinessException;
import fr.epsi.mspr.keunotor.service.OrderService;
import fr.epsi.mspr.keunotor.service.ProductSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductSheetService productSheetService;

    @PostMapping("/salesRep/createorder/create")
    public Response<Void> createOrder(@RequestBody Order order, Authentication authentication)
            throws BusinessException {
        if(authentication == null) {
            throw new BusinessException("user not logged");
        }
        User user = (User) authentication.getPrincipal();
        orderService.createOrder(order, user);
        return new Response<>();
    }

    @PostMapping("/salesRep/order/product")
    public Response<Void> addProductToOrder(
            @RequestParam int orderId,
            @RequestParam int productId) throws BusinessException {

        orderService.addProductToOrder(orderId, productId);
        return new Response<>();
    }

    @GetMapping("/salesRep/orders")
    public Response<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        Response<List<Order>> response = new Response<>();
        response.setData(orders);

        return response;
    }

    @PostMapping("/salesRep/orders/remove")
    public Response<Void> removeOrder(@RequestBody Order order) {
        orderService.removeOrder(order);
        return new Response<>();
    }

    @GetMapping("/salesRep/order")
    public Response<Order> getOrderById(@RequestParam int orderId) {
        Order order = orderService.getOrderById(orderId);
        Response<Order> response = new Response<>();
        response.setData(order);
        return response;
    }

    @PostMapping("salesRep/order/remove")
    public Response<Void> removeProduct(
            @RequestParam int orderId,
            @RequestParam int productId) {
        orderService.removeProduct(orderId, productId);
        return new Response<>();
    }
}
