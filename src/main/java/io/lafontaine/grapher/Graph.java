package io.lafontaine.grapher;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public Map<Integer, ArrayList<Integer>> getAdjacencyList() {
        Map<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();

        for (Edge edge : edges) {
            int source = edge.getSource();
            int target = edge.getTarget();

            if (!adjacencyList.containsKey(source)) {
                adjacencyList.put(source, new ArrayList<>());
            }

            adjacencyList.get(source).add(target);
        }

        return adjacencyList;
    }

    public Map<Integer, Map<Integer, Double>> getWeights() {
        Map<Integer, Map<Integer, Double>> weights = new HashMap<>();

        for (Edge edge : edges) {
            int source = edge.getSource();
            int target = edge.getTarget();
            double weight = edge.getWeight();

            if (!weights.containsKey(source)) {
                weights.put(source, new HashMap<>());
            }

            weights.get(source).put(target, weight);
        }

        return weights;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
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

    public static Graph fillGraph(Graph graph){
        graph.addNode(new Node(1, "Node 1"));
        graph.addNode(new Node(2, "Node 2"));
        graph.addNode(new Node(3, "Node 3"));
        graph.addEdge(new Edge(1, 2, 1.1));
        graph.addEdge(new Edge(2, 3, 2.2));
        graph.addEdge(new Edge(3, 1, 3.3));
        return graph;
    }

    public String Json2Cy(String json) {
        Graph graph = Json2Graph(json);
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        for (Node node : graph.getNodes()) {
            sb.append("{ group: 'nodes', data: { id: '").append(node.getId()).append("', name: '").append(node.getName()).append("' } },\n");
        }

        for (Edge edge : graph.getEdges()) {
            sb.append("{ group: 'edges', data: { source: '").append(edge.getSource()).append("', target: '").append(edge.getTarget()).append("', weight: ").append(edge.getWeight()).append(" } },\n");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }
}
