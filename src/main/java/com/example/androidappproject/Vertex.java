package com.example.androidappproject;

import android.graphics.Paint;

public class Vertex {

    public Vertex() {
        vLabel = "Unassigned";
        visitedStatus = false;
    }

    public Vertex(String label) {
        vLabel = label;
        visitedStatus = false;
    }

    public Vertex(float x, float y, String label) {
        xpos = x;
        ypos = y;
        vLabel = label;
        visitedStatus = false;
    }

    private final String vLabel;
    private float xpos;
    private float ypos;
    private boolean visitedStatus;

    public float getX() {
        return xpos;
    }

    public float getY() {
        return ypos;
    }


    public String getVertexLabel() {
        return vLabel;
    }

    public void setVisitedStatus(boolean status) {
        visitedStatus = status;
    }

    public boolean getVisitedStatus() {
        return visitedStatus;
    }

    public void resetVisitedStatus() {
        visitedStatus = false;
    }
}
