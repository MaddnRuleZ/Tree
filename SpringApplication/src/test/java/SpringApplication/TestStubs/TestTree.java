package SpringApplication.TestStubs;

import com.Application.Tree.elements.Child;
import com.Application.Tree.elements.Root;

public class TestTree {

    public Root createTree() {
        Root root = new Root();
        root.setContent("root");



        return root;
    }
}
