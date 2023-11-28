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

    private static final int STARTING_NODE = 0;

    public static void main(String[] args) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Defina a origem e o destino no formato 'X X': ");
            String startAndEnd = scanner.nextLine();
            
            scanner.close();
            
            List<String> nodes = scan("C:\\Users\\Biscoitinho\\Documents\\paths.txt");
            Graph graph = ToGraph(nodes);
            
            Graph shortestPathsBetweenNodes = calculateShortestPath(graph, graph.getNodesAsArrayList().get(STARTING_NODE));
            List<Node> shortestPath = shortestBetween(startAndEnd, shortestPathsBetweenNodes);
            
            System.out.println("A menor distância entre o ponto " + startAndEnd.charAt(0) + " e " + startAndEnd.charAt(2) + " é:");

            for (Node i : shortestPath) {
                System.out.println(i.getName());
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    private static List<Node> shortestBetween(String startAndEnd, Graph shortestPathsBetweenNodes) {
        char start = startAndEnd.charAt(0);
        char end = startAndEnd.charAt(2);

        for (Node i : shortestPathsBetweenNodes.getNodes()) {
            if (i.getName().equals(start)) {

            }
        }
        return null;
    }

    /**
     * @param graph O {@link Graph} a ser calculado.
     * @param source O nó de origem.
     * @return {@link Graph} contendo o menor caminho de todo nó para todos os nós possíveis.
     */
    private static Graph calculateShortestPath(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);
        
        while (unsettledNodes.size() != 0) {
            
            Node node = traverseToLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(node);
            
            for (Entry<Node, Integer> adjacentNodes: node.getAdjacentNodes().entrySet()) {
                
                Node adjacentNode = adjacentNodes.getKey();
                Integer weight = adjacentNodes.getValue();
                
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, weight, node);
                    unsettledNodes.add(adjacentNode);
                }
            }
            
            settledNodes.add(node);
        }
        
        return graph;
    }
    
    private static Node traverseToLowestDistanceNode(Set<Node> unsettledNodes) {
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
    
    private static void calculateMinimumDistance(Node adjacentNode, Integer weight, Node node) {
        Integer distance = node.getDistance();
        
        if (distance + weight < adjacentNode.getDistance()) {
            adjacentNode.setDistance(distance + weight);

            LinkedList<Node> shortestPath = new LinkedList<>(node.getShortestPath());
            shortestPath.add(node);

            adjacentNode.setShortestPath(shortestPath);
        }
    }

    private static Graph ToGraph(List<String> nodes) {
        Graph graph = new Graph();
        
        for (String x: nodes) {
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