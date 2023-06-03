package com.example.testplz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView button = findViewById(R.id.buttonGame1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Game1Activity.class);
                startActivity(intent);
            }
        });

        TextView button2 = findViewById(R.id.buttonGame2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Game2Activity.class);
                startActivity(intent);
            }
        });

        TextView button3 = findViewById(R.id.buttonShop);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Shop.class);
                startActivity(intent);
            }
        });

        TextView button4 = findViewById(R.id.buttonInventory);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inventory.class);
                startActivity(intent);
            }
        });

        TextView button5 = findViewById(R.id.buttonRace);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Race.class);
                startActivity(intent);
            }
        });

        displayMoney();

        ImageView imageFrancklin = findViewById(R.id.imageFrancklin);
        imageFrancklin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
                imageFrancklin.startAnimation(zoomAnimation);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Random random = new Random();
                int randomNumber = random.nextInt(8);

                if (randomNumber == 0) {
                    editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) + 1);
                    editor.apply();

                    TextView textMoney = findViewById(R.id.ferrariValue);
                    textMoney.setText(String.valueOf(sharedPreferences.getInt("ferrari", 0)));
                } else if (randomNumber == 1 || randomNumber == 2) {
                    editor.putInt("oil", sharedPreferences.getInt("oil", 0) + 1);
                    editor.apply();

                    TextView textMoney = findViewById(R.id.oilValue);
                    textMoney.setText(String.valueOf(sharedPreferences.getInt("oil", 0)));
                } else {
                    editor.putInt("cash", sharedPreferences.getInt("cash", 0) + 1);
                    editor.apply();

                    TextView textMoney = findViewById(R.id.cashValue);
                    textMoney.setText(String.valueOf(sharedPreferences.getInt("cash", 0)));
                }
            }
        });
    }

    private int moneyValue = 0;
    private int oilValue = 0;
    private int ferrariValue = 0;

    protected void displayMoney() {
        // Pour enregistrer les valeurs
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Money
        if (!sharedPreferences.contains("cash")) {sharedPreferences.edit().putInt("cash", 0).apply();}
        if (!sharedPreferences.contains("oil")) {sharedPreferences.edit().putInt("oil", 0).apply();}
        if (!sharedPreferences.contains("ferrari")) {sharedPreferences.edit().putInt("ferrari", 0).apply();}

        // Item
        if (!sharedPreferences.contains("motogp")) {sharedPreferences.edit().putInt("motogp", 0).apply();}
        if (!sharedPreferences.contains("rs6")) {sharedPreferences.edit().putInt("rs6", 0).apply();}
        if (!sharedPreferences.contains("urus")) {sharedPreferences.edit().putInt("urus", 0).apply();}
        if (!sharedPreferences.contains("gtr")) {sharedPreferences.edit().putInt("gtr", 0).apply();}
        if (!sharedPreferences.contains("gt500")) {sharedPreferences.edit().putInt("gt500", 0).apply();}
        if (!sharedPreferences.contains("aventador")) {sharedPreferences.edit().putInt("aventador", 0).apply();}
        if (!sharedPreferences.contains("buggati")) {sharedPreferences.edit().putInt("buggati", 0).apply();}
        if (!sharedPreferences.contains("supra")) {sharedPreferences.edit().putInt("supra", 0).apply();}

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getInt("cash", 0) <= 0) {editor.putInt("cash", 0);}
        if(sharedPreferences.getInt("oil", 0) <= 0) {editor.putInt("oil", 0);}
        if(sharedPreferences.getInt("ferrari", 0) <= 0) {editor.putInt("ferrari", 0);}
        editor.apply();

        // Pour récupérer les valeurs
        moneyValue = sharedPreferences.getInt("cash", 0);
        oilValue = sharedPreferences.getInt("oil", 0);
        ferrariValue = sharedPreferences.getInt("ferrari", 0);

        TextView textMoney = findViewById(R.id.cashValue);
        textMoney.setText(String.valueOf(moneyValue));

        TextView textOil = findViewById(R.id.oilValue);
        textOil.setText(String.valueOf(oilValue));

        TextView textFerrari = findViewById(R.id.ferrariValue);
        textFerrari.setText(String.valueOf(ferrariValue));
    }
}
