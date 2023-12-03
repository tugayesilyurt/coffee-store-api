package com.bestseller.coffee.exception;

public class ToppingNotFoundException extends RuntimeException{
    public ToppingNotFoundException(String message){
        super(message);
    }
}
