package com.example.testplz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game2Activity extends AppCompatActivity {

    private GridLayout gridLayout;
    private View[] squares = new View[9];
    private int currentSquareIndex;
    private Random random = new Random();
    private Handler handler = new Handler();
    private boolean gameRunning = true;
    private int successfulClicks = 0;
    private boolean gameStarted = false;

    private int totalColors = random.nextInt(35) + 11;

    private long msDefault = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2);

        gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < squares.length; i++) {
            squares[i] = gridLayout.getChildAt(i);
            setViewBorder(squares[i]);
            squares[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gameStarted) {
                        gameStarted = true;
                        nextSquare(msDefault);
                        msDefault = 1000;
                        return;
                    }
                    if (v == squares[currentSquareIndex]) {
                        successfulClicks++;
                        nextSquare(msDefault);
                    } else {
                        Toast.makeText(Game2Activity.this, "Game Over!", Toast.LENGTH_LONG).show();
                        gameStarted = false;
                        showGameOver();
                    }
                }
            });
        }

        nextSquare(msDefault);

        TextView buttonGoHomeGame2 = findViewById(R.id.buttonGoHomeGame2);
        buttonGoHomeGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void nextSquare(long ms) {
        if (!gameRunning) {
            return;
        }

        if (successfulClicks >= totalColors) {
            showSuccess();
            return;
        }

        TextView textGameOver = findViewById(R.id.textGameOver);
        textGameOver.setVisibility(View.GONE);

        TextView buttonGoHome = findViewById(R.id.buttonGoHomeGame2);
        buttonGoHome.setVisibility(View.GONE);

        int previousSquareIndex = currentSquareIndex;
        currentSquareIndex = random.nextInt(squares.length);
        squares[previousSquareIndex].setBackgroundResource(R.drawable.view_border);
        squares[currentSquareIndex].setBackgroundColor(getRandomColor());

        handler.removeCallbacksAndMessages(null);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameRunning) {
                    squares[currentSquareIndex].setBackgroundResource(R.drawable.view_border);
                    showGameOver();
                }
            }
        }, ms);
    }


    private void setViewBorder(View view) {
        Drawable backgroundDrawable = view.getBackground();
        Drawable[] layers = new Drawable[2];
        layers[0] = backgroundDrawable;
        layers[1] = getResources().getDrawable(R.drawable.view_border);

        LayerDrawable layerDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layerDrawable = new LayerDrawable(layers);
        } else {
            layerDrawable = new LayerDrawable(layers);
            layerDrawable.setLayerInset(1, 2, 2, 2, 2);
        }

        view.setBackground(layerDrawable);
    }

    private void showGameOver() {
        handler.removeCallbacksAndMessages(null);

        Random random = new Random();
        int cashKey = random.nextInt(85) + 65;
        int oilKey = random.nextInt(55) + 35;
        int ferrariKey = random.nextInt(20) + 15;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) - cashKey);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) - oilKey);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) - ferrariKey);

        if(sharedPreferences.getInt("cash", 0) <= 0) {editor.putInt("cash", 0);}
        if(sharedPreferences.getInt("oil", 0) <= 0) {editor.putInt("oil", 0);}
        if(sharedPreferences.getInt("ferrari", 0) <= 0) {editor.putInt("ferrari", 0);}

        editor.apply();

        Toast.makeText(Game2Activity.this, "Game Over! You lose : " + cashKey + ", oil=" + oilKey + ", ferrari=" + ferrariKey, Toast.LENGTH_LONG).show();

        // Affiche le TextView "Game Over"
        TextView textGameOver = findViewById(R.id.textGameOver);
        textGameOver.setVisibility(View.VISIBLE);

        TextView buttonGoHome = findViewById(R.id.buttonGoHomeGame2);
        buttonGoHome.setVisibility(View.VISIBLE);

        gameRunning = false;
    }

    private void showSuccess() {
        handler.removeCallbacksAndMessages(null);

        TextView buttonGoHome = findViewById(R.id.buttonGoHomeGame2);
        buttonGoHome.setVisibility(View.VISIBLE);

        // Génération aléatoire des trois clés
        Random random = new Random();
        int cashKey = random.nextInt(81) + 60;
        int oilKey = random.nextInt(38) + 23;
        int ferrariKey = random.nextInt(11) + 5;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) + cashKey);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) + oilKey);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) + ferrariKey);

        editor.apply();

        Toast.makeText(Game2Activity.this, "Congratulations! You won! Keys generated: cash=" + cashKey + ", oil=" + oilKey + ", ferrari=" + ferrariKey, Toast.LENGTH_LONG).show();
    }

    private int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Assurez-vous d'arrêter toutes les tâches différées lorsque l'activité est détruite pour éviter les fuites de mémoire
        handler.removeCallbacksAndMessages(null);
    }
}


