import java.util.ArrayList;
import java.util.List;

public class Node {

    public Node(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Node(String name) {
        this.name = name;
    }

    public String name;
    public int value;

    public List<Node> nodes = new ArrayList<Node>();
}
