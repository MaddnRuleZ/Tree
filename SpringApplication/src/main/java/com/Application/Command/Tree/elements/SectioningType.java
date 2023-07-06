package elements;

import javax.swing.text.Element;

public enum SectioningType {

    PART("\\section") {

    },


    CHAPTER("\\chapter") {

    },


    SECTION("\\section") {

    },


    PARAGRAPH("\\paragraph") {

    };


    private String startPart;

    SectioningType(final String startPart) {
        this.startPart = startPart;
        // set the Level

    }





}
