package services;

import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.MyDB;

public class UserService implements IUserService {

    public Connection conx;
    public Statement stm;

    public UserService() {
        conx = MyDB.getInstance().getConx();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO `user`(`full_name`, `email`, `tel`, `token`,`img_url`, `roles`, `password`, `point`) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, user.getFullname());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getTel());
        ps.setString(4, user.getToken());
        ps.setString(5, user.getImgUrl());
        ps.setString(6, user.getRoles());
        ps.setString(7, user.getPassword());
        ps.setInt(8, user.getPoint());
        ps.executeUpdate();
        System.out.println("User added successfully");
        ps.close();
    }

    public User getOneUser(String email) throws SQLException {
        String req = "SELECT * FROM `user` where email = ?";
        PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        User user = new User();
        user.setId(-999);

        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setFullname(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setTel(rs.getString("tel"));
            user.setToken(rs.getString("token"));
            user.setIsVerified(rs.getBoolean("is_verified"));
            user.setState(rs.getBoolean("state"));
            user.setDescription(rs.getString("description"));
            user.setFbLink(rs.getString("fb_link"));
            user.setTwitterLink(rs.getString("twitter_link"));
            user.setInstaLink(rs.getString("insta_link"));
            user.setImgUrl(rs.getString("img_url"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
            user.setPoint(rs.getInt("point"));
        }
        ps.close();
        return user;
    }

}
