package com.example.testplz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game1Activity extends AppCompatActivity {

    private EditText editTextNumber;
    private TextView buttonGuess;
    private int randomNumber;
    private int attemptCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1);

        editTextNumber = findViewById(R.id.editTextNumber);
        buttonGuess = findViewById(R.id.buttonGuess);

        // Générer un nombre aléatoire entre 0 et 99
        Random random = new Random();
        randomNumber = random.nextInt(100);
        attemptCount = 0;

        buttonGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nombre saisi par le joueur
                String inputText = editTextNumber.getText().toString();
                if (!inputText.isEmpty()) {
                    int guessedNumber = Integer.parseInt(inputText);
                    attemptCount++;

                    TextView attemptString = findViewById(R.id.attemptString);
                    if (attemptCount >= 7) {
                        attemptString.setText("Try : 7/7");
                    } else {
                        attemptString.setText("Try : " + attemptCount + "/7");
                    }


                    // Comparer le nombre saisi avec le nombre aléatoire
                    if (guessedNumber == randomNumber) {
                        showSuccess();
                    } else if (guessedNumber < randomNumber) {
                        TextView test = findViewById(R.id.symboleUpDown);
                        test.setText("⬇️");
                    } else {
                        TextView test = findViewById(R.id.symboleUpDown);
                        test.setText("⬆️");
                    }

                    if (attemptCount > 7) {
                        showGameOver(randomNumber);
                    }
                } else {
                    Toast.makeText(Game1Activity.this, "Please enter a number.", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView buttonGoHomeGame1 = findViewById(R.id.buttonGoHomeGame1);
        buttonGoHomeGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game1Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSuccess() {
        TextView buttonGoHome = findViewById(R.id.buttonGoHomeGame1);
        buttonGoHome.setVisibility(View.VISIBLE);

        Random random = new Random();
        int cashKey = random.nextInt(65) + 70;
        int oilKey = random.nextInt(45) + 40;
        int ferrariKey = random.nextInt(19) + 6;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) + cashKey);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) + oilKey);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) + ferrariKey);
        editor.apply();

        Toast.makeText(Game1Activity.this, "Congratulations! You won! Keys generated: cash=" + cashKey + ", oil=" + oilKey + ", ferrari=" + ferrariKey, Toast.LENGTH_LONG).show();
    }
    private void showGameOver(int guessedNumber) {
        Random random = new Random();
        int cashKey = random.nextInt(90) + 75;
        int oilKey = random.nextInt(38) + 23;
        int ferrariKey = random.nextInt(20) + 11;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Game1Activity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) - cashKey);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) - oilKey);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) - ferrariKey);
        editor.apply();

        if(sharedPreferences.getInt("cash", 0) <= 0) {editor.putInt("cash", 0);}
        if(sharedPreferences.getInt("oil", 0) <= 0) {editor.putInt("oil", 0);}
        if(sharedPreferences.getInt("ferrari", 0) <= 0) {editor.putInt("ferrari", 0);}
        editor.apply();

        Toast.makeText(Game1Activity.this, "Game Over! You have reached the maximum number of attempts. You lose : " + cashKey + ", oil=" + oilKey + ", ferrari=" + ferrariKey, Toast.LENGTH_LONG).show();

        TextView buttonGoHome = findViewById(R.id.buttonGoHomeGame1);
        buttonGoHome.setVisibility(View.VISIBLE);

        TextView numberGuess = findViewById(R.id.numberGuessString);
        numberGuess.setText("Number : " + guessedNumber);
    }
}

