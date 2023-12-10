package com.example.androidappproject;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstTraversal implements IGraphTraversal {

    private Graph graph;
    private LinkedList<Vertex> vertexOrder = new LinkedList<Vertex>();

    BreadthFirstTraversal(Graph g) {
        graph = g;
    }
    @Override
    public void traverse(Vertex v) {
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(v);

        while (!q.isEmpty()) {
            if (!q.peek().getVisitedStatus()) {
                vertexOrder.add(q.peek());
            }
            Vertex next = q.remove();
            next.setVisitedStatus(true);

            LinkedList<Edge> edgeList = graph.getNeighbors(next);

            for (Edge edge : edgeList) {
                Vertex dst = edge.getDestination();

                if (!dst.getVisitedStatus()) {
                    q.add(dst);
                }
            }
        }
    }

    @Override
    public LinkedList<Vertex> vertexOrdering() {
        return vertexOrder;
    }
}
