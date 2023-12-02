package com.bestseller.coffee.exception;

public class DrinkAlreadyExistException extends RuntimeException{
    public DrinkAlreadyExistException(String message){
        super(message);
    }
}
