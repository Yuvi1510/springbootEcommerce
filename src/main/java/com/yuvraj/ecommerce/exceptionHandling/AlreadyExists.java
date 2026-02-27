package com.yuvraj.ecommerce.exceptionHandling;

public class AlreadyExists extends Exception{
    private String entityType;
    private String fieldName;
    private String fieldValue;

    public AlreadyExists(String entityType, String fieldName, String fieldValue){
        super(entityType + " already exists with "+ fieldName + " " + fieldValue);
    }
}
