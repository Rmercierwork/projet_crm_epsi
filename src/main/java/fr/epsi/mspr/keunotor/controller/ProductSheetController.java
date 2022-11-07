package fr.epsi.mspr.keunotor.controller;

import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.domain.ProductSheet;
import fr.epsi.mspr.keunotor.domain.Response;
import fr.epsi.mspr.keunotor.exception.BusinessException;
import fr.epsi.mspr.keunotor.service.ProductSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * La partie controller permet au programme d'avoir une interface utilisateur dans l'application.
 * Toute méthode nécessitant une interaction utilisateur passe par le controller.
 * Ici on va reprendre les méthodes getAllProductSheet() permettant d'afficher mon tableau de produits disponibles
 * avec leurs détails correspondant, addProductSheet() qui permet au staff de créer une nouvelle fiche produit dans
 * la BDD, getProductSheetById() qui permet de rechercher un produit en particulier en fonction de son id,
 * removeProductSheet() qui permet d'effacer un produit de la BDD,modifyProductSheet() qui permet de mettre à jour
 * la BDD.
 */
@Controller
@RestController
public class ProductSheetController {
    @Autowired
    private ProductSheetService productSheetService;


    @GetMapping("/salesRep/productsheets")
    public Response<List<ProductSheet>> getAllProductSheet() throws SQLException {
        List<ProductSheet> productSheets = productSheetService.getAllProductSheet();
        Response<List<ProductSheet>> response = new Response();
        response.setData(productSheets);
        return response;
    }

    //Méthode pour afficher une ProductSheet en particulier => getProductSheetById()
    @GetMapping("/salesRep/productsheet")
    public Response<ProductSheet> getProductSheetById(@RequestParam int productSheetId) {
        ProductSheet productSheet = productSheetService.getProductSheetById(productSheetId);
        Response<ProductSheet> response = new Response<>();
        response.setData(productSheet);
        return response;
    }

    @PostMapping("/staff/createproductsheet/create")
    public Response<ProductSheet> createProductSheet(@RequestBody ProductSheet newProductSheet) {
        ProductSheet productSheet = productSheetService.addProductSheet(newProductSheet);
        Response<ProductSheet> response = new Response<>();
        response.setData(productSheet);
        return response;
    }

    //Méthode pour effacer une fiche produit//
    @PostMapping("/staff/productsheets/remove")
    public Response<ProductSheet>removeProductSheet(@RequestBody ProductSheet productSheet) {
        productSheetService.removeProductSheet(productSheet);
        return new Response<>();
    }

    //Méthode pour modifier une ficher produit//
    //FIXME
    @PostMapping("/staff/productsheet/modify")
    public Response<ProductSheet>modifyProductSheet(
            @RequestBody ProductSheet productSheet){
        Response<ProductSheet> response = new Response<>();

        try{
            productSheet = productSheetService.modifyProductSheet(productSheet);
            response.setSuccess(true);
        } catch (BusinessException e) {
            response.setSuccess(false);
        }
        response.setData(productSheet);
        return response;
    }
}
