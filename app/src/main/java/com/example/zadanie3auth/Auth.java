package com.example.zadanie3auth;

import java.security.PublicKey;

public class Auth {
        public int Id;
        public String Username;
        public boolean isAdmin;
        private String Password;

    public Auth(int id, String user, String passwd, Boolean admin){
        Id = id;
        Username = user;
        Password = "abc"+passwd+"def";
        isAdmin = admin;
    }

    public boolean checkPass(String user, String pass){
        String passSalt = "abc"+pass+"def";
        if(user.equals(Username) && passSalt.equals(Password)) return true;
        else return false;
    }
}

