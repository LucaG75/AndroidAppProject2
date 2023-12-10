package com.example.androidappproject;

import java.util.LinkedList;

public class DepthFirstTraversal implements IGraphTraversal {

    private final Graph graph;
    private final LinkedList<Vertex> vertexOrder = new LinkedList<Vertex>();

    DepthFirstTraversal(Graph g) {
        graph = g;
    }
    @Override
    public void traverse(Vertex v) {

        v.setVisitedStatus(true);
        vertexOrder.add(v);
        LinkedList<Edge> neighborList = graph.getNeighbors(v);

        for (Edge edge : neighborList) {

            Vertex dst = edge.getDestination();

            if (!dst.getVisitedStatus()) {
                traverse(dst);
            }
            else {
                vertexOrder.add(dst);
            }
        }
    }

    @Override
    public LinkedList<Vertex> vertexOrdering() {
        return vertexOrder;
    }
}
