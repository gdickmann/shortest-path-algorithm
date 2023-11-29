import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int distance, Map<Node, Integer> adjacentNodes) {
        this.name = name;
        this.shortestDistanceFound = distance;
        this.neighbors = adjacentNodes;
    }

    private String name;
    private List<Node> shortestPath = new LinkedList<>();
    private int shortestDistanceFound = Integer.MAX_VALUE;
    private Map<Node, Integer> neighbors = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        neighbors.put(destination, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getShortestDistanceFound() {
        return shortestDistanceFound;
    }

    public void setShortestDistanceFound(Integer distance) {
        this.shortestDistanceFound = distance;
    }

    public Map<Node, Integer> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Map<Node, Integer> adjacentNodes) {
        this.neighbors = adjacentNodes;
    }
}
