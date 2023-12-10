package com.example.androidappproject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Graph {

    private final HashMap<Vertex, LinkedList<Edge>> m_adjacencyList;

    public Graph() {
        m_adjacencyList = new HashMap<>(1024);
    }

    public int getNumVertices() {
        return m_adjacencyList.size();
    }

    public int getNumEdges() {

        int edgeSum = 0;
        for (HashMap.Entry<Vertex, LinkedList<Edge>> hEntry : m_adjacencyList.entrySet()) {
            LinkedList<Edge> edgeList = hEntry.getValue();
            edgeSum += edgeList.size();
        }
        return edgeSum;
    }

    public void addVertex(Vertex v) {
        m_adjacencyList.put(v, new LinkedList<Edge>());
    }

    public void addEdge(Vertex src, Vertex dst) {

        if (m_adjacencyList.containsKey(src)) {
            LinkedList<Edge> edgeList = m_adjacencyList.get(src);
            Edge vEdge = new Edge(src, dst);
            edgeList.add(vEdge);
        }
    }

    public float getEdgeWeight(Vertex src, Vertex dst) {

        float weight = 0.0f;

        if (m_adjacencyList.containsKey(src)) {
            LinkedList<Edge> edgeList = m_adjacencyList.get(src);

            for (int i = 0; i < Objects.requireNonNull(edgeList).size(); ++i) {

                Edge temp = edgeList.get(i);

                if (temp.getSource() == src && temp.getDestination() == dst) {
                    weight = temp.getWeight();
                }
            }
        }
        return weight;
    }

    public LinkedList<Edge> getNeighbors(Vertex src) {
        return m_adjacencyList.getOrDefault(src, null);
    }

    public LinkedList<Vertex> getVertices() {

        LinkedList<Vertex> vertexList = new LinkedList<>();
        for (HashMap.Entry<Vertex, LinkedList<Edge>> hEntry : m_adjacencyList.entrySet()) {
            vertexList.add(hEntry.getKey());
        }
        return vertexList;
    }

    public LinkedList<Edge> getEdges() {

        LinkedList<Edge> edgeList = new LinkedList<>();
        for (HashMap.Entry<Vertex, LinkedList<Edge>> hEntry : m_adjacencyList.entrySet()) {
            edgeList.addAll(hEntry.getValue());
        }
        return edgeList;
    }
}
