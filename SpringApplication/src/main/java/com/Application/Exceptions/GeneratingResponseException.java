package com.Application.Exceptions;

public class GeneratingResponseException extends ProcessingException{

    public GeneratingResponseException() {
        super("Error while generating response");
    }
}
