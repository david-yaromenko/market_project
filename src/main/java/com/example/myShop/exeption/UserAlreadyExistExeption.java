package com.example.myShop.exeption;

public class UserAlreadyExistExeption extends RuntimeException{
    public UserAlreadyExistExeption(String message){
        super(message);
    }
}
