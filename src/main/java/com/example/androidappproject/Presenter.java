package com.example.androidappproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.LinkedList;
import java.util.Objects;

public class Presenter implements MVPContract.PresenterContract {

    private MVPContract.CanvasContract view;
    private final Graph g;
    private LinkedList<Vertex> vertexLinkedList, vertexOrderList;
    private int first, last;
    private Vertex ePreviewStart;
    private float ePreviewX, ePreviewY;
    private boolean ePreviewBool, traversalRunning;
    private Paint drawPaint;

    public Presenter(MVPContract.CanvasContract v) {
        view = v;
        g = new Graph();
        ePreviewBool = false;
        traversalRunning = false;
        drawPaint = new Paint();
    }

    public Vertex findNearbyVertex(float x, float y) {

        float r = 55f;
        for (Vertex v : g.getVertices()) {
            if (((x-v.getX()) * (x-v.getX())) + ((y-v.getY()) * (y-v.getY())) <= (r*r)) {
                return v;
            }
        }
        return new Vertex();
    }

    public boolean checkForEdge(Vertex src, Vertex dst) {

        for (Edge e : g.getEdges()) {
            if (e.getSource() == src && e.getDestination() == dst) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void renderData(Canvas canvas) {

            if (!traversalRunning) {
                for (Vertex v : g.getVertices()) {
                    drawPaint.setColor(Color.RED);
                    canvas.drawCircle(v.getX(), v.getY(), 50f, drawPaint);
                    drawPaint.setColor(Color.BLACK);
                    drawPaint.setTextAlign(Paint.Align.CENTER);
                    drawPaint.setTextSize(42f);
                    canvas.drawText(v.getVertexLabel(), v.getX(), v.getY(), drawPaint);
                }
            }
        if (traversalRunning) {
            DFSTraversal(canvas);
        }
        drawPaint.setStrokeWidth(8f);
        Vertex src;
        Vertex dst;
        for (Edge e : g.getEdges()) {
            src = e.getSource();
            dst = e.getDestination();
            drawPaint.setARGB(100,0,0,0);
            canvas.drawLine(src.getX(), src.getY(), dst.getX(), dst.getY(), drawPaint);
            drawPaint.setColor(Color.BLACK);
            drawPaint.setTextAlign(Paint.Align.LEFT);
            drawPaint.setTextSize(38f);
            canvas.drawText(String.format("%.1f", e.getWeight()), (src.getX() + dst.getX()) / 2,
                    (src.getY() + dst.getY()) / 2, drawPaint);
        }
        if (ePreviewBool) {
            drawPaint.setARGB(100,0,0,0);
            canvas.drawLine(ePreviewStart.getX(), ePreviewStart.getY(),
                    ePreviewX, ePreviewY, drawPaint);
        }
    }

    @Override
    public void createVertex(float x, float y) {

        if (Objects.equals(findNearbyVertex(x, y).getVertexLabel(), "Unassigned")) {
            int size = g.getNumVertices();
            String label = "V" + size;
            g.addVertex(new Vertex(x, y, label));
        }
    }

    @Override
    public void createEdge(float x1, float y1, float x2, float y2) {

        Vertex vertexStartPoint = findNearbyVertex(x1, y1);
        Vertex vertexEndPoint = findNearbyVertex(x2, y2);
        if (!Objects.equals(vertexStartPoint.getVertexLabel(), "Unassigned")
            && (!Objects.equals(vertexEndPoint.getVertexLabel(), "Unassigned"))) {
            if (!checkForEdge(vertexStartPoint, vertexEndPoint)) {
                g.addEdge(vertexStartPoint, vertexEndPoint);
                ePreviewBool = false;
            }
        }
    }

    @Override
    public void previewEdge (float x1, float y1, float x2, float y2) {

        if (!Objects.equals(findNearbyVertex(x1, y1).getVertexLabel(), "Unassigned")) {
            ePreviewStart = findNearbyVertex(x1, y1);
            ePreviewX = x2;
            ePreviewY = y2;
            ePreviewBool = true;
        }
    }

    @Override
    public void DFSTraversal(Canvas canvas) {

        for (int i = 0; i < last; ++i) {
            Vertex v = vertexOrderList.get(i);
            drawPaint.setColor(Color.RED);
            canvas.drawCircle(v.getX(), v.getY(), 50f, drawPaint);
            drawPaint.setColor(Color.BLACK);
            drawPaint.setTextAlign(Paint.Align.CENTER);
            drawPaint.setTextSize(42f);
            canvas.drawText(v.getVertexLabel(), v.getX(), v.getY(), drawPaint);
        }
        for (int i = 0; i <= first; ++i) {
            Vertex v = vertexOrderList.get(i);
            drawPaint.setColor(Color.BLUE);
            canvas.drawCircle(v.getX(), v.getY(), 50f, drawPaint);
            drawPaint.setColor(Color.WHITE);
            drawPaint.setTextAlign(Paint.Align.CENTER);
            drawPaint.setTextSize(42f);
            canvas.drawText(v.getVertexLabel(), v.getX(), v.getY(), drawPaint);
        }
        if (first < last) {
            ++first;
        }
    }

    @Override
    public void startDFSTraversal() {

        DepthFirstTraversal DFS = new DepthFirstTraversal(g);
        vertexLinkedList = g.getVertices();
        DFS.traverse(vertexLinkedList.getFirst());
        // Reset visited status
        for (Vertex v : vertexLinkedList) {
            v.setVisitedStatus(false);
        }
        vertexOrderList = DFS.vertexOrdering();
        first = 0;
        last = vertexOrderList.size();
        traversalRunning = true;
    }

    public void resetTraversal() {
        traversalRunning = false;
    }
}
