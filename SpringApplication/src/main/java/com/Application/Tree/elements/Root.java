package com.Application.Tree.elements;

public class Root extends Parent {

    // err w/ coldstart by now
    public static final String startPart = "";
    public static final String endPart = null;
    public Root() {
        super(startPart, endPart, 0);
    }



    // Singleton-Pattern !!!!
    public static Root getInstance() {
        if(this.instance == null) {
            this.instance = new Root();
        }
        return this.instance;
    }

}
