package io.lafontaine.grapher;
import java.util.Objects;

public class Edge {
    private Node from;
    private Node to;
    private double weight;

    Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }


    public Node getFrom() {
        return this.from;
    }

    public Node getTo() {
        return this.to;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Edge getReverse() {
        return new Edge(this.to, this.from, this.weight);
    }

    @Override
    public String toString() {
        return this.from + " -> " + this.to + " (" + this.weight + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return this.from == edge.from && this.to == edge.to && Double.compare(edge.weight, this.weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to, this.weight);
    }
}
