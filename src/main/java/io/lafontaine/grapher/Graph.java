package io.lafontaine.grapher;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes.values()) {
            sb.append(node).append(" -> ").append(adjacencyList.get(node)).append("\n");
        }
        return sb.toString();
    }

    public static String Graph2Json(Graph graph) {
        Gson gson = new Gson();
        return gson.toJson(graph);
    }

    public static Graph Json2Graph(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Graph.class);
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

    public String Graph2Cy(Graph graph) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Edge> edges = new ArrayList<>();

        sb.append("[\n");

        for (Node node : graph.getNodes().values()) {
            sb.append("{ \"group\": \"nodes\", \"data\": { \"id\": \"").append(node.getName()).append("\", \"name\": \"").append(node.getName()).append("\" } },\n");
            for (Edge edge : graph.getEdges(node.getName())) {
                if (!edges.contains(edge.getReverse())) {
                    sb.append("{ \"group\": \"edges\", \"data\": { \"id\": \"").append(edge.getFrom()).append("-").append(edge.getTo()).append("\", \"source\": \"").append(edge.getTo()).append("\", \"target\": \"").append(edge.getFrom()).append("\", \"weight\": ").append(edge.getWeight()).append(" } },\n");
                    edges.add(edge);
                }
            }
        }

        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }
}