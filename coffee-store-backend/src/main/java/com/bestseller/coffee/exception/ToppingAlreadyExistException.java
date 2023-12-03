package com.bestseller.coffee.exception;

public class ToppingAlreadyExistException extends RuntimeException{
    public ToppingAlreadyExistException(String message){
        super(message);
    }
}
