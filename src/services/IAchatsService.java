package services;

import java.sql.SQLException;
import java.util.List;

import entities.Achats;
import entities.Coupon;
import entities.Produit;

public interface IAchatsService {

  public List<Achats> getAllAchats();

  public void adminDeleteAchat(int achat_id);

  public Achats getOneAchat(int idAchat) throws SQLException;

  public List<Produit> getAllProducts(int commande_id);

  public List<Achats> searchCommands(String search);

  public List<Achats> getAchatsToday();

  public List<Achats> getAchatsLastWeek();

  public List<Achats> getAchatsLastMonth();

  public void Checkout(Achats achat);

  public void supprimerAddress(int achat_Id) throws SQLException;

  public Achats getAddressDetails(int commande_id) throws SQLException;

  public void updateCheckout(Achats achat);

  // 1: set method name (update)*** 2: set null (delete)
  // PaymentMethod: Points *** Stripe *** Livraison
  public void updatePaymentMethod(int test, int achatId, String PaymentMethod);

  public void ValidateCheckoutPoints(int command_id, int achatId, int user_id, int point);

  public void ValidateCheckoutLivraison(int command_id, int achatId);

  public void addCoupon(int coupon_code, int produit_id, String email);

  public int VerifCoupon(int coupon_code) throws SQLException;

  public int VerifUserCoupon(int coupon_code, String email) throws SQLException;

  public void updateStatusCoupon(int coupon_code, String email);

  public Coupon getOneCoupon(int coupon_code) throws SQLException;

  public void updateCommandStatus(int achatId, int etat);

}
