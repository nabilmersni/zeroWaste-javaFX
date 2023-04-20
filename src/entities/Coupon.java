package entities;

public class Coupon {
 private int id;
  private String email;
  private int coupon_code;
  private int produit_id;
  private int status;
public Coupon(int id, String email, int coupon_code, int produit_id, int status) {
    this.id = id;
    this.email = email;
    this.coupon_code = coupon_code;
    this.produit_id = produit_id;
    this.status = status;
}
public Coupon() {
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public int getCoupon_code() {
    return coupon_code;
}
public void setCoupon_code(int coupon_code) {
    this.coupon_code = coupon_code;
}
public int getProduit_id() {
    return produit_id;
}
public void setProduit_id(int produit_id) {
    this.produit_id = produit_id;
}
public int getStatus() {
    return status;
}
public void setStatus(int status) {
    this.status = status;
}
@Override
public String toString() {
    return "Coupon [id=" + id + ", email=" + email + ", coupon_code=" + coupon_code + ", produit_id=" + produit_id
            + ", status=" + status + "]";
}


}
