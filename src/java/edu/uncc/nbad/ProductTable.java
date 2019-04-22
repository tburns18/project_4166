package edu.uncc.nbad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class ProductTable {
    
    static String url = "jdbc:mysql://localhost:3306/shop";
    static String user = "ProductTable";
    static String password = "password";
    
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
	
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    
    }
    
    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Attempted to connect to database");
            
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
    // Retrieves all records of products from the database and store them in a List data structure
    public static List<Product> selectProducts() {
	
        List<Product> productList = new ArrayList<Product>();
        
        try {
            String preparedSQL = "SELECT * FROM shop.products;";
            preparedStatement = connection.prepareStatement(preparedSQL);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Product product = new Product();
                product.setItemCode(resultSet.getString("code"));
                product.setItemDescription(resultSet.getString("description"));
                product.setItemPrice(resultSet.getDouble("price"));
                
                productList.add(product);
            }
            return productList;
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    // Retrieves the record of the product with a product code matching the provided one
    public static Product selectProduct(String productCode) {
        
        Product product = new Product();
        
        try {
            String preparedSQL = "SELECT code,description,price FROM shop.products WHERE code = ?;";
            preparedStatement = connection.prepareStatement(preparedSQL);
            preparedStatement.setString(1, productCode);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                product.setItemCode(resultSet.getString("code"));
                product.setItemDescription(resultSet.getString("description"));
                product.setItemPrice(resultSet.getDouble("price"));
            } else {
                product = null;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return product;
    }
    
    // Returns true if the database contains a product with a code matching the provided code
    public static boolean exists(String productCode) {
        
        Product product = selectProduct(productCode);
        
        return product != null;
    }    
    
    // Inserts a provided list of products into the database
    private static void saveProducts(List<Product> products) {
                
        for (Product product : products){
            insertProduct(product);
        }
    }
    
    // Inserts a record for the provided product into the database
    public static void insertProduct(Product product) {
        
        // If product already exists, return immediately
        if (exists(product.getItemCode())){
            return;
        } else {
            // Insert the product into the database
            try {
                String preparedSQL = "INSERT INTO shop.products (code,description,price) VALUES (?,?,?);";
                preparedStatement = connection.prepareStatement(preparedSQL);
                preparedStatement.setString(1, product.getItemCode());
                preparedStatement.setString(2, product.getItemDescription());
                preparedStatement.setDouble(3, product.getItemPrice());
                preparedStatement.executeUpdate();
            
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    // Updates an existing product in the database (based on product code) with the new information of the provided product object
    public static void updateProduct(Product product) {
        
        // If product does not exist, return immediately
        if (!exists(product.getItemCode())){
            return;
        } else {
            // Update the product in the database
            try {
                String preparedSQL = "UPDATE shop.products SET description = ?, price = ? WHERE code = ?;";
                preparedStatement = connection.prepareStatement(preparedSQL);
                preparedStatement.setString(1, product.getItemDescription());
                preparedStatement.setDouble(2, product.getItemPrice());
                preparedStatement.setString(3, product.getItemCode());
                preparedStatement.executeUpdate();
            
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    // Removes a provided product from the database
    public static void deleteProduct(Product product) throws IOException {
	
        // Check if product exists
        if (!exists(product.getItemCode())){
            return;
        }
        
        try {
            String preparedSQL = "DELETE FROM shop.products WHERE code = ?;";
            preparedStatement = connection.prepareStatement(preparedSQL);
            preparedStatement.setString(1, product.getItemCode());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static void main(String[] args) {
        ArrayList<Product> productList = (ArrayList<Product>) selectProducts();
    
        if(productList == null){
            System.out.println("Null");
            System.exit(0);
        }
    
        for (Product p : productList){
            System.out.println(p.getItemCode() + ", " + p.getItemDescription() + ", " + p.getPriceCurrencyFormat());
        }
    }
}
