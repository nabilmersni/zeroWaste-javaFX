package entities;

public class Produit {

    private int id;
    private String nom_produit;
    private String description;
    private int quantite;
    private float prix_produit;
    private String image;
    private int categorie_produit_id;
    private int prix_point_produit;
    private float remise;
    private String etiquette;
    private double score;

    public static int actionTest = 0;

    private static int idProduit;
    private static String searchValue;

    private static String sortByCateg = null;
    private static int sortByCategId = -1;
    private static String searchImageEtiquette = null;

    private static double searchImageScore = -1;

    private static int listAchat = 0;

    private static int valueReviews = 0;

    private static int ReviewId = 0;

    public Produit() {
    }

    public Produit(String nom_produit, String description, int quantite, float prix_produit, String image,
            int categorie_produit_id, int prix_point_produit) {
        this.nom_produit = nom_produit;
        this.description = description;
        this.quantite = quantite;
        this.prix_produit = prix_produit;
        this.image = image;
        this.categorie_produit_id = categorie_produit_id;
        this.prix_point_produit = prix_point_produit;
    }

    public int getId() {
        return id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(Float p) {
        this.prix_produit = p;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategorie_produit_id() {
        return categorie_produit_id;
    }

    public void setCategorie_produit_id(int categorie_produit_id) {
        this.categorie_produit_id = categorie_produit_id;
    }

    public int getPrix_point_produit() {
        return prix_point_produit;
    }

    public void setPrix_point_produit(int prix_point_produit) {
        this.prix_point_produit = prix_point_produit;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    public String getEtiquette() {
        return etiquette;
    }

    public void setEtiquette(String etiquette) {
        this.etiquette = etiquette;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Produit [nom_produit=" + nom_produit + ", description=" + description + ", quantite=" + quantite
                + ", prix_produit=" + prix_produit + ", image=" + image + ", categorie_produit_id="
                + categorie_produit_id + ", prix_point_produit=" + prix_point_produit + "]";
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getSearchValue() {
        return searchValue;
    }

    public static void setSearchValue(String searchValue) {
        Produit.searchValue = searchValue;
    }

    public static int getIdProduit() {
        return idProduit;
    }

    public static void setIdProduit(int idProduit) {
        Produit.idProduit = idProduit;
    }

    public static String getSortByCateg() {
        return sortByCateg;
    }

    public static void setSortByCateg(String sortByCateg) {
        Produit.sortByCateg = sortByCateg;
    }

    public static int getSortByCategId() {
        return sortByCategId;
    }

    public static void setSortByCategId(int sortByCategId) {
        Produit.sortByCategId = sortByCategId;
    }

    public static String getSearchImageEtiquette() {
        return searchImageEtiquette;
    }

    public static void setSearchImageEtiquette(String searchImageEtiquette) {
        Produit.searchImageEtiquette = searchImageEtiquette;
    }

    public static double getSearchImageScore() {
        return searchImageScore;
    }

    public static void setSearchImageScore(double searchImageScore) {
        Produit.searchImageScore = searchImageScore;
    }

    public static int getListAchat() {
        return listAchat;
    }

    public static void setListAchat(int listAchat) {
        Produit.listAchat = listAchat;
    }

    public static int getValueReviews() {
        return valueReviews;
    }

    public static void setValueReviews(int valueReviews) {
        Produit.valueReviews = valueReviews;
    }

    public static int getReviewId() {
        return ReviewId;
    }

    public static void setReviewId(int reviewId) {
        ReviewId = reviewId;
    }

}
