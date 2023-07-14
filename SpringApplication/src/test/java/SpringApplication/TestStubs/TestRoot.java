package SpringApplication.TestStubs;

import com.Application.Tree.Element;
import com.Application.Tree.elements.Root;
import org.mockito.Mockito;

import java.util.UUID;

public class TestRoot extends Root {

    public Element searchForID(UUID id, int level) {
        return Mockito.mock(Element.class);
    }
}

