package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    public Button btnEasy;
    public Button btnMidle;
    public Button btnHard;

    public int[][] sudokuM = {
            {9, 6, 8, 3, 2, 5, 7, 4, 1},
            {1, 4, 5, 6, 7, 8, 3, 2, 9},
            {3, 2, 7, 1, 9, 4, 6, 8, 5},
            {4, 3, 6, 9, 5, 1, 2, 7, 8},
            {5, 1, 2, 8, 4, 7, 9, 6, 3},
            {7, 8, 9, 2, 6, 3, 5, 1, 4},
            {2, 5, 3, 4, 1, 6, 8, 9, 7},
            {8, 9, 1, 7, 3, 2, 4, 5, 6},
            {6, 7, 4, 5, 8, 9, 1, 3, 2}
    };

    public int[][] sudokuM_Empty = {
            {9, 6, 8, 3, 0, 5, 7, 4, 1},
            {1, 0, 0, 0, 0, 8, 0, 0, 0},
            {3, 0, 0, 0, 9, 4, 6, 8, 5},
            {0, 0, 0, 9, 0, 0, 2, 7, 0},
            {5, 1, 2, 8, 4, 7, 9, 0, 0},
            {0, 0, 9, 0, 0, 3, 5, 0, 4},
            {0, 5, 0, 0, 0, 6, 0, 0, 0},
            {0, 0, 1, 7, 0, 2, 0, 5, 0},
            {6, 7, 0, 0, 0, 9, 0, 3, 0}
    };

    public int[][] sudokuE = {
            {2, 9, 8, 3, 7, 5, 1, 4, 6},
            {6, 1, 7, 9, 2, 4, 3, 8, 5},
            {5, 4, 3, 6, 1, 8, 2, 9, 7},
            {3, 6, 4, 5, 9, 1, 8, 2, 1},
            {9, 2, 5, 8, 4, 7, 6, 7, 3},
            {8, 7, 1, 2, 3, 6, 9, 5, 4},
            {7, 3, 6, 4, 8, 2, 5, 1, 9},
            {1, 5, 2, 7, 6, 9, 4, 3, 8},
            {4, 8, 9, 1, 5, 3, 7, 6, 2}
    };

    public int[][] sudokuE_Empty = {
            {0, 0, 0, 3, 7, 5, 1, 4, 0},
            {6, 1, 7, 9, 0, 4, 0, 0, 5},
            {0, 0, 3, 0, 0, 0, 0, 0, 0},
            {3, 6, 4, 5, 9, 7, 8, 0, 0},
            {9, 0, 5, 8, 0, 0, 6, 7, 3},
            {8, 7, 0, 2, 3, 0, 9, 5, 0},
            {7, 0, 6, 0, 8, 0, 0, 1, 9},
            {1, 5, 2, 7, 6, 9, 4, 0, 8},
            {0, 0, 9, 1, 5, 0, 7, 6, 2}
    };

    public int[][] sudokuH = {
            {1, 5, 3, 6, 4, 2, 7, 8, 9},
            {9, 2, 7, 3, 8, 5, 4, 1, 6},
            {6, 8, 4, 7, 1, 9, 5, 2, 3},
            {8, 9, 6, 1, 5, 3, 2, 7, 4},
            {4, 3, 1, 9, 2, 7, 6, 5, 8},
            {5, 7, 2, 4, 6, 8, 3, 9, 1},
            {2, 1, 9, 5, 3, 6, 8, 4, 7},
            {3, 4, 5, 8, 7, 1, 9, 6, 2},
            {7, 6, 8, 2, 9, 4, 1, 3, 5}
    };

    public int[][] sudokuH_Empty = {
            {0, 0, 0, 6, 0, 0, 7, 8, 9},
            {9, 2, 7, 3, 0, 0, 0, 0, 0},
            {6, 0, 0, 0, 0, 0, 5, 0, 3},
            {8, 9, 6, 0, 0, 3, 0, 7, 0},
            {4, 0, 1, 0, 0, 7, 6, 5, 8},
            {5, 0, 2, 0, 0, 0, 3, 0, 1},
            {2, 1, 0, 0, 0, 0, 8, 4, 0},
            {0, 0, 0, 8, 7, 0, 0, 0, 2},
            {0, 6, 8, 2, 9, 4, 0, 0, 0}
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnEasy = findViewById(R.id.btnEasy);
        btnMidle = findViewById(R.id.btnMidle);
        btnHard = findViewById(R.id.btnHard);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                // Передаем матрицу как дополнение к Intent
                intent.putExtra("sudoku_matrix", sudokuE_Empty);
                // Переходим к другой активности
                startActivity(intent);

            }
        });

        btnMidle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                // Передаем матрицу как дополнение к Intent
                intent.putExtra("sudoku_matrix", sudokuM_Empty);
                // Переходим к другой активности
                startActivity(intent);
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                // Передаем матрицу как дополнение к Intent
                intent.putExtra("sudoku_matrix", sudokuH_Empty);
                // Переходим к другой активности
                startActivity(intent);

            }
        });
    }
}