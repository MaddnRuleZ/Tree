package com.application.exceptions;

public class OwnChildException extends ProcessingException {

        /**
        * constructor
        */
        public OwnChildException() {
            super("Ein Element kann nicht in sich selbst verschoben werden.");
        }
}
