package com.example.sudoku;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SudokuBoard sudokuBoard;

    private TextView textView;
    private Boolean isSecond = false;
    private int scoreG = 0;

    private TextView score;

    private int maxError = 3;
    private int currentError = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем матрицу из Intent
        Intent intent = getIntent();
        int[][] sudoku_Empty = (int[][]) intent.getSerializableExtra("sudoku_matrix");

        TableLayout tableLayout = findViewById(R.id.tableSudoku);
        sudokuBoard = new SudokuBoard(sudoku_Empty);
        textView = findViewById(R.id.textView);
        score = findViewById(R.id.scoreGame);


        // Добавление кнопки "Заврешить"
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Если доска полностью заполнена, показываем AlertDialog о завершении игры
                if (sudokuBoard.isBoardFull()) {
                    showGameFinishedDialog();
                }else{
                    // Переход к другой активности
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                    finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться назад
                }

            }
        });

        for(int q = 0; q < 3; q++) {
            TableRow tableAllrow = new TableRow(this);

            for (int i = 0; i < 3; i++) {
                TableLayout subTableLayout = new TableLayout(this);

                // Создание 3 строк (TableRow) в каждой таблице
                for (int j = 0; j < 3; j++) {
                    TableRow tableRow = new TableRow(this);
                    subTableLayout.addView(tableRow);

                    // Создание 3 EditText в каждой строке
                    for (int k = 0; k < 3; k++) {
                        EditText editText = new EditText(this);
                        // Добавьте здесь настройки для EditText, если необходимо
                        editText.setHint("");
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setPadding(20, 20, 20, 20);
                        editText.setGravity(View.TEXT_ALIGNMENT_CENTER);

                        // Устанавливаем размеры EditText
                        TableRow.LayoutParams params = new TableRow.LayoutParams(
                                0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                        editText.setLayoutParams(params);

                        // Добавляем границы для каждой ячейки
                        GradientDrawable border = new GradientDrawable();
                        border.setStroke(1, Color.BLACK);
                        editText.setBackground(border);

                        final int row = q * 3 + j;
                        final int col = i * 3 + k;

                        int value = sudoku_Empty[q * 3 + j][i * 3 + k];
                        if (value != 0) {
                            editText.setText(String.valueOf(value));
                            editText.setEnabled(false); // чтобы не редактировать заполненные значения
                            editText.setTextColor(Color.BLACK);
                        }

                       /* editText.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        editText.setBackgroundColor(Color.parseColor("#FF9696C8"));
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        // Сбрасываем цвет фона при отпускании пальца
                                        editText.setBackgroundColor(Color.WHITE);
                                        break;
                                }
                                // Возвращаем false, чтобы обработчик onTouch не прерывался и другие обработчики могли быть вызваны
                                return false;
                            }
                        });*/

                        // Добавляем слушатель изменения текста для проверки правильности ввода
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (!editable.toString().isEmpty()) {
                                    int value = Integer.parseInt(editable.toString());
                                    if(value <= 9 && value >= 1) {
                                        if (sudokuBoard.isMoveValid(row, col, value)) {
                                            // Если введенное значение допустимо, устанавливаем его в доску
                                            sudokuBoard.setCellValue(row, col, value);
                                            scoreG += 100;
                                            editText.setTextColor(Color.parseColor("#4561EF"));
                                            score.setText("Счет : " + scoreG);
                                        } else {

                                            // Увеличиваем значение ошибок
                                            currentError++; // Увеличиваем на 1
                                            scoreG -= 75;
                                            if(scoreG < 0){
                                                scoreG = 0;
                                            }
                                            score.setText("Счет : " + scoreG);

                                            // Обновляем textView с новым значением ошибок
                                            textView.setText("Ошибки: " + currentError + " / " + maxError);

                                            // Проверяем, достигли ли мы максимального количества ошибок
                                            if (currentError > maxError) {
                                                if(!isSecond) {
                                                    // Показываем AlertDialog о завершении игры
                                                    showGameOverDialog();
                                                }else{
                                                    showGameOver();
                                                }
                                            }else {
                                                // Если введенное значение недопустимо, очищаем поле и выводим уведомление
                                                editText.setText("");
                                                Toast.makeText(MainActivity.this, "Недопустимое значение!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }else{
                                        // Если введенное значение недопустимо, очищаем поле и выводим уведомление
                                        editText.setText("");

                                        // Увеличиваем значение ошибок

                                        currentError++; // Увеличиваем на 1

                                        // Обновляем textView с новым значением ошибок
                                        textView.setText("Ошибки: " + currentError + " / " + maxError);

                                        // Проверяем, достигли ли мы максимального количества ошибок
                                        if (currentError > maxError) {
                                            // Показываем AlertDialog о завершении игры
                                            showGameOverDialog();
                                        }else {
                                            Toast.makeText(MainActivity.this, "Вы можете ввести только цифры от 1 - 9", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else {
                                    sudokuBoard.setCellValue(row, col, 0);
                                }

                                // Если доска полностью заполнена, показываем AlertDialog о завершении игры
                                if (sudokuBoard.isBoardFull()) {
                                    showGameFinishedDialog();
                                }
                            }
                        });


                        tableRow.addView(editText);
                    }
                }
                GradientDrawable border = new GradientDrawable();

                border.setStroke(3, Color.parseColor("#FF000000"));
                subTableLayout.setBackground(border);

                tableAllrow.addView(subTableLayout);
            }
            tableLayout.addView(tableAllrow);
        }

        GradientDrawable border = new GradientDrawable();

        border.setStroke(6, Color.parseColor("#FF000000"));
        tableLayout.setBackground(border);
    }

    // Метод для показа AlertDialog о завершении игры
    private void showGameFinishedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Поздравляем!")
                .setMessage("Вы успешно завершили игру со счетом" + scoreG + ".")
                .setPositiveButton("Вернуться", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Переход к другой активности
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться назад
                    }
                })
                .setCancelable(false) // Нельзя закрыть AlertDialog кнопкой "Назад" на устройстве
                .show();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Игра окончена")
                .setMessage("Вы допустили " + currentError + " ошибки и проиграли.")
                .setPositiveButton("Завершить игру", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Переходим к другой активности
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться назад
                    }
                })
                .setNegativeButton("Второй шанс", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentError = 0;
                        // Обнуляем счетчик ошибок
                        textView.setText("Ошибки: " + currentError + " / " + maxError);
                        isSecond = true;
                    }
                })
                .setCancelable(false) // Нельзя закрыть AlertDialog кнопкой "Назад" на устройстве
                .show();
    }

    private void showGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Игра окончена")
                .setMessage("Вы допустили " + currentError + " ошибки и проиграли.")
                .setPositiveButton("Завершить игру", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Переходим к другой активности
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться назад
                    }
                })
                /*.setNegativeButton("Второй шанс", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentError = 0;
                        // Обнуляем счетчик ошибок
                        textView.setText("Ошибки: " + currentError + " / " + maxError);
                    }
                })*/
                .setCancelable(false) // Нельзя закрыть AlertDialog кнопкой "Назад" на устройстве
                .show();
    }


}