package com.example.androidappproject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;

public class DrawingView extends View implements MVPContract.CanvasContract {

    private final MVPContract.PresenterContract presenter;

    public DrawingView(Context context, AttributeSet attrs) {

        super(context, attrs);
        presenter = new Presenter(this);
    }

    public void createVertex(float touchX, float touchY) {
        presenter.createVertex(touchX, touchY);
    }

    public void createEdge(float x1, float y1, float x2, float y2) {
        presenter.createEdge(x1, y1, x2, y2);
    }

    public void previewEdge(float x1, float y1, float x2, float y2) {
        presenter.previewEdge(x1, y1, x2, y2);
    }

    public void startDFSTraversal() {
        presenter.startDFSTraversal();
    }

    public void resetTraversal() {
        presenter.resetTraversal();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        presenter.renderData(canvas);
    }

    @Override
    public void dataUpdated() {
        postInvalidate();
    }
}
