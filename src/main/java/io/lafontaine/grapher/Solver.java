package io.lafontaine.grapher;

public class Solver {

    public boolean isPositive(Graph graph) {
        for(Node node : graph.getNodes().values()) {
            for(Edge edge : graph.getEdges(node.getName())) {
                if(edge.getWeight() < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isConnected(Graph graph) {
        for(Node node : graph.getNodes().values()) {
            if(graph.getEdges(node.getName()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Node getClosestUnvisited(Graph graph) {
        Node closest = null;
        for(Node node : graph.getNodes().values()) {
            if(!node.isVisited() && (closest == null || node.getDistance() < closest.getDistance())) {
                closest = node;
            }
        }
        return closest;
    }

    public Graph shortestPath(Graph graph, Node start, Node end) {
        if (!isPositive(graph) || !isConnected(graph)) {
            throw new IllegalArgumentException("Graph must be positive and connected");
        }

        // Set the distance for the start node to 0 and for all other nodes to infinity.
        for (Node node : graph.getNodes().values()) {
            node.setDistance(Double.POSITIVE_INFINITY);
        }
        start.setDistance(0);

        // Set the start node as the current node.
        Node current = start;

        while (current != null) {
            // For the current node, consider all of its unvisited neighbors and calculate their tentative distances.
            for (Edge edge : graph.getEdges(current.getName())) {
                Node neighbor = edge.getTo();
                if (!neighbor.isVisited()) {
                    double tentativeDistance = current.getDistance() + edge.getWeight();
                    if (tentativeDistance < neighbor.getDistance()) {
                        neighbor.setDistance(tentativeDistance);
                        neighbor.setPrevious(current);
                    }
                }
            }

            // After considering all the neighbors of the current node, mark the current node as visited.
            current.setVisited(true);

            // If the destination node has been marked visited, then stop. The algorithm has finished.
            if (end.isVisited()) {
                break;
            }

            // Otherwise, select the unvisited node that is marked with the smallest tentative distance, and set it as the new "current node".
            current = getClosestUnvisited(graph);
        }

        return graph;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Graph.fillGraph(graph);
        Solver solver = new Solver();
        System.out.println(graph);
        System.out.println(solver.shortestPath(graph, graph.getNodes().get("A"), graph.getNodes().get("D")));
    }

}
