package Dao;


public class Contact {

    private String name;
    private String company;
    private String telephone;
    private String email;
    private String business;

    public Contact(String name, String company, String telephone, String email, String business) {
        this.name = name;
        this.company = company;
        this.telephone = telephone;
        this.email = email;
        this.business = business;
    }

    public Contact(){}

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getBusiness() {
        return business;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
