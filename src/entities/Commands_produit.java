package entities;

public class Commands_produit {
    private int id;
    private int produit_id;
    private int commande_id;
    private int quantite_c;
    
    @Override
    public String toString() {
        return "Commands_produit [id=" + id + ", produit_id=" + produit_id + ", commande_id=" + commande_id
                + ", quantite_c=" + quantite_c + "]";
    }
    public Commands_produit(int id, int produit_id, int commande_id, int quantite_c) {
        this.id = id;
        this.produit_id = produit_id;
        this.commande_id = commande_id;
        this.quantite_c = quantite_c;
    }
    public Commands_produit() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProduit_id() {
        return produit_id;
    }
    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }
    public int getCommande_id() {
        return commande_id;
    }
    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }
    public int getQuantite_c() {
        return quantite_c;
    }
    public void setQuantite_c(int quantite_c) {
        this.quantite_c = quantite_c;
    }
}
