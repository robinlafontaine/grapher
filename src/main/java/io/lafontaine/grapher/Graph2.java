package io.lafontaine.grapher;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An undirected and weighted graph.
 */
public class Graph2 {
    ArrayList<ArrayList<HashMap<Integer, Integer> >> adj;
    HashMap<Integer, String> nodes;
    ArrayList<HashMap<String, Integer>> edges;

    /**
     * Create a new graph.
     */
    public Graph2() {
        this.adj = new ArrayList<>();
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Add an edge to the graph.
     * @param u The source node.
     * @param v The target node.
     * @param weight The weight of the edge.
     */
    void addEdge(int u, int v, int weight)
    {
        this.adj.get(u).add(new HashMap<>());
        this.adj.get(u)
                .get(this.adj.get(u).size() - 1)
                .put(v, weight);

        this.adj.get(v).add(new HashMap<>());
        this.adj.get(v)
                .get(this.adj.get(v).size() - 1)
                .put(u, weight);

        HashMap<String, Integer> edge = new HashMap<>();
        edge.put("source", u);
        edge.put("target", v);
        edge.put("weight", weight);
        this.edges.add(edge);
    }

    /**
     * Get the adjacency list of the graph.
     * @return The adjacency list.
     */
    ArrayList<ArrayList<HashMap<Integer, Integer>>> getAdjacencyList() {
        return this.adj;
    }

    /**
     *  Add node to the graph.
     *  @param node The node to add.
     *  @param name The name of the node.
     */
    void addNode(int node, String name) {
        this.adj.add(new ArrayList<>());
        this.nodes.put(node, name);

    }

    /**
     * Get the nodes of the graph.
     * @return The nodes.
     */
    HashMap<Integer, String> getNodes() {
        return this.nodes;
    }

    /**
     * Get the edges of the graph.
     * @return The edges.
     */
    ArrayList<HashMap<String, Integer>> getEdges() {
        return this.edges;
    }

    public String toString() {
        return "Graph{" +
                "adj=" + adj +
                ", nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }

    public static String Graph2Json(Graph2 graph) {
        Gson gson = new Gson();
        return gson.toJson(graph);
    }

    public static Graph2 Json2Graph(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Graph2.class);
    }

    public static Graph2 fillGraph(Graph2 graph){
        graph.addNode(0, "A");
        graph.addNode(1, "B");
        graph.addNode(2, "C");
        graph.addNode(3, "D");
        graph.addNode(4, "E");
        graph.addNode(5, "F");

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 6);
        graph.addEdge(4, 5, 7);

        return graph;
    }

    public String Json2Cy(String json) {
        Graph2 graph = Json2Graph(json);
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        for (Node node : graph.getNodes()) {
            sb.append("{ \"group\": \"nodes\", \"data\": { \"id\": \"").append(node).append("\", \"name\": \"").append(node.getName()).append("\" } },\n");
        }

        for (Edge edge : graph.getEdges()) {
            sb.append("{ \"group\": \"edges\", \"data\": { \"id\": \"").append(edge.getSource()).append("-").append(edge.getTarget()).append("\", \"source\": \"").append(edge.getTarget()).append("\", \"target\": \"").append(edge.getSource()).append("\", \"weight\": ").append(edge.getWeight()).append(" } },\n");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }
}
