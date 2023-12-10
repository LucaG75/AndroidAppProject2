package com.example.androidappproject;

import java.util.LinkedList;

public interface IGraphTraversal {

    public void traverse(Vertex v);

    public LinkedList<Vertex> vertexOrdering();
}
