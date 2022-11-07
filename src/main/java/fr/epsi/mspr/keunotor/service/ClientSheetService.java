package fr.epsi.mspr.keunotor.service;

import fr.epsi.mspr.keunotor.dao.ClientSheetDAO;
import fr.epsi.mspr.keunotor.domain.ClientSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientSheetService {

    @Autowired
    private ClientSheetDAO clientSheetDAO;

    public ClientSheet createClientSheet(ClientSheet clientSheet) {
        this.clientSheetDAO.createClientSheet(clientSheet.getFirstname(),
                clientSheet.getLastname(), clientSheet.getEmail(), clientSheet.getPhonenumber(), clientSheet.getCity(), clientSheet.getStreetnumber(), clientSheet.getStreet(), clientSheet.getZipcode());
        return clientSheet;
    }

    public ClientSheet getClientSheetById(int clientSheetId) {
        return clientSheetDAO.getClientSheetById(clientSheetId);
    }

    public ClientSheet modifyClientSheet(ClientSheet newClientSheet) {
        this.clientSheetDAO.modifyClientSheet(newClientSheet);
        return newClientSheet;
    }

    public List<ClientSheet> getAllClientSheet() throws SQLException {
        return clientSheetDAO.getAllClientSheet();
    }

    public void removeClientSheet(ClientSheet clientSheet) {
        this.clientSheetDAO.removeClientSheet(clientSheet);
    }
}
