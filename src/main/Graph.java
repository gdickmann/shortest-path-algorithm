import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public List<Node> getNodesAsArrayList() {
        return new ArrayList<Node>(nodes);
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
