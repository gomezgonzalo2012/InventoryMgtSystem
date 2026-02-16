package com.gonzalodev.InventoryMgtSystem.exceptions;

public class NameValueRequiredException extends RuntimeException{
    public NameValueRequiredException(String message){
        super(message);
    }
}
