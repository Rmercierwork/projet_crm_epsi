package fr.epsi.mspr.keunotor.service;

import fr.epsi.mspr.keunotor.dao.OrderDAO;
import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.domain.Order;
import fr.epsi.mspr.keunotor.domain.User;
import fr.epsi.mspr.keunotor.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public void createOrder(Order order, User user) throws BusinessException {
        int orderId = orderDAO.createOrder(user.getLogin(), order.getClientId());
        for (int i = 0; i < order.getProductSheets().size(); i++) {
            int productId = order.getProductSheets().get(i).getId();
            orderDAO.addProductToOrder(orderId, productId);
        }
    }

    public void addProductToOrder(int orderId, int productId)
            throws BusinessException {

        if (orderId < 0 || productId < 0) {
            throw new BusinessException("order.product_sheet.id.invalide");
        }
        orderDAO.addProductToOrder(orderId, productId);
    }

    public void addClientToOrder(int orderId, int clientId)
            throws BusinessException {

        if (orderId < 0 || clientId < 0) {
            throw new BusinessException("order.product_sheet.id.invalide");
        }
        orderDAO.addClientToOrder(orderId, clientId);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.getAllOrders();
        return orders;
    }

    public List<Order> getAllOrdersByClientId(int clientSheetId) {
        return orderDAO.getAllOrderByClientId(clientSheetId);
    }

    public Order getOrderById(int orderId) {
        Order order = orderDAO.getOrderById(orderId);
        return order;
    }

    public int removeProduct(int orderId, int productId) {
        orderDAO.removeProduct(orderId, productId);
        return orderId;
    }

    public void removeOrder(Order order) {
        orderDAO.removeOrder(order);
    }
}
