package SpringApplication.TestStubs;

import com.Application.Tree.Element;
import com.Application.Tree.elements.*;

import java.util.ArrayList;


/**
 * Class to create a tree for testing purposes
 * Used in DeleteTest
 * Don't change
 */
public class TestTree {
    public Root root;
    public ArrayList<Child> childrenList = new ArrayList<>();
    public ArrayList<Parent> sectioningList = new ArrayList<>();
    public ArrayList<Environment> environmentList = new ArrayList<>();

    // Tree structure:
    //                        root
    //                          |
    //      --------------Sectioning1---------------
    //               /                    |    |    \
    //    -------Sec2-----------        Sec3   Sec4   Child1
    //   /  |     |       |    \          |      |
    // Sec5 Env1 Child2 Child3 Child4   Child5  Env2
    //        |
    //       Sec6

    //TODO sectioning 1 noch nicht mit Root verbunden
    public TestTree() {
        this.root = Root.getInstance();

        //create sectioning
        Sectioning sectioning1 = new Sectioning("sectioning1", 0, 0);

        Sectioning sectioning2 = new Sectioning("sectioning2", 0, 1);
        Sectioning sectioning3 = new Sectioning("sectioning3", 0, 1);
        Sectioning sectioning4 = new Sectioning("sectioning4", 0, 1);

        Sectioning sectioning5 = new Sectioning("sectioning5", 0, 2);
        Sectioning sectioning6 = new Sectioning("sectioning6", 0, 2);

        Environment environment1 = new Environment("environment1", "environment1", 0, 2);
        Environment environment2 = new Environment("environment2", "environment2", 0, 2);

        //create children
        Child child1 = new Child("child1", "child1", 0, 1);

        Child child2 = new Child("child2", "child2", 0, 2);
        Child child3 = new Child("child3", "child3", 0, 2);
        Child child4 = new Child("child4", "child4", 0, 2);

        Child child5 = new Child("child5", "child5", 0, 2);

        //set children
        sectioning1.addChild(sectioning2);
        sectioning1.addChild(sectioning3);
        sectioning1.addChild(sectioning4);

        sectioning2.addChild(sectioning5);
        sectioning2.addChild(environment1);

        sectioning1.addChild(child1);
        sectioning2.addChild(child2);
        sectioning2.addChild(child3);
        sectioning2.addChild(child4);
        sectioning3.addChild(child5);

        sectioning4.addChild(environment2);

        environment1.addChild(sectioning6);

        //set parents
        sectioning2.setParent(sectioning1);
        sectioning3.setParent(sectioning1);
        sectioning4.setParent(sectioning1);
        sectioning5.setParent(sectioning2);
        sectioning6.setParent(environment1);

        environment1.setParent(sectioning2);
        environment2.setParent(sectioning4);

        child1.setParent(sectioning1);
        child2.setParent(sectioning2);
        child3.setParent(sectioning2);
        child4.setParent(sectioning2);
        child5.setParent(sectioning3);


        //add Elements to TestTree
        root.addChild(sectioning1);

        childrenList.add(child1);
        childrenList.add(child2);
        childrenList.add(child3);
        childrenList.add(child4);
        childrenList.add(child5);

        sectioningList.add(sectioning1);
        sectioningList.add(sectioning2);
        sectioningList.add(sectioning3);
        sectioningList.add(sectioning4);
        sectioningList.add(sectioning5);
        sectioningList.add(sectioning6);

        environmentList.add(environment1);
    }

}
