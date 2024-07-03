package io.lafontaine.grapher;

public class Node {

    private String name;
    private boolean visited;
    private double distance;
    private Node previous;

    public Node(String name) {
        this.name = name;
        this.visited = false;
        this.distance = Double.MAX_VALUE;
        this.previous = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return name.equals(node.name);
    }

    public int compareTo(Node node){
        return this.name.compareTo(node.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
