package com.example.testplz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        TextView buttonGoHomeShop = findViewById(R.id.StartRaceButton);
        buttonGoHomeShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setupPopupButton(R.id.motoGpButton, "Moto GP", "motogp", 45, 35, 15, 10, 5, 10);
        setupPopupButton(R.id.rs6Button, "Audi rs6", "rs6", 175, 125, 35, 24, 10, 8);
        setupPopupButton(R.id.urusButton, "Urus", "urus", 280, 145, 60, 34, 24, 19);
        setupPopupButton(R.id.gtrButton, "Nissan GTR", "gtr", 640, 200, 95, 55, 50, 29);
        setupPopupButton(R.id.gt500Button, "Shelby Gt500", "gt500", 1170, 380, 160, 80, 78, 45);
        setupPopupButton(R.id.aventadorButton, "Aventador", "aventador", 1790, 570, 200, 100, 98, 52);
        setupPopupButton(R.id.buggatiButton, "Buggati Chiron", "buggati", 2695, 710, 260, 120, 170, 80);
        setupPopupButton(R.id.supraButton, "Toyota Supra", "supra", 3975, 1250, 300, 180, 168, 88);
    }

    private void setupPopupButton(int buttonId, final String title, final String idVehicle, final int cashMin, final int cashMax, final int oilMin, final int oilMax, final int ferrariMin, final int ferrariMax) {
        TextView popupButton = findViewById(buttonId);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(idVehicle, title, getRandomValue(cashMin, cashMax), getRandomValue(oilMin, oilMax), getRandomValue(ferrariMin, ferrariMax));
            }
        });
    }

    private void showPopup(String idVehicle, String name, int priceCash, int priceOil, int priceFerrari) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Acheter l'item")
                .setMessage("Voulez-vous acheter " + name + " ?\n\nCash: " + priceCash + "\nOil: " + priceOil + "\nFerrari: " + priceFerrari)
                .setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Shop.this);

                        if(sharedPreferences.getInt("cash", 0) < priceCash || sharedPreferences.getInt("oil", 0) < priceOil || sharedPreferences.getInt("ferrari", 0) < priceFerrari) {
                            Toast.makeText(Shop.this, "You don't have enought stuff to buy :  " + name +  "...", Toast.LENGTH_LONG).show();
                        } else {
                            deleteMoney(priceCash, priceOil, priceFerrari);
                            sharedPreferences.edit().putInt(idVehicle, sharedPreferences.getInt(idVehicle, 0) + 1).apply();
                            Toast.makeText(Shop.this, "You buy " + name +  " !", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteMoney(int priceCash, int priceOil, int priceFerrari) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) - priceCash);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) - priceOil);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) - priceFerrari);

        if(sharedPreferences.getInt("cash", 0) <= 0) {editor.putInt("cash", 0);}
        if(sharedPreferences.getInt("oil", 0) <= 0) {editor.putInt("oil", 0);}
        if(sharedPreferences.getInt("ferrari", 0) <= 0) {editor.putInt("ferrari", 0);}
        editor.apply();

    }

    private int getRandomValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) + min; // Valeur aléatoire entre 1 et 10 inclus
    }
}
