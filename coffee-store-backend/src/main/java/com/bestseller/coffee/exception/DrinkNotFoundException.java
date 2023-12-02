package com.bestseller.coffee.exception;

public class DrinkNotFoundException extends RuntimeException{
    public DrinkNotFoundException(String message){
        super(message);
    }
}
