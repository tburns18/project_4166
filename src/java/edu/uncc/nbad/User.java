/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;
import java.io.Serializable;


/**
 *
 * @author tyler
 */
public class User implements Serializable{
    
    private String fName;
    private String lName;
    private String email;
    private String username;
    private String password;
    
    public User(){
        fName = "";
        lName = "";
        email = "";
        username = "";
        password = "";
        
    }
    public User(String fName, String lName, String email, String username, String password){
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public String getfName(){
        return fName;
    }
    public void setfName(String fName){
        this.fName = fName;
    }
    public String getlName(){
        return lName;
    }
    public void setlName
        (String lName){
        this.lName = lName;
    }
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email  = email;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username  = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
