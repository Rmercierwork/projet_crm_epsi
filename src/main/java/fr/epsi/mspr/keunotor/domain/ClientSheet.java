package fr.epsi.mspr.keunotor.domain;

/** CLientSheet est le formulaire d'inscription que doit remplir le client, et qui sera utilisé par le staff pour
 * faire correspondre avec l'Order.
 */
public class ClientSheet {
    private int id;
    private String email;
    private String lastname;
    private String firstname;
    private String phonenumber;
    private String city;
    private int streetnumber;
    private String street;
    private String zipcode;

    //Getter et setter des variables ClientSheet

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getCity() {
        return city;
    }

    public int getStreetnumber() {
        return streetnumber;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreetnumber(int streetnumber) {
        this.streetnumber = streetnumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
