package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Input extends Parent {

    /**
     * ACHTUNG, FUNKTIONIERT NOCH NICHT !
     *
     */

    public Input() {
        super(null, null, 0, 0);

    }

    public String[] startText;

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
        return null;
    }
}
