package entities;


public class Achats {

  private int id;
  private int commande_id;
  private String date_achat;
  private String full_name;
  private String email;
  private String address;
  private int tel;
  private String city;
  private int zip_code;
  private String payment_method;
  private int validate;
  private int userDeleted;
  private int adminDeleted;
  public static int achatModelTest=0 ;
  private static int commandeId ;
  private static String SearchValue ;

 
  public static String getSearchValue() {
    return SearchValue;
  }

  public static void setSearchValue(String SearchValue) {
    Achats.SearchValue = SearchValue;
  }

  public static int getCommandeId() {
    return commandeId;
  }

  public static void setCommandeId(int commandeId) {
    Achats.commandeId = commandeId;
  }

  public static int getAchatId() {
    return achatId;
  }

  public static void setAchatId(int achatId) {
    Achats.achatId = achatId;
  }

  private static int achatId;
  
  public Achats(int id, int commande_id, String date_achat, String full_name, String email, String address, int tel,
      String city, int zip_code, String payment_method, int validate, int userDeleted, int adminDeleted) {
    this.id = id;
    this.commande_id = commande_id;
    this.date_achat = date_achat;
    this.full_name = full_name;
    this.email = email;
    this.address = address;
    this.tel = tel;
    this.city = city;
    this.zip_code = zip_code;
    this.payment_method = payment_method;
    this.validate = validate;
    this.userDeleted = userDeleted;
    this.adminDeleted = adminDeleted;
  }

  public Achats() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCommande_id() {
    return commande_id;
  }

  public void setCommande_id(int commande_id) {
    this.commande_id = commande_id;
  }

  public String getDate_achat() {
    return date_achat;
  }

  public void setDate_achat(String date_achat) {
    this.date_achat = date_achat;
  }

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getTel() {
    return tel;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getZip_code() {
    return zip_code;
  }

  public void setZip_code(int zip_code) {
    this.zip_code = zip_code;
  }

  public String getPayment_method() {
    return payment_method;
  }

  public void setPayment_method(String payment_method) {
    this.payment_method = payment_method;
  }

  public int getValidate() {
    return validate;
  }

  public void setValidate(int validate) {
    this.validate = validate;
  }

  public int getUserDeleted() {
    return userDeleted;
  }

  public void setUserDeleted(int userDeleted) {
    this.userDeleted = userDeleted;
  }

  public int getAdminDeleted() {
    return adminDeleted;
  }

  public void setAdminDeleted(int adminDeleted) {
    this.adminDeleted = adminDeleted;
  }

  @Override
  public String toString() {
    return "Achats [id=" + id + ", commande_id=" + commande_id + ", date_achat=" + date_achat + ", full_name="
        + full_name + ", email=" + email + ", address=" + address + ", tel=" + tel + ", city=" + city + ", zip_code="
        + zip_code + ", payment_method=" + payment_method + ", validate=" + validate + ", userDeleted=" + userDeleted
        + ", adminDeleted=" + adminDeleted + "]";
  }
  
}
