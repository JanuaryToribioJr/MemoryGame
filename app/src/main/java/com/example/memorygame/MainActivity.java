package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CardMatch adapter;
    GridView gridView;
    Button resetBtn, homeBtn, moves;
    TextView endMsg;
    int moveCount = 0;


    private int[] cardImages = {
            R.drawable.img1, R.drawable.img1,
            R.drawable.img2, R.drawable.img2,
            R.drawable.img3, R.drawable.img3,
            R.drawable.img4, R.drawable.img4,
            R.drawable.img5, R.drawable.img5,
            R.drawable.img6, R.drawable.img6,
            R.drawable.img7, R.drawable.img7,
            R.drawable.img8, R.drawable.img8,
            R.drawable.img9, R.drawable.img9
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        endMsg = findViewById(R.id.endMsg);
        moves = findViewById(R.id.moves);

        gridView = findViewById(R.id.gridView);
        adapter = new CardMatch(this, cardImages);
        gridView.setAdapter(adapter);

        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMsg.setVisibility(View.GONE);
                resetMoveCount();
                resetGame();
            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                endMsg.setVisibility(View.GONE);
                finish();
            }
        });

    }

    private void resetGame() {
        adapter = new CardMatch(this, cardImages);
        gridView.setAdapter(adapter);
        Toast.makeText(this, "Cards Shuffled", Toast.LENGTH_SHORT).show();
    }

    public void showCongrats() {
        endMsg.setVisibility(View.VISIBLE);
    }

    public void incrementMoveCount() {
        moveCount++;
        moves.setText("Moves: " + moveCount);
    }

    public void resetMoveCount() {
        moveCount = 0;
        moves.setText("Moves: 0");
    }

}
