package entities;

import java.util.Date;

public class Reviews {

  private int id;
  private int user_id;
  private int product_id;
  private String title;
  private String comment;
  private int value;
  private Date date_ajout;

  public Reviews(int id, int user_id, int product_id, String title, String comment, int value, Date date_ajout) {
    this.id = id;
    this.user_id = user_id;
    this.product_id = product_id;
    this.title = title;
    this.comment = comment;
    this.value = value;
    this.date_ajout = date_ajout;
  }

  public Reviews() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public Date getDate_ajout() {
    return date_ajout;
  }

  public void setDate_ajout(Date date_ajout) {
    this.date_ajout = date_ajout;
  }

  @Override
  public String toString() {
    return "Reviews [id=" + id + ", user_id=" + user_id + ", product_id=" + product_id + ", title=" + title
        + ", comment=" + comment + ", value=" + value + ", date_ajout=" + date_ajout + "]";
  }

}
