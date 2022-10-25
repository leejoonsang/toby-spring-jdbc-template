package com.exercise.dao;

import com.exercise.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(){
        this.connectionMaker = new AWSConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO users(id, name, password) values (?, ?, ?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    public User findById(String id) throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "select * from users where id=?");
        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();
        User user = null;
        if(rs.next()){
            user = new User(rs.getString("id"),
                    rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        if(user == null){ throw new EmptyResultDataAccessException(1); }

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            conn = connectionMaker.makeConnection();
            pstmt = conn.prepareStatement("delete from users");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException e){
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.makeConnection();
            pstmt = conn.prepareStatement("select count(*) from users");
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException e){
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.add(new User("20", "mark20", "twenty"));
    }

}

