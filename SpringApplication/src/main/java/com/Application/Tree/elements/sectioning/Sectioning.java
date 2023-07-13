package com.Application.Tree.elements.sectioning;

import com.Application.Tree.Element;
import com.Application.Tree.elements.Parent;

import java.util.List;
import java.util.UUID;

public class Sectioning extends Parent {
    //Bei LaTeX zu struct Abgleich mit diesen Strings alle werden zu Sectioning
    public List<String> types = SectioningTypes.getList();

    public Sectioning(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
    }


    // durchläuft Baum und berechnet type nur für struct -> LaTeX
    // Zählen der passierten Sectionings
    // Anzahl der passierten Sectionings wird in Sectioning types mit level verglichen und der dazugehörige type zugewiesen
    // wenn Sectioning -> Zähler erhöhen
    // auf Weg können passierte Sections berechnet werden
    public void getType(int level) {
        // TODO
    }

    @Override
    public Element searchForID(UUID id, int level) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id, level + 1);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }
}
