package com.example.testplz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Inventory extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        displayArea(R.id.motoGpArea, R.id.motoGPNumber, "Moto GP", "motogp");
        displayArea(R.id.rs6Area, R.id.rs6Number, "Audi rs6", "rs6");
        displayArea(R.id.urusArea, R.id.urusNumber, "Urus", "urus");
        displayArea(R.id.gtrArea, R.id.gtrNumber, "Nissan GTR", "gtr");
        displayArea(R.id.gt500Area, R.id.gt500Number, "Shelby Gt500", "gt500");
        displayArea(R.id.aventadorArea, R.id.aventadorNumber, "Aventador", "aventador");
        displayArea(R.id.buggatiArea, R.id.buggatiNumber, "Buggati Chiron", "buggati");
        displayArea(R.id.supraArea, R.id.supraNumber, "Toyota Supra", "supra");

        TextView buttonGoHome = findViewById(R.id.GoHomeGarage);
        buttonGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayArea(int areaId, int itemNumberId, String name, String id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int numberItem = sharedPreferences.getInt(id, 0);

        if (numberItem > 0) {
            TextView numberString = findViewById(itemNumberId);
            numberString.setText("x" + numberItem);

            LinearLayout area = findViewById(areaId);
            area.setVisibility(View.VISIBLE);
        }
    }
}
