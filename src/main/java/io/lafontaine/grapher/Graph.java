package io.lafontaine.grapher;
import com.google.gson.Gson;

import java.util.*;

import static io.lafontaine.grapher.Solver.pathToCy;

public class Graph {
    private final Map<String, Node> nodes;
    private final Map<Node, LinkedList<Edge>> adjacencyList;

    public Graph() {
        nodes = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addNode(String nodeName) {
        if (!nodes.containsKey(nodeName)) {
            Node node = new Node(nodeName);
            nodes.put(nodeName, node);
            adjacencyList.put(node, new LinkedList<>());
        }
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void addEdge(String nodeName1, String nodeName2, double weight) {
        Node node1 = nodes.get(nodeName1);
        Node node2 = nodes.get(nodeName2);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph");
        }

        Edge edge1 = new Edge(node1, node2, weight);
        Edge edge2 = new Edge(node2, node1, weight); // because the graph is undirected

        adjacencyList.get(node1).add(edge1);
        adjacencyList.get(node2).add(edge2);
    }

    public LinkedList<Edge> getEdges(String nodeName) {
        Node node = nodes.get(nodeName);
        if (node == null) {
            throw new IllegalArgumentException("Node not found in the graph");
        }
        return adjacencyList.get(node);
    }

    public boolean exists(String nodeName) {
        return nodes.containsKey(nodeName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes.values()) {
            sb.append(node).append(" -> ").append(adjacencyList.get(node)).append("\n");
        }
        return sb.toString();
    }

    public static String toJson(Graph graph) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"nodes\": [\n");
        for (Node node : graph.getNodes().values()) {
            sb.append("{ \"name\": \"").append(node.getName()).append("\" },\n");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("],\n\"edges\": [\n");
        for (Node node : graph.getNodes().values()) {
            for (Edge edge : graph.getEdges(node.getName())) {
                sb.append("{ \"from\": \"").append(edge.getFrom()).append("\", \"to\": \"").append(edge.getTo()).append("\", \"weight\": ").append(edge.getWeight()).append(" },\n");
            }
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("]\n}");
        return sb.toString();
    }

    public static Graph fromJson(String json) {
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        Graph graph = new Graph();

        for (Map<String, String> node : (List<Map<String, String>>) map.get("nodes")) {
            graph.addNode(node.get("name"));
        }

        for (Map<String, Object> edge : (List<Map<String, Object>>) map.get("edges")) {
            graph.addEdge((String) edge.get("from"), (String) edge.get("to"), (double) edge.get("weight"));
        }

        return graph;
    }

    public static void fillGraph(Graph graph){
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 2);
        graph.addEdge("C", "B", 1);
        graph.addEdge("B", "D", 1);
        graph.addEdge("C", "D", 7);
    }

    public static void fillGraphBIG(Graph graph) {
        for (char nodeName = 'A'; nodeName <= 'T'; nodeName++) {
            graph.addNode(String.valueOf(nodeName));
        }

        for (char nodeName = 'A'; nodeName < 'T'; nodeName++) {
            graph.addEdge(String.valueOf(nodeName), String.valueOf((char) (nodeName + 1)), Math.floor((Math.random() * 20 + 1) * 100)/100);
        }

        graph.addEdge("A", "C", 2);
        graph.addEdge("A", "D", 3);
        graph.addEdge("B", "E", 4);
        graph.addEdge("B", "F", 5);
        graph.addEdge("C", "G", 6);
        graph.addEdge("C", "H", 7);
        graph.addEdge("D", "I", 8);
        graph.addEdge("D", "J", 9);
        graph.addEdge("E", "K", 10);
        graph.addEdge("E", "L", 11);
        graph.addEdge("F", "M", 12);
        graph.addEdge("F", "N", 13);
        graph.addEdge("G", "O", 14);
        graph.addEdge("G", "P", 15);
    }

    public String Graph2Cy(Graph graph, LinkedList<Node> solution) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<String> cyEdges = null;
        
        if (solution != null) {
            cyEdges = pathToCy(solution);
        }

        sb.append("[\n");

        for (Node node : graph.getNodes().values()) {
            sb.append("{ \"group\": \"nodes\", \"data\": { \"id\": \"").append(node.getName()).append("\", \"name\": \"").append(node.getName()).append("\" } ");
            if (solution != null && solution.contains(node)) {
                sb.append(", \"classes\": [\"solution_node\"] ");
            }
            sb.append("},\n");

            for (Edge edge : graph.getEdges(node.getName())) {
                if (!edges.contains(edge.getReverse())) {
                    var id = edge.getFrom().getName() + "-" + edge.getTo().getName();
                    sb.append("{ \"group\": \"edges\", \"data\": { \"id\": \"").append(id).append("\", \"source\": \"").append(edge.getTo()).append("\", \"target\": \"").append(edge.getFrom()).append("\", \"weight\": ").append(edge.getWeight()).append(" } ");
                    if (solution != null && cyEdges.contains(id)) {
                        sb.append(", \"classes\": [\"solution_edge\"] ");
                    }
                    sb.append("},\n");
                    edges.add(edge);
                }
            }
        }

        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }
}