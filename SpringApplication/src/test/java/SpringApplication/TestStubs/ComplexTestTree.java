package SpringApplication.TestStubs;

import com.application.exceptions.ParseException;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Figure;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

public class ComplexTestTree extends TestTree{
    public int sectioningCounter = 0;
    public int figureCounter = 0;
    public int childCounter = 0;
    public int envCounter = 0;

    UUID[] uuids = {
            UUID.fromString("6ba18b12-10e1-4e0e-9a49-24ca4b4e1bc2"),
            UUID.fromString("4d6e8d14-6a47-437f-ba79-8ebec503b5ea"),
            UUID.fromString("4e8a9e7c-56ac-43f5-9f17-02a32c52c7f3"),
            UUID.fromString("ac34528f-dc42-4f16-9b1d-ec8426b601a5"),
            UUID.fromString("0c0e94a7-2d98-4a17-b1b5-6f6c548ebca5"),
            UUID.fromString("9e304a6f-043e-41c7-bd1e-6f47ee1e3342"),
            UUID.fromString("ecdb5c4d-e46d-4d52-9f5b-82bc6c372972"),
            UUID.fromString("90a11c54-bf0e-4c24-9475-39a4b13472f2"),
            UUID.fromString("f7f86717-45b2-42ac-b918-16e97c87c8db"),
            UUID.fromString("adf6470b-8284-477b-93bf-875d7e8c21c1"),
            UUID.fromString("2e10284f-81f6-4b8b-8f5b-16d5f3ef35d8"),
            UUID.fromString("e7b57934-9ce5-4d2e-a28f-4e8fcf7ff2f2"),
            UUID.fromString("14e9e203-8030-473f-9fc9-86f1e1c41e6c"),
            UUID.fromString("c7f2828b-6b21-4f3b-8e10-649b8821ac1f"),
            UUID.fromString("7d53db89-d524-43db-a08b-43b89a50e7e3"),
            UUID.fromString("f48f5b11-d9f1-4212-8e3c-e2da1e1ac1e1"),
            UUID.fromString("eb762c2f-75c6-4db0-8b15-5cd009cdd914"),
            UUID.fromString("4f9c8e39-7e4e-4f12-9d68-8323dfc358d0"),
            UUID.fromString("1bbd5d6b-5e07-4fbf-9925-59f01e34ebc6"),
            UUID.fromString("b12c6762-ff08-4171-84d7-2872e9b86d4e"),
            UUID.fromString("6f380542-33e0-41be-8891-3fc454e6e90c"),
            UUID.fromString("185d9d7b-2b06-44c2-aa12-0b6032db1299"),
            UUID.fromString("cd57b438-4181-401e-9e07-2e53ff6a0ae7"),
            UUID.fromString("1fcf04f3-7320-4660-bbe4-487719db444d"),
            UUID.fromString("a288156d-0a25-4e4e-9d15-51b631c209f8"),
            UUID.fromString("d7022d33-87c2-4e96-8cfb-e4f24b2cb51a"),
            UUID.fromString("08c18f33-d0fc-4d0a-bd90-8cf165d7199a"),
            UUID.fromString("c810cb9f-7e20-42b6-92da-8a1dd9e036e5"),
            UUID.fromString("1d2ff48a-1a2e-478f-9b91-6c3c161e9773"),
            UUID.fromString("57a80b1b-afed-4a2e-8b6f-129e9e019b3b"),
            UUID.fromString("8c6aaf99-d8b0-4f07-bdd1-80e3c0d231ea"),
            UUID.fromString("46c97f7f-2e75-4cb0-99c9-60ef7c746c7f"),
            UUID.fromString("ac7b687c-8254-4a1b-aec3-9e4c42b35484"),
            UUID.fromString("93ecb078-6f1f-4fca-9d48-38daa6e0a91b"),
            UUID.fromString("00786788-3474-41f2-8ae5-8f65d9778e89"),
            UUID.fromString("59c74d5f-0025-4a3d-84ea-8a931d6f2d3c"),
            UUID.fromString("4e1566c5-69f7-4d32-95d3-1a5e8da29d60"),
            UUID.fromString("1f5ea6a1-df7b-442f-9b6d-446cfa2873d8"),
            UUID.fromString("7ef255df-4f6d-43ea-a172-2b40b2081069"),
            UUID.fromString("3490ad9d-6a31-4f6e-9c54-2e2e0837851e"),
            UUID.fromString("a92358fb-783e-4ef5-a301-8b4f111db15f"),
            UUID.fromString("cc6281da-1810-4a24-b1b8-42a86ea92581"),
            UUID.fromString("daae7e7d-3f9d-4bf4-8c12-d58ec2a0f7d8"),
            UUID.fromString("4e7cb980-2072-40f6-a74f-8d932f1542a0"),
            UUID.fromString("aa1eeb0b-d60e-43cb-9f69-2a1e755e80ef"),
            UUID.fromString("c64e46a3-5b3d-45b5-93e5-4df0dfe5a3ea"),
            UUID.fromString("c123a268-93e2-4ea9-b3eb-5b92236b7417"),
            UUID.fromString("77a0e52e-9e0c-41d0-8dc3-4d4803b0fb4c")};
    public int uuidCounter = 0;

    public ComplexTestTree() {
        super();
        this.uuidCounter = 0;
        this.sectioningCounter = 0;
        this.figureCounter = 0;
        this.childCounter = 0;
        this.envCounter = 0;
        this.root.addStartHeader(new ArrayList<String>() {
            {
                add("\\documentclass[12pt]{article}");
                add("\\usepackage{amsmath}");
                add("\\begin{document}"); }
        });

        //create sectioning
        Sectioning sectioning1 = this.createSectioning(null);
        Sectioning sectioning2 = this.createSectioning(null);

        Sectioning sectioning3 = this.createSectioning(sectioning1);
        Sectioning sectioning4 = this.createSectioning(sectioning1);

        Sectioning sectioning5 = this.createSectioning(sectioning2);
        Sectioning sectioning6 = this.createSectioning(sectioning2);

        //create environment
        Environment environment1 = this.createEnvironment(sectioning1);
        Environment environment2 = this.createEnvironment(sectioning3);

        //create figure
        Figure figure1 = this.createFigure(sectioning1);
        Figure figure2 = this.createFigure(sectioning3);

        //create children
        Child child1 = this.createChild(sectioning1);
        Child child2 = this.createChild(sectioning2);
        Child child3 = this.createChild(sectioning2);
        Child label4 = this.createLabel(sectioning3);
        Child label5 = this.createLabel(sectioning5);

        //create blockelement
        BlockElement blockElement1 = this.createBlockelement(sectioning1);
        BlockElement blockElement2 = this.createBlockelement(sectioning3);
        BlockElement blockElement3 = this.createBlockelement(sectioning5);
        BlockElement blockElement4 = this.createBlockelement(sectioning6);

        //create inline equation
        BlockElement inlineEquation1 = this.createInlineEquation(sectioning5);

        this.root.setMinLevel(1);
    }

    public Sectioning createSectioning(Parent parent) {
        Sectioning sectioning = new Sectioning("sectioning", 0);
        sectioning.setId(uuids[uuidCounter]);
        uuidCounter++;

        if(parent != null){
            parent.addChild(sectioning);
        } else {
            root.addChild(sectioning);
        }

        sectioning.setParent(parent);

        sectioning.extractContent("\\section*{"+"sec"+ sectioningCounter+ "}");
        sectioningCounter++;

        sectioning.setComment("%comment1");
        sectioning.setSummary("summaryText");
        sectioning.setChooseManualSummary(true);

        sectioningList.add(sectioning);
        return sectioning;
    }

    public Figure createFigure(Parent parent) {
        Figure figure = new Figure("figure", "figure",0);
        figure.setId(uuids[uuidCounter]);
        uuidCounter++;
        if(parent != null){
            parent.addChild(figure);
        } else {
            root.addChild(figure);
        }

        figure.setParent(parent);

        figure.extractContent("\\begin{figure}");
        figureCounter++;

        figure.setComment("%comment1");
        figure.setSummary("summaryText");
        figure.setChooseManualSummary(true);

        figure.addCaption("caption1");
        figure.addCaption("caption2");

        environmentList.add(figure);
        return figure;
    }

    public Environment createEnvironment(Parent parent) {
        Environment environment = new Environment("unknown", "\\begin", "\\end", 0);
        environment.setId(uuids[uuidCounter]);
        uuidCounter++;

        if(parent != null){
            parent.addChild(environment);
        } else {
            root.addChild(environment);
        }

        environment.setParent(parent);

        environment.extractContent("\\begin{environment"+ envCounter +"}");
        envCounter++;

        environment.setComment("%comment1");
        environment.setSummary("summaryText");
        environment.setChooseManualSummary(true);

        environmentList.add(environment);
        return environment;
    }

    public Child createChild(Parent parent) {
        Child child = new Child("child", null, 0);
        child.setId(uuids[uuidCounter]);
        uuidCounter++;

        parent.addChild(child);
        child.setParent(parent);

        child.extractContent("\\label{child"+ childCounter +"}");
        childCounter++;

        child.setComment("%comment1");
        child.setSummary("summaryText");
        child.setChooseManualSummary(true);

        childrenList.add(child);
        return child;
    }

    public Child createLabel(Parent parent) {
        Child child = new Child("\\label", null, 0);
        child.setId(uuids[uuidCounter]);
        uuidCounter++;

        parent.addChild(child);
        child.setParent(parent);

        child.extractContent("\\label{label"+ childCounter +"}");
        childCounter++;

        child.setComment("%comment1");
        child.setSummary("summaryText");
        child.setChooseManualSummary(true);

        childrenList.add(child);
        return child;
    }

    public BlockElement createBlockelement(Parent parent) {
        BlockElement blockElement = new BlockElement();
        blockElement.setId(uuids[uuidCounter]);
        uuidCounter++;

        parent.addChild(blockElement);
        blockElement.setParent(parent);

        blockElement.addTextBlockToElem("blockElement blablablablalbslvgbjwegbvqeujigjdieuweda"+ childCounter);
        blockElement.addTextBlockToElem("blockElement blablablablalbslvgbjwegbvqeujigjdieuweda"+ childCounter);
        blockElement.addTextBlockToElem("blockElement blablablablalbslvgbjwegbvqeujigjdieuweda"+ childCounter);
        blockElement.addTextBlockToElem("blockElement blablablablalbslvgbjwegbvqeujigjdieuweda"+ childCounter);
        childCounter++;

        blockElement.setComment("%comment1");
        blockElement.setSummary("summaryText");
        blockElement.setChooseManualSummary(true);

        childrenList.add(blockElement);
        return blockElement;
    }

    public BlockElement createInlineEquation(Parent parent) {
        BlockElement blockElement = new BlockElement();
        blockElement.setId(uuids[uuidCounter]);
        uuidCounter++;

        parent.addChild(blockElement);
        blockElement.setParent(parent);

        blockElement.addTextBlockToElem("inlineEquation $\sqrt{1+2}$");
        childCounter++;

        blockElement.setComment("%comment1");
        blockElement.setSummary("summaryText");
        blockElement.setChooseManualSummary(true);
        childrenList.add(blockElement);
        return blockElement;
    }
}
