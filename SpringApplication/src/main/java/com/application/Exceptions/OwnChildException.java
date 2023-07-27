package com.application.Exceptions;

public class OwnChildException extends ProcessingException {

        /**
        * constructor
        */
        public OwnChildException() {
            super("Ein Element kann nicht in sich selbst verschoben werden.");
        }
}
