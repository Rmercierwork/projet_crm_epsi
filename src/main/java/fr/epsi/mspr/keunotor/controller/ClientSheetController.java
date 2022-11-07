package fr.epsi.mspr.keunotor.controller;

import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.domain.Order;
import fr.epsi.mspr.keunotor.domain.Response;
import fr.epsi.mspr.keunotor.service.ClientSheetService;
import fr.epsi.mspr.keunotor.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ClientSheetController {

    @Autowired
    private ClientSheetService clientSheetService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/salesRep/createclient_sheet/create")
    public Response<ClientSheet> createClientSheet(@RequestBody ClientSheet newclientSheet) {
        ClientSheet clientSheet = clientSheetService.createClientSheet(newclientSheet);
        Response<ClientSheet> response = new Response<>();
        response.setData(clientSheet);
        return response;
    }

    @PostMapping("/salesRep/client_sheet/modify")
    public Response<ClientSheet> modifyClientSheet(@RequestBody ClientSheet newClientSheet) {
        ClientSheet clientSheet = clientSheetService.modifyClientSheet(newClientSheet);
        Response<ClientSheet> response = new Response<>();
        response.setData(clientSheet);
        return response;
    }

    @GetMapping("/salesRep/client_sheets")
    public Response<List<ClientSheet>> getAllClientSheet() throws SQLException {
        List<ClientSheet> clientSheets = clientSheetService.getAllClientSheet();

        Response <List<ClientSheet>> response = new Response<>();
        response.setData(clientSheets);

        return response;
    }

    @PostMapping("/salesRep/client_sheets/remove")
    public Response<Void> removeClientSheet(@RequestBody ClientSheet clientSheet) {
        clientSheetService.removeClientSheet(clientSheet);
        return new Response<>();
    }

    @GetMapping("/salesRep/client_sheet")
    public Response<ClientSheet> getClientSheet(@RequestParam int clientSheetId) {
        ClientSheet clientSheet = clientSheetService.getClientSheetById(clientSheetId);
        Response<ClientSheet> response = new Response<>();
        response.setData(clientSheet);
        return response;
    }

    @GetMapping("/salesRep/client_sheet/orders")
    public Response<List<Order>> getOrdersByClientId (@RequestParam int clientSheetId) {
        List<Order> orders = orderService.getAllOrdersByClientId(clientSheetId);
        Response<List<Order>> response = new Response<>();
        response.setData(orders);
        return response;
    }
}
