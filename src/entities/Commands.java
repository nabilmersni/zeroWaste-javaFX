package entities;

public class Commands {

  private int id;
  private int status;
  private int user_id;

  public Commands(int id, int status, int user_id) {
    this.id = id;
    this.status = status;
    this.user_id = user_id;
  }

  public Commands() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
  
  @Override
  public String toString() {
    return "Commands [id=" + id + ", status=" + status + ", user_id=" + user_id + "]";
  }
  
}
