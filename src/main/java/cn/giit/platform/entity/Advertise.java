package cn.giit.platform.entity;

public class Advertise extends BaseEntity {

    private String company;

    private Integer companyId;

    private String title;

    private String city;

    private Integer lowestPay;

    private Integer mostPay;

    private String description;

    private String phone;

    private String address;

    private String email;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getLowestPay() {
        return lowestPay;
    }

    public void setLowestPay(Integer lowestPay) {
        this.lowestPay = lowestPay;
    }

    public Integer getMostPay() {
        return mostPay;
    }

    public void setMostPay(Integer mostPay) {
        this.mostPay = mostPay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

}