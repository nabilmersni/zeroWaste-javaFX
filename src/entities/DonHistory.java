package entities;

import java.sql.Date;

public class DonHistory {

    private int id;
    private int user_id;
    private int fund_id;
    private float donation_price;
    private String comment;
    private Date date_donation;

    public static int actionTest = 0;

    private static int idHistory;
    private static String searchValue;
   

    public DonHistory() {
    }

    public DonHistory(int user_id, int fund_id, float donation_price, String comment, Date date_donation) 
    {
        this.user_id = user_id;
        this.fund_id = fund_id;
        this.donation_price = donation_price;
        this.comment = comment;
        this.date_donation = date_donation;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getFundId() {
        return fund_id;
    }

    public void setFundId(int fund_id) {
        this.fund_id = fund_id;
    }

    public float getDonationPrice() {
        return donation_price;
    }

    public void setDonationPrice(float donation_price) {
        this.donation_price = donation_price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateDonation() {
        return date_donation;
    }

    public void setDateDonation(Date date_donation) {
        this.date_donation = date_donation;
    }

     @Override
     public String toString() {
         return "DonHistory [user_id=" + user_id + ", fund_id=" + fund_id + ", donation_price=" + donation_price
                 + ", comment=" + comment + ", date_donation=" + date_donation + "]";
     }

    public void setId(int id) {
        this.id = id;
    }

    
    public static String getSearchValue() {
        return searchValue;
    }

    public static void setSearchValue(String searchValue) {
        DonHistory.searchValue = searchValue;
    }

    public static int getIdHistory() {
        return idHistory;
    }

    public static void setIdHistory(int idHistory) {
        DonHistory.idHistory = idHistory;
    }

    
}