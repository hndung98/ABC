package com.example.myfirstapplication;

public class User {
    private String userName = null;
    private String password = null;
    private String email = null;
    private String userType = null;
    private String name = null;
    public User(){

    }
    public User(String name, String userName, String password, String email, String userType){
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public String getName(){return this.name;}
    public void setName(String name){ this.name = name;}
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getUserType(){ return  userType;}
    public void setUserType() {this.userType = userType;}
}
