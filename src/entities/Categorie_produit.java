package entities;

public class Categorie_produit {

    private int id;
    private String nom_categorie;
    private String image_categorie;
    private int order_categ;
    
    
    public Categorie_produit(int id, String nom_categorie, String image_categorie) {
      this.id = id;
      this.nom_categorie = nom_categorie;
      this.image_categorie = image_categorie;
    }

    public Categorie_produit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public String getImage_categorie() {
        return image_categorie;
    }

    public void setImage_categorie(String image_categorie) {
        this.image_categorie = image_categorie;
    }

    public int getOrder_categ() {
        return order_categ;
    }

    public void setOrder_categ(int order_categ) {
        this.order_categ = order_categ;
    }

   
    @Override
    public String toString() {
        return "Categorie_produit [id=" + id + ", nom_categorie=" + nom_categorie + ", image_categorie="
                + image_categorie + "]";
    }

    
}
