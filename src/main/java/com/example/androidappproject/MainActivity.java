package com.example.androidappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawingView dv;
    private static int mode;
    private float previousX;
    private float previousY;
    private int previousMotionEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        dv = findViewById(R.id.drawingView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        /**
        Restrict touch events to drawing view zone
         */

        mode = 0;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText(MainActivity.this, "Selected mode: " + selectedItem, Toast.LENGTH_SHORT).show();
                    mode = position;
                    Button traverseButton = findViewById(R.id.traverse_button);
                    if (position == 5 || position == 6) {
                        traverseButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        traverseButton.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();
        int[] coordsView = {(int) touchX, (int) touchY};
        dv.getLocationInWindow(coordsView);
        touchY = touchY - (float) coordsView[1];
        Log.d("Touch Event", "X: " + touchX + ", Y: " + touchY);
        int action = event.getActionMasked();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d("Touch Event Type: ", "Action DOWN");
                if (mode == 1) {
                    dv.createVertex(touchX, touchY);
                }
                if (mode == 2) {
                    previousX = touchX;
                    previousY = touchY;
                }
                previousMotionEvent = 0;
                dv.dataUpdated();
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d("Touch Event Type: ", "Action MOVE");
                if (mode == 2 && (previousMotionEvent == 0 || previousMotionEvent == 2)) {
                    dv.previewEdge(previousX, previousY, touchX, touchY);
                }
                previousMotionEvent = 2;
                dv.dataUpdated();
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d("Touch Event Type: ", "Action UP");
                if (mode == 2 && previousMotionEvent == 2) {
                    dv.createEdge(previousX, previousY, touchX, touchY);
                }
                previousMotionEvent = 1;
                dv.dataUpdated();
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d("Touch Event Type:", "Action CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d("Touch Event Type: ", "Action OUTSIDE");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void onClick_Traverse(View v) {

        if (mode == 5) {
            dv.startDFSTraversal();
            dv.dataUpdated();
            //dv.resetTraversal();
        }
        if (mode == 6) {
            //dv.startBFSTraversal();
        }
    }
}
