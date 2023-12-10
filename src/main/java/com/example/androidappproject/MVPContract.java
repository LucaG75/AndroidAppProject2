package com.example.androidappproject;

import android.graphics.Canvas;

public interface MVPContract {

    interface PresenterContract {
        void renderData(Canvas canvas);
        void createVertex(float x, float y);
        void createEdge(float x1, float y1, float x2, float y2);
        void previewEdge(float x1, float y1, float x2, float y2);
        void DFSTraversal(Canvas canvas);
        void startDFSTraversal();
        void resetTraversal();
    }

    interface CanvasContract {
        void dataUpdated();
    }
}
