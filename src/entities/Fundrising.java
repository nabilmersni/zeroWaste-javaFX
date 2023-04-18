package entities;

import java.sql.Date;

public class Fundrising {

    private int id;
    private String titre_don;
    private String description_don;
    private String image_don;
    private Date date_don;
    private Date date_don_limite;
    private String etat;
    private float objectif;
    private float total;

    public static int actionTest = 0;

    private static int idFund;

    public static int getIdFund() {
        
        return idFund;
    }

    public static void setIdFund(int idFund) {
        Fundrising.idFund = idFund;
    }
    
    public Fundrising() {
    }


    public Fundrising( String titre_don, String description_don, String image_don, Date date_don,
            Date date_don_limite, String etat, float objectif, float total) {
       this.titre_don = titre_don;
        this.description_don = description_don;
        this.image_don = image_don;
        this.date_don = date_don;
        this.date_don_limite = date_don_limite;
        this.etat = etat;
        this.objectif = objectif;
        this.total = total;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitre_don() {
        return titre_don;
    }
    public void setTitre_don(String titre_don) {
        this.titre_don = titre_don;
    }
    public String getDescription_don() {
        return description_don;
    }
    public void setDescription_don(String description_don) {
        this.description_don = description_don;
    }
    public String getImage_don() {
        return image_don;
    }
    public void setImage(String image_don) {
        this.image_don = image_don;
    }
    public Date getDate_don() {
        return date_don;
    }
    public void setDate_don(Date date_don) {
        this.date_don = date_don;
    }
    public Date getDate_don_limite() {
        return date_don_limite;
    }
    public void setDate_don_limite(Date date_don_limite) {
        this.date_don_limite = date_don_limite;
    }
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
    public float getObjectif() {
        return objectif;
    }
    public void setObjectif(float objectif) {
        this.objectif = objectif;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
 

    @Override
    public String toString() {
        return "Produit [titre_don=" + titre_don + ", description_don=" + description_don + ", image_don=" + image_don + ", date_don=" + date_don
                + ", date_don_limite=" + date_don_limite + ", etat=" + etat + ", objectif="
                + objectif + ", total=" + total + "]";
    }

    
}