package com.example.zadanie3auth;

import java.security.PublicKey;

public class Auth {
        public int Id;
        public String Username;
        public int Range;
        private String Password;
        public String Name;

    public Auth(int id, String user, String passwd, int range, String name){
        Id = id;
        Username = user;
        Password = "abc"+passwd+"def";
        Range = range;
        Name = name;
    }

    public boolean checkPass(String user, String pass){
        String passSalt = "abc"+pass+"def";
        if(user.equals(Username) && passSalt.equals(Password)) return true;
        else return false;
    }
}

