package io.lafontaine.grapher;

import java.util.*;

public final class Graph {

    private final Map<Integer, Set<Integer>> adjacentList = new HashMap<>();
    private final Map<Integer, Map<Integer, Double>> weights = new HashMap<>();

    public void addNode(int node) {
        adjacentList.computeIfAbsent(node, key -> new TreeSet<>());
    }

    public void addEdge(int one, int two, double weight) {
        addDirectedEdge(one, two, weight);
        addDirectedEdge(two, one, weight);
    }

    private void addDirectedEdge(int one, int two, double weight) {
        addNode(one);
        addNode(two);
        adjacentList.get(one).add(two);
        setWeight(one, two, weight);
    }

    private void setWeight(int one, int two, double weight) {
        weights.computeIfAbsent(one, key -> new HashMap<>());
        weights.get(one).put(two, weight);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Set<Integer>> entry : adjacentList.entrySet()) {
            int from = entry.getKey();
            for (int to : entry.getValue()) {
                sb.append(from).append(" -> ").append(to).append(" (").append(weights.get(from).get(to)).append(")").append("\n");
            }
        }
        return sb.toString();
    }

    public static void main(String... args) {
        Graph graph = new Graph();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addEdge(1, 2, 1.1);
        graph.addEdge(2, 3, 2.2);
        graph.addEdge(3, 1, 3.3);
        System.out.println(graph);
    }

}