package io.lafontaine.grapher;
import java.util.HashMap;
import java.util.Map;

public class Solver {

    public static void main(String[] args) {
        Graph graph = new Graph();
        Graph.fillGraph(graph);
        System.out.println(isConnected(graph));
        System.out.println(isPositive(graph));
        Graph shortestPath = dijkstra(graph, 0, 5);
        System.out.println(shortestPath);
    }

    public static boolean isConnected(Graph graph) {
        for (Node node : graph.getNodes()) {
            if (graph.getAdjacencyList().get(node.getId()) == null || graph.getAdjacencyList().get(node.getId()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPositive(Graph graph) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getWeight() < 0) {
                return false;
            }
        }
        return true;
    }

    public static Graph dijkstra(Graph graph, int source, int target) {
        if (!isConnected(graph)) {
            System.out.println("Graph is not connected");
            return null;
        }
        if (!isPositive(graph)) {
            System.out.println("Graph has negative weights");
            return null;
        }

        Graph shortestPath = new Graph();
        Map<Integer, Double> distance = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();

        for (Node node : graph.getNodes()) {
            distance.put(node.getId(), Double.POSITIVE_INFINITY);
            previous.put(node.getId(), null);
        }

        distance.put(source, 0.0);

        while (!distance.isEmpty()) {
            int u = -1;
            double minDistance = Double.POSITIVE_INFINITY;
            for (Map.Entry<Integer, Double> entry : distance.entrySet()) {
                if (entry.getValue() < minDistance) {
                    u = entry.getKey();
                    minDistance = entry.getValue();
                }
            }

            if (u == -1) {
                break;
            }

            distance.remove(u);

            for (int v : graph.getAdjacencyList().get(u)) {
                System.out.println(v);
                double alt = minDistance + graph.getWeights().get(u).get(v);
                System.out.println(distance);
                if (alt < distance.get(v)) {
                    distance.put(v, alt);
                    previous.put(v, u);
                }
            }
        }

        int u = target;

        while (u != source) {
            shortestPath.addNode(graph.getNodes().get(u));
            if (previous.get(u) != null) {
                shortestPath.addEdge(new Edge(previous.get(u), u, graph.getWeights().get(previous.get(u)).get(u)));
            }
            u = previous.get(u);
        }

        return shortestPath;
    }
}
