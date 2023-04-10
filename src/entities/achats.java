package entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class achats {
    private Integer id;
    private Integer commande_id;
    private Date date_achat;
    private String full_name;
    private Integer tel;
    private String city;
    private Integer zip_code;
    private String payment_method;
    private List<achats> achats = new ArrayList<>();
   
    public achats() {
}
    public achats(Integer id, Integer commande_id, Date date_achat, String full_name, Integer tel, String city,
            Integer zip_code, String payment_method, List<achats> achats) {
        this.id = id;
        this.commande_id = commande_id;
        this.date_achat = date_achat;
        this.full_name = full_name;
        this.tel = tel;
        this.city = city;
        this.zip_code = zip_code;
        this.payment_method = payment_method;
        this.achats = achats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(Integer commande_id) {
        this.commande_id = commande_id;
    }

    public Date getDate_achat() {
        return date_achat;
    }

    public void setDate_achat(Date date_achat) {
        this.date_achat = date_achat;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZip_code() {
        return zip_code;
    }

    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public List<achats> getAchats() {
        return achats;
    }

    public void setAchats(List<achats> achats) {
        this.achats = achats;
    }

    

}
