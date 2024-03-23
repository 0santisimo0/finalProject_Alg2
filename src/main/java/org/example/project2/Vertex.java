package org.example.project2;


public class Vertex {
    String value;

    public Vertex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}