package fr.epsi.mspr.keunotor.domain;

import java.util.List;

public class Order {

    private int id;
    private String vendorName;
    private int clientId;
    private String clientFirstName;
    private String clientLastName;
    private long date;
    private String Status;
    private List<ProductSheet> productSheets;

    public int getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public long getDate() {
        return date;
    }

    public String getStatus() {
        return Status;
    }

    public List<ProductSheet> getProductSheets() {
        return productSheets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setProductSheets(List<ProductSheet> productSheets) {
        this.productSheets = productSheets;
    }
}
