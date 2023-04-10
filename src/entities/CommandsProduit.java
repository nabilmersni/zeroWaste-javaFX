package entities;

public class CommandsProduit {

    private Integer id;
    private Integer quantiteC;
   
    private Produit produit;

    private Commands commande;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantiteC() {
        return quantiteC;
    }

    public void setQuantiteC(Integer quantiteC) {
        this.quantiteC = quantiteC;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commands getCommande() {
        return commande;
    }

    public void setCommande(Commands commande) {
        this.commande = commande;
    }
}
