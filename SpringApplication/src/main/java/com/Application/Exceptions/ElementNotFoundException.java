package com.Application.Exceptions;

public class ElementNotFoundException extends ProcessingException{
    public ElementNotFoundException(String element) {
        super("Element not found" + element);
    }
}
