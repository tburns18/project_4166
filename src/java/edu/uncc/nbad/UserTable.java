package edu.uncc.nbad;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tyler
 */


import java.io.*;
import java.util.*;
import java.sql.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserTable {
    
    static String url = "jdbc:mysql://localhost:3306/shop";
    static String user = "UserTable";
    static String password = "password";
    
    
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultset = null;
	
	//Static initializer, it runs when the class is intialized (it is executed once)
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
    static {
        try {
            connection = DriverManager.getConnection (url, user, password);
            System.out.println("Attempted to connect to database");
        }
        catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
    }
    
    public static void addRecord(User user) throws IOException {
        try {
            String preparedSQL = "INSERT INTO shop.users (firstName,lastName,email,password) VALUES (?,?,?,?);";
            preparedStatement = connection.prepareStatement(preparedSQL);
            preparedStatement.setString(1, user.getfName());
            preparedStatement.setString(2, user.getlName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }

    }

    public static User getUser(String emailAddress) throws IOException {
        User user = new User();
        boolean sqlWork = false;
        try{
            String preparedSQL = "SELECT firstName,lastName,email,password FROM shop.users WHERE email = ?;";
            preparedStatement = connection.prepareStatement(preparedSQL);
            preparedStatement.setString(1, emailAddress);
            resultset = preparedStatement.executeQuery();
            
            while (resultset.next()) {
                user.setfName(resultset.getString("firstName"));
                user.setlName(resultset.getString("lastName"));
                user.setEmail(resultset.getString("email"));
                user.setPassword(resultset.getString("password"));
            }
            sqlWork = true;
        }
        catch (SQLException e) {
            for(Throwable t: e)
                t.printStackTrace();
        }
        if (!sqlWork) {
            user = null;
        }
        return user;

    }

    public static ArrayList<User> getUsers() throws IOException {
        String SQL = "SELECT * FROM  'users'";
        ArrayList<User> users = new ArrayList<User>();
        try{
            preparedStatement = connection.prepareStatement(SQL);
            resultset = preparedStatement.executeQuery();
            
            while(resultset.next()){
                User u = new User();
                u.setEmail(resultset.getString("email"));
                u.setfName(resultset.getString("firstName"));
                u.setlName(resultset.getString("lastName"));
                u.setPassword(resultset.getString("password"));
                users.add(u);
            }
            
        }catch (SQLException e) {
            System.out.println("Exception thrown: " + e);
        }
        return users;
    }

    public static HashMap<String, User> getUsersMap() throws IOException {
		throw new NotImplementedException(); // remove this line and implement the logic
    }
}
