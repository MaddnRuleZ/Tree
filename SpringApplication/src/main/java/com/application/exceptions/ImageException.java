package com.application.exceptions;

public class ImageException extends ProcessingException{
    public ImageException(String path) {
        super("Die Bilddatei konnte nicht gefunden werden. Dateipfad: " + path);
    }
}
