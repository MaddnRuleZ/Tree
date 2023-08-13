package SpringApplication.TestStubs;

import com.application.exceptions.ParseException;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Root;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Class to create a tree for testing purposes
 * Used in DeleteTest
 * Don't change
 */
public class TestTree {
    public final UUID notUsedUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public Root root;
    public ArrayList<Child> childrenList = new ArrayList<>();
    public ArrayList<Sectioning> sectioningList = new ArrayList<>();
    public ArrayList<Environment> environmentList = new ArrayList<>();

    // tree structure:
    //                        root
    //                          |
    //      --------------Sectioning1------------------------
    //               /                    |    |    \         \
    //    -------Sec2-----------        Sec3   Sec4   Child1   Sec7
    //   /  |     |       |    \          |      |              |
    // Sec5 Env1 Child2 Child3 Child4   Child5  Env2           Sec8
    //        |                                                 |
    //       Sec6                                              Sec9
    //                                                          |
    //                                                         Sec10
    //                                                          |
    //                                                         Sec11
    //                                                          |
    //                                                         Sec12

    TestTree() {
        this.root = Root.resetInstance();
    }


    /**
     * creates a tree for testing purposes
     * @return TestTree
     * @throws ParseException
     */
    public static TestTree createTestTree() throws ParseException {
        TestTree testTree = new TestTree();

        //create sectioning
        Sectioning sectioning1 = new Sectioning("sectioning1", 0);

        Sectioning sectioning2 = new Sectioning("sectioning2",1);
        Sectioning sectioning3 = new Sectioning("sectioning3",1);
        Sectioning sectioning4 = new Sectioning("sectioning4",1);

        Sectioning sectioning5 = new Sectioning("sectioning5",2);
        Sectioning sectioning6 = new Sectioning("sectioning6",2);
        Sectioning sectioning7 = new Sectioning("sectioning7",2);

        Sectioning sectioning8 = new Sectioning("sectioning8",3);
        Sectioning sectioning9 = new Sectioning("sectioning9",4);
        Sectioning sectioning10 = new Sectioning("sectioning10",5);
        Sectioning sectioning11 = new Sectioning("sectioning11",6);
        Sectioning sectioning12 = new Sectioning("sectioning12",7);

        //create environment
        Environment environment1 = new Environment("environment1", "environment1",2);
        Environment environment2 = new Environment("environment2", "environment2",2);


        //create children
        Child child1 = new Child("child1", "child1",1);

        Child child2 = new Child("child2", "child2",2);
        Child child3 = new Child("child3", "child3",2);
        Child child4 = new Child("child4", "child4",2);

        Child child5 = new Child("child5", "child5",2);



        //set children
        sectioning1.addChild(sectioning2);
        sectioning1.addChild(sectioning3);
        sectioning1.addChild(sectioning4);
        sectioning1.addChild(sectioning7);

        sectioning2.addChild(sectioning5);
        sectioning2.addChild(environment1);

        sectioning1.addChild(child1);
        sectioning2.addChild(child2);
        sectioning2.addChild(child3);
        sectioning2.addChild(child4);
        sectioning3.addChild(child5);

        sectioning4.addChild(environment2);

        environment1.addChild(sectioning6);

        sectioning7.addChild(sectioning8);
        sectioning8.addChild(sectioning9);
        sectioning9.addChild(sectioning10);
        sectioning10.addChild(sectioning11);
        sectioning11.addChild(sectioning12);


        //set parents
        sectioning2.setParent(sectioning1);
        sectioning3.setParent(sectioning1);
        sectioning4.setParent(sectioning1);
        sectioning5.setParent(sectioning2);
        sectioning6.setParent(environment1);
        sectioning7.setParent(sectioning1);

        environment1.setParent(sectioning2);
        environment2.setParent(sectioning4);

        child1.setParent(sectioning1);
        child2.setParent(sectioning2);
        child3.setParent(sectioning2);
        child4.setParent(sectioning2);
        child5.setParent(sectioning3);

        sectioning8.setParent(sectioning7);
        sectioning9.setParent(sectioning8);
        sectioning10.setParent(sectioning9);
        sectioning11.setParent(sectioning10);
        sectioning12.setParent(sectioning11);

        sectioning1.setParent(null);




        //add Elements to TestTree
        testTree.root.addChild(sectioning1);

        testTree.childrenList.add(child1);
        testTree.childrenList.add(child2);
        testTree.childrenList.add(child3);
        testTree.childrenList.add(child4);
        testTree.childrenList.add(child5);

        testTree.sectioningList.add(sectioning1);
        testTree.sectioningList.add(sectioning2);
        testTree.sectioningList.add(sectioning3);
        testTree.sectioningList.add(sectioning4);
        testTree.sectioningList.add(sectioning5);
        testTree.sectioningList.add(sectioning6);
        testTree.sectioningList.add(sectioning7);
        testTree.sectioningList.add(sectioning8);
        testTree.sectioningList.add(sectioning9);
        testTree.sectioningList.add(sectioning10);
        testTree.sectioningList.add(sectioning11);
        testTree.sectioningList.add(sectioning12);

        testTree.environmentList.add(environment1);

        //set content
        child1.setContentManually("child1");
        child2.setContentManually("child2");
        child3.setContentManually("child3");
        child4.setContentManually("child4");
        child5.setContentManually("child5");

        sectioning1.setContentManually("sectioning1");
        sectioning2.setContentManually("sectioning2");
        sectioning3.setContentManually("sectioning3");
        sectioning4.setContentManually("sectioning4");
        sectioning5.setContentManually("sectioning5");
        sectioning6.setContentManually("sectioning6");

        environment1.setContentManually("environment1");
        environment2.setContentManually("environment2");
        return testTree;
    }




}
