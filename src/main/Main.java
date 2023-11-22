import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            List<String> response = scan("C:\\Users\\Biscoitinho\\Documents\\paths.txt");
            Graph graph = ToGraph(response);

            List<Node> listGraph = new ArrayList<Node>(graph.getNodes());
            calculateShortestPathFromSource(graph, listGraph.get(0));
        } catch (Exception e) {
            throw e;
        }
    }

    private static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (Entry<Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {

                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            
            settledNodes.add(currentNode);
        }

        return graph;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();

            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();

        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }

    }

    private static Graph ToGraph(List<String> nodes) {
        Graph graph = new Graph();

        for (String x : nodes) {
            String[] values = x.split(";");

            String node = values[0];
            String destination = values[1];
            int distance = Integer.parseInt(values[2]);

            Node n = new Node(node);
            n.addDestination(new Node(destination), distance);

            graph.addNode(n);
        }

        return graph;
    }

    private static List<String> scan(String path) throws IOException {
        File file = new File(path);
        Scanner input = new Scanner(file);

        List<String> response = new ArrayList<String>();

        while (input.hasNext()) {
            String word = input.next();
            response.add(word);
        }

        input.close();
        return response;
    }
}