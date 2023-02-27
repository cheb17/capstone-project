package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PlotActivity extends AppCompatActivity {

    ImageView halfCourtView;
//    ImageView fullCourtView;
    Button missed;
    Button made;
    Button firstQtr;
    Button secondQtr;
    Button thirdQtr;
    Button fourthQtr;
    Button undo;
    Button redo;

    Button cluster;
    private boolean madeClicked;
    private boolean missedClicked;
    private boolean isQuarterSelected = false;
    private Stack<Bitmap> undoStack = new Stack<>();
    private Stack<Bitmap> redoStack = new Stack<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_activtiy);

        halfCourtView = findViewById(R.id.halfCourtImage);
        made = findViewById(R.id.btnMade);
        missed = findViewById(R.id.btnMissed);
        firstQtr = findViewById(R.id.btn1);
        secondQtr = findViewById(R.id.btn2);
        thirdQtr = findViewById(R.id.btn3);
        fourthQtr = findViewById(R.id.btn4);
        undo = findViewById(R.id.btnUndo);
        redo = findViewById(R.id.btnRedo);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!undoStack.empty()) {
                    Bitmap lastState = undoStack.pop();
                    redoStack.push(lastState);
                    if (!undoStack.empty()) {
                        lastState = undoStack.peek();
                    }
                    halfCourtView.setImageDrawable(new BitmapDrawable(getResources(), lastState));
                }
            }
        });

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!redoStack.empty()) {
                    Bitmap lastState = redoStack.pop();
                    undoStack.push(lastState);
                    halfCourtView.setImageDrawable(new BitmapDrawable(getResources(), lastState));
                }
            }
        });

        halfCourtView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (madeClicked || missedClicked) { // check if either button is clicked
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Drawable drawable = halfCourtView.getDrawable();
                        Rect bounds = drawable.getBounds();

                        int imageX = (int) event.getX() - bounds.left;
                        int imageY = (int) event.getY() - bounds.top;

                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setColor(madeClicked ? Color.parseColor("#B1D8B7") : Color.parseColor("#C02929"));

                        Bitmap bitmap = Bitmap.createBitmap(halfCourtView.getWidth(), halfCourtView.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawBitmap(((BitmapDrawable) drawable).getBitmap(), null, bounds, null);
                        canvas.drawCircle(imageX, imageY, 15, paint);
                        halfCourtView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

                        undoStack.push(bitmap); // Add current state to undo stack
                        redoStack.clear(); // Clear redo stack
                        Log.d(madeClicked ? "MADE" : "MISSED", "X: " + imageX + ", Y: " + imageY);
                    }
                    return true;
                }
                return false;
            }
        });

        // Initialize quarter variable to 1 for the first quarter
        final int[] quarter = {1};

        // Set click listeners for the quarter buttons
        firstQtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarter[0] = 1;
                firstQtr.setBackgroundColor(Color.parseColor("#CB3636"));
                firstQtr.setTextColor(Color.parseColor("#FFFFFF"));
                secondQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                thirdQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                fourthQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
            }
        });

        secondQtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarter[0] = 2;
                firstQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                secondQtr.setBackgroundColor(Color.parseColor("#CB3636"));
                secondQtr.setTextColor(Color.parseColor("#FFFFFF"));
                thirdQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                fourthQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
            }
        });

        thirdQtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarter[0] = 3;
                firstQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                secondQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                thirdQtr.setBackgroundColor(Color.parseColor("#CB3636"));
                thirdQtr.setTextColor(Color.parseColor("#FFFFFF"));
                fourthQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
            }
        });

        fourthQtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarter[0] = 4;
                firstQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                secondQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                thirdQtr.setBackgroundColor(Color.parseColor("#d4d3d6"));
                fourthQtr.setBackgroundColor(Color.parseColor("#CB3636"));
                fourthQtr.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

// Set click listeners for the made and missed buttons
        made.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                madeClicked = true;
                missedClicked = false;
                made.setBackgroundColor(Color.parseColor("#B1D8B7"));
                missed.setBackgroundColor(Color.GRAY);
            }
        });

        missed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missedClicked = true;
                madeClicked = false;
                missed.setBackgroundColor(Color.parseColor("#C02929"));
                made.setBackgroundColor(Color.GRAY);
            }
        });
    }
}