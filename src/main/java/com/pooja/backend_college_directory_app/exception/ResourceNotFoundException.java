package com.pooja.backend_college_directory_app.exception;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String s){
        super(s);
    }
}
