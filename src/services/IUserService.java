package services;

import java.sql.SQLException;
import entities.User;

public interface IUserService {

    public void ajouter(User user) throws SQLException;

    public User getOneUser(String email) throws SQLException;

    public User getUserById(int email) throws SQLException;
}
