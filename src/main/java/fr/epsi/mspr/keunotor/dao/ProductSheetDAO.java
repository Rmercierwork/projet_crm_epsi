package fr.epsi.mspr.keunotor.dao;

import fr.epsi.mspr.keunotor.domain.ClientSheet;
import fr.epsi.mspr.keunotor.domain.ProductSheet;
import fr.epsi.mspr.keunotor.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Accès à la BDD via le @Repository
 * Permet d'entrer les commandes SQL pour traiter les données depuis la BDD
 * Contient les méthodes addProductSheet, getAllProductSheet, getProductSheetById,
 * removeProductSheet, modifyProductSheet, qui permettent respectivement de
 * créer une fiche produit, d'afficher les fiches produits existantes, d'en sélectionner
 * une spécifique, de supprimer, mettre à jour une fiche produit.
 */
@Repository
public class ProductSheetDAO {
    @Autowired
    private DataSource ds;
    private static final Logger logger = LogManager.getLogger(ProductSheetDAO.class);

    //Méthode pour afficher les fiches produits existantes sur la page du staff//
    public List<ProductSheet> getAllProductSheet() throws SQLException {
        logger.debug("getAllProductSheet()");
        String sql = "select " +
                    "product_sheet.id as id, " +
                    "product_sheet.name as Label, " +
                    "product_sheet.release_date as ReleaseDate, " +
                    "product_sheet.description as Resume, " +
                    "product_sheet.pegi as Pegi, " +
                    "product_sheet.grade as Grade, " +
                    "product_sheet.type as Type, " +
                    "product_sheet.platform as Platform " +
                    "from product_sheet;";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<ProductSheet> productSheets = new ArrayList<>();
            while (rs.next()) {
                ProductSheet productSheet = new ProductSheet();
                productSheet.setId(rs.getInt("id"));
                productSheet.setReleaseDate(rs.getString("ReleaseDate"));
                productSheet.setName(rs.getString("Label"));
                productSheet.setDescription(rs.getString("Resume"));
                productSheet.setPegi(rs.getInt("Pegi"));
                productSheet.setGrade(rs.getInt("Grade"));
                productSheet.setType(rs.getString("Type"));
                productSheet.setPlatform(rs.getString("Platform"));
                productSheets.add(productSheet);
            }
            return productSheets;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    //Méthode pour ajouter une productSheet (réservé au staff)//
    public int addProductSheet(String name, String releaseDate, String description, Integer pegi, Integer grade, String type, String platform) {
        String sql = "insert into product_sheet " +
                    "(name, " +
                    "release_date, " +
                    "description, " +
                    "pegi, " +
                    "grade, " +
                    "type, " +
                    "platform) " +
                    "values (?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {


            ps.setString(1, name);
            ps.setString(2, releaseDate);
            ps.setString(3, description);
            ps.setInt(4, pegi);
            ps.setInt(5, grade);
            ps.setString(6, type);
            ps.setString(7, platform);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Product Sheet create error"));
    }

    //Méthode pour afficher une ProductSheet => rechercher par nom (staff)
    public ProductSheet getProductSheetById(int productSheetId) {
        String sql = "select " +
                "product_sheet.id as id, " +
                "product_sheet.name as Label, " +
                "product_sheet.release_date as ReleaseDate, " +
                "product_sheet.description as Resume, " +
                "product_sheet.pegi as Pegi, " +
                "product_sheet.grade as Grade, " +
                "product_sheet.type as Type, " +
                "product_sheet.platform as Platform " +
                "from product_sheet " +
                "WHERE product_sheet.id = ?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productSheetId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ProductSheet productSheet = new ProductSheet();
                productSheet.setId(rs.getInt("id"));
                productSheet.setReleaseDate(rs.getString("ReleaseDate"));
                productSheet.setName(rs.getString("Label"));
                productSheet.setDescription(rs.getString("Resume"));
                productSheet.setPegi(rs.getInt("Pegi"));
                productSheet.setGrade(rs.getInt("Grade"));
                productSheet.setType(rs.getString("Type"));
                productSheet.setPlatform(rs.getString("Platform"));

                return productSheet;
            }

            return null;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void removeProductSheet(ProductSheet productSheet) {
        String sql = "DELETE FROM product_sheet " +
                "where id = ?";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,productSheet.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    //TODO:Méthode pour mettre à jour une ProductSheet existante (admin,staff)
    //TODO: pouvoir la remplir entièrement et modifier un paramètre en particulier
    //TODO: finir de rédiger convenablement cette méthode
    //TODO:requête SQL à tester avant d'implémenter
    public ProductSheet modifyProductSheet(ProductSheet productSheet) {
        String sql = "UPDATE product_sheet " +
                "SET product_sheet.idProduct_sheet=?, " +
                "product_sheet.name=?, " +
                "product_sheet.release_date=?, " +
                "product_sheet.description=?, " +
                "product_sheet.pegi=?, " +
                "product_sheet.grade=?, " +
                "product_sheet.platform=? " +
                "WHERE product_sheet.id = ?;";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,productSheet.getId());
            ps.setString(2, productSheet.getName());
            ps.setString(3, productSheet.getReleaseDate());
            ps.setString(4, productSheet.getDescription());
            ps.setInt(5, productSheet.getPegi());
            ps.setInt(6, productSheet.getGrade());
            ps.setString(7, productSheet.getPlatform());
            ps.setInt(1,productSheet.getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();


        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return productSheet;
    }
}


