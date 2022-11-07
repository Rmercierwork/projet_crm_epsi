package fr.epsi.mspr.keunotor.service;

import fr.epsi.mspr.keunotor.dao.ProductSheetDAO;
import fr.epsi.mspr.keunotor.domain.ProductSheet;
import fr.epsi.mspr.keunotor.exception.BusinessException;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Insérer la javadoc
 */
@Service
public class ProductSheetService {
    @Autowired
    private ProductSheetDAO productSheetDAO;


    //Afficher les produits disponibles avec leurs caractéristiques dans un tableau//
    public List<ProductSheet> getAllProductSheet() throws SQLException {
        return productSheetDAO.getAllProductSheet();
    }

    //Créer une nouvelle fiche produit//
    public ProductSheet addProductSheet(ProductSheet productSheet) {
        this.productSheetDAO.addProductSheet(productSheet.getName(), productSheet.getReleaseDate(),
                productSheet.getDescription(), productSheet.getPegi(), productSheet.getGrade(), productSheet.getType(), productSheet.getPlatform());
        return productSheet;
    }

    //Modifier une fiche produit//
    //TODO: Gestion de l'erreur
    public ProductSheet modifyProductSheet(ProductSheet productSheet) throws BusinessException {
        /*if(productSheet.getName()==null){
            throw new BusinessException("product.name.null");
        }*/
        productSheetDAO.modifyProductSheet(productSheet);
        return productSheet;
    }

    //Chercher un produit en fonction de son nom et de son id//
    public ProductSheet getProductSheetById(int productSheetId) {
        return productSheetDAO.getProductSheetById(productSheetId);
    }
    //Méthode pour supprimer une fiche-produit par id (on la désactive, on la supprime pas réellement//
    public void removeProductSheet(ProductSheet productSheet) {
        this.productSheetDAO.removeProductSheet(productSheet);
    }
}
