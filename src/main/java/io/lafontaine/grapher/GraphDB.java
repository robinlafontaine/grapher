package io.lafontaine.grapher;

import jakarta.persistence.*;

public class GraphDB {

    @Entity
    @Table(name = "graphs")
    public class Graphs {

        @Id
        @GeneratedValue
        private long id;

        @Column(name = "json", nullable = false)
        private String json;

        @Column(name = "hashcode", nullable = false)
        private int hashcode;

    }
}
