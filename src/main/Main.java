import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            List<String> response = scan("C:\\Users\\Biscoitinho\\Documents\\paths.txt");
            List<Node> graph = ToNodes(response);
        } catch (Exception e) {
            throw e;
        }
    }

    private static List<Node> ToNodes(List<String> nodes) {
        List<Node> graph = new ArrayList<Node>();

        for (String x : nodes) {            
            String[] values = x.split(";");

            Node node = new Node(values[0]);
            node.nodes.add(new Node(values[1]));
            node.value = Integer.parseInt(values[2]);

            graph.add(node);
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