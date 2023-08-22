package com.example.Library_Management_System.CustomException;

public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String message){
        super(message);
    }
}
