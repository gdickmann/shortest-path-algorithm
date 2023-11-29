import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

public class Main {

    private static final int SOURCE_NODE = 0;

    public static void main(String[] args) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Defina a origem e o destino no formato 'X X': ");
            String startAndEnd = scanner.nextLine();
            
            scanner.close();
            
            List<String> nodes = scan("C:\\Users\\Biscoitinho\\Documents\\paths.txt");
            Graph graph = ToGraph(nodes);
            
            // 1) Calcular a distância entre dois pontos fornecidos pelo usuário (pedir quais pontos), utilizando o algoritmo Dijkstra. 
            Graph shortestPathsBetweenNodes = calculateShortestPath(graph, graph.getSourceAsArrayList().get(SOURCE_NODE));

            System.out.println("\nMostrando valores em forma de pilha");
            printNodesAsStack(shortestPathsBetweenNodes, startAndEnd);

            System.out.println("\nMostrando valores em forma de fila");
            printNodesAsQueue(shortestPathsBetweenNodes, startAndEnd);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private static void printNodesAsQueue(Graph graph, String startAndEnd) {
        Queue<Node> queue = new LinkedList<Node>();

        for (Node node : graph.getNodes()) {
            queue.add(node);
        }

        for (Node node: queue) {
            for (Entry<Node, Integer> neighbors: node.getNeighbors().entrySet()) {
                if (neighbors.getKey().getShortestDistanceFound() != Integer.MAX_VALUE) {
                    System.out.println("O menor caminho encontrado do nó " + node.getName() + " para o nó " 
                    + neighbors.getKey().getName() + " foi de " + neighbors.getKey().getShortestDistanceFound());
                }
            }
        }
    }

    private static void printNodesAsStack(Graph graph, String startAndEnd) {
        Stack<Node> stack = new Stack<Node>();

        for (Node node : graph.getNodes()) {
            stack.push(node);
        }

        for (Node node: stack) {
            for (Entry<Node, Integer> neighbors: node.getNeighbors().entrySet()) {
                if (neighbors.getKey().getShortestDistanceFound() != Integer.MAX_VALUE) {
                    System.out.println("O menor caminho encontrado do nó " + node.getName() + " para o nó " 
                    + neighbors.getKey().getName() + " foi de " + neighbors.getKey().getShortestDistanceFound());
                }
            }
        }
    }
    /**
     * @param graph O {@link Graph} a ser calculado.
     * @param source O nó de origem.
     * @return {@link Graph} contendo o menor caminho de todos os nós com base no nó de origem.
     */
    private static Graph calculateShortestPath(Graph graph, Node source) {
        source.setShortestDistanceFound(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);
        
        while (unsettledNodes.size() != 0) {
            
            Node node = traverseToLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(node);
            
            for (Entry<Node, Integer> neighbors: node.getNeighbors().entrySet()) {
                
                Node neighbor = neighbors.getKey();
                Integer weight = neighbors.getValue();
                
                if (!settledNodes.contains(neighbor)) {
                    calculateShortestDistanceFound(neighbor, weight, node);
                    unsettledNodes.add(neighbor);
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
            if (node.getShortestDistanceFound() < lowestDistance) {
                lowestDistance = node.getShortestDistanceFound();
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }
    
    private static void calculateShortestDistanceFound(Node neighbor, Integer weight, Node node) {
        Integer distance = node.getShortestDistanceFound();
        
        if (distance + weight < neighbor.getShortestDistanceFound()) {
            neighbor.setShortestDistanceFound(distance + weight);

            LinkedList<Node> shortestPath = new LinkedList<>(node.getShortestPath());
            shortestPath.add(node);

            neighbor.setShortestPath(shortestPath);
        }
    }

    private static Graph ToGraph(List<String> nodes) {
        Graph graph = new Graph();
        
        for (String x: nodes) {
            String[] values = x.split(";");
            
            String name = values[0];
            String destination = values[1];
            int distance = Integer.parseInt(values[2]);

            Node node = getNodeByName(name, graph);
            node.addDestination(new Node(destination), distance);
            
            graph.addNode(node);
        }

        return graph;
    }
    
    private static Node getNodeByName(String name, Graph graph) {
        for (Node node : graph.getNodes()) {
            if (node.getName().equals(name)) return node;
        }

        return new Node(name);
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