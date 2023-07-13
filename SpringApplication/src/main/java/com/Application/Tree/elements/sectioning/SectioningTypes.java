package com.Application.Tree.elements.sectioning;

import java.util.ArrayList;
import java.util.List;

public enum SectioningTypes {
    SECTION(3,"\section");
    //etc
    int level;
    String startPart;

    SectioningTypes(int level, String startPart) {
        this.level = level;
        this.startPart = startPart;
    }

    public static  List<String> getList() {
        List<String> list = new ArrayList<>();
        for (SectioningTypes type : SectioningTypes.values()) {
            list.add(type.startPart);
        }
        return list;
    }

}
