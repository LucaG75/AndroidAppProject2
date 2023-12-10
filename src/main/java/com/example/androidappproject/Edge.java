package com.example.androidappproject;

public class Edge {
    private final Vertex src;
    private final Vertex dst;
    private float weight;
    final float DEFAULT_WEIGHT = 1.0f;

    public Edge() {
        src = null;
        dst = null;
        weight = 0.0f;
    }

    public Edge(Vertex s, Vertex d) {
        src = s;
        dst = d;
        weight = (float) Math.sqrt((d.getX()-s.getX())*(d.getX()-s.getX())
                + (d.getY()-s.getY())*(d.getY()-s.getY()));
    }

    public Vertex getSource() {
        return src;
    }

    public Vertex getDestination() {
        return dst;
    }

    public float getWeight() {
        return weight;
    }
}
