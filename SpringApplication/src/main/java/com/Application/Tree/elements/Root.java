package com.Application.Tree.elements;

import com.Application.Tree.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Root extends Parent {
    public String[] startText;

    public Root() {
        super("", null, 0,10000);
    }

    @Override
    public boolean validateIndicTextGeneration() {
        return false;
    }

    public void addStartText(String[] startText) {
        this.startText = startText;
    }

    @Override
    public String[] toText() {
        List<String> arrayList = new ArrayList<>();
        String[] curr = this.getText();
        arrayList.addAll(Arrays.stream(this.startText).toList());

        if (curr != null) {
            arrayList.add(this.getOptions());

            if (this.comment.getContent() != null) {
                arrayList.addAll(this.comment.getContent());
            }

            if (this.summary.getContent() != null) {
                arrayList.addAll(this.summary.getContent());
            }

            if (textBlock.getTextParts() != null) {
                for (List<String> list: textBlock.getTextParts()) {
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                }
            }
        }

        for (Element element: childElements) {
            arrayList.addAll(Arrays.asList(element.toText()));
        }
        return arrayList.toArray(new String[0]);
    }


    public String[] getStartText() {
        return this.startText;
    }

    @Override
    public Element searchForID(UUID id) {
        //TODO
        return null;
    }

}
