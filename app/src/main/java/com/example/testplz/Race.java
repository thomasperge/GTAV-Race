package com.example.testplz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race extends AppCompatActivity {

    boolean gameStarted = false;
    String carSelectedName;
    Drawable carSelectedImage;

    String carOpponentName;
    Drawable carOpponentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race);

        LinearLayout chooseCarLayout = findViewById(R.id.layoutChooseCar);
        LinearLayout launchRaceLayout = findViewById(R.id.layoutLaunchRace);

        if (!gameStarted) {
            chooseCarLayout.setVisibility(View.VISIBLE);
            launchRaceLayout.setVisibility(View.GONE);
            displaySpinner();
            chooseOpponent();
        } else {
            chooseCarLayout.setVisibility(View.GONE);
            launchRaceLayout.setVisibility(View.VISIBLE);
        }

        // Car 1 Choose
        LinearLayout chooseCar1 = findViewById(R.id.chooseCar1);
        chooseCar1.setOnClickListener(v -> {
            highlightSelected(chooseCar1);
        });

        // Car 2 Choose
        LinearLayout chooseCar2 = findViewById(R.id.chooseCar2);
        chooseCar2.setOnClickListener(v -> {
            highlightSelected(chooseCar2);
        });

        // Car 3 Choose
        LinearLayout chooseCar3 = findViewById(R.id.chooseCar3);
        chooseCar3.setOnClickListener(v -> {
            highlightSelected(chooseCar3);
        });

        // Race Start
        TextView startRaceButton = findViewById(R.id.startRaceButton);
        startRaceButton.setOnClickListener(v -> {
            if(carOpponentName != null && carOpponentImage != null && carSelectedName != null && carSelectedImage != null) {
                chooseCarLayout.setVisibility(View.GONE);
                launchRaceLayout.setVisibility(View.VISIBLE);
                gameStarted = true;
                launchRace(carSelectedImage, carOpponentImage);
            } else {
                Toast.makeText(Race.this, "Vous n'avez pas choisie de voiture", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void launchRace(Drawable imagePLayer, Drawable imageOpponent) {
        Toast.makeText(Race.this, "Race start !!", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        LinearLayout displayStuff = findViewById(R.id.winLoseStuff);
        displayStuff.setVisibility(View.GONE);

        TextView buttonGoHomeRace = findViewById(R.id.buttonGoHomeRace);
        buttonGoHomeRace.setVisibility(View.GONE);
        buttonGoHomeRace.setOnClickListener(v -> {
            Intent intent = new Intent(Race.this, MainActivity.class);
            startActivity(intent);
        });

        Random random = new Random();
        int totalKm = random.nextInt(55) + 58;

        ImageView car1Image = findViewById(R.id.car1ImageProgressBar);
        ImageView car2Image = findViewById(R.id.car2ImageProgressBar);

        car1Image.setImageDrawable(imagePLayer); // Remplacez car1_image par le drawable correspondant à l'image de la voiture 1
        car2Image.setImageDrawable(imageOpponent);

        TextView totalKmRace = findViewById(R.id.totalKmRace);
        totalKmRace.setText("Total : " + totalKm + "km");

        TextView raceCar1Name = findViewById(R.id.raceCar1Name);
        raceCar1Name.setText(carSelectedName);

        ImageView raceCar1Image = findViewById(R.id.raceCar1Image);
        raceCar1Image.setImageDrawable(carSelectedImage);

        TextView raceCar2Name = findViewById(R.id.raceCar2Name);
        raceCar2Name.setText(carOpponentName);

        ImageView raceCar2Image = findViewById(R.id.raceCar2Image);
        raceCar2Image.setImageDrawable(carOpponentImage);

        TextView totalKmCar1 = findViewById(R.id.totalKmCar1);
        TextView totalKmCar2 = findViewById(R.id.totalKmCar2);

        TextView cashValueWinLose = findViewById(R.id.winLoseCashValue);
        TextView oilValueWinLose = findViewById(R.id.winLoseOilValue);
        TextView ferrariValueWinLose = findViewById(R.id.winLoseFerrariValue);

        final float[] totaKmPlayer = {0.0f};
        final float[] totaKmOpponent = {0.0f};

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        totalKmCar1.setText(decimalFormat.format(totaKmPlayer[0]) + "km");
        totalKmCar2.setText(decimalFormat.format(totaKmOpponent[0]) + "km");

        Handler handler = new Handler();
        Runnable updateKilometersRunnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                float totaKmPlayerRandom = random.nextFloat() * fixSpeed(carSelectedName) + 1.2f;
                float totaKmOpponentRandom = random.nextFloat() * fixSpeed(carOpponentName) + 1.2f;

                totaKmPlayer[0] += totaKmPlayerRandom;
                totaKmOpponent[0] += totaKmOpponentRandom;


                // Calculer la position relative des voitures
                float playerProgress = (float) totaKmPlayer[0] / totalKm * 100;
                float opponentProgress = (float) totaKmOpponent[0] / totalKm * 100;

                ProgressBar car1ProgressBar = findViewById(R.id.car1ProgressBar);
                ProgressBar car2ProgressBar = findViewById(R.id.car2ProgressBar);

                // Mettre à jour la largeur des ProgressBar en fonction de la position relative des voitures
                car1ProgressBar.setProgress((int) playerProgress);
                car2ProgressBar.setProgress((int) opponentProgress);

                /// Mettre à jour la position de l'image de la voiture 1
                int car1Progress = car1ProgressBar.getProgress();
                int car1MaxProgress = car1ProgressBar.getMax();
                RelativeLayout.LayoutParams car1LayoutParams = (RelativeLayout.LayoutParams) car1Image.getLayoutParams();
                car1LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                car1LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                int car1LeftMargin = (int) ((car1Progress / (float) car1MaxProgress) * car1ProgressBar.getWidth());
                int car1MaxLeftMargin = car1ProgressBar.getWidth() - car1Image.getWidth();
                car1LayoutParams.leftMargin = Math.min(car1LeftMargin, car1MaxLeftMargin);
                car1Image.setLayoutParams(car1LayoutParams);

                // Mettre à jour la position de l'image de la voiture 2
                int car2Progress = car2ProgressBar.getProgress();
                int car2MaxProgress = car2ProgressBar.getMax();
                RelativeLayout.LayoutParams car2LayoutParams = (RelativeLayout.LayoutParams) car2Image.getLayoutParams();
                car2LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                car2LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                int car2LeftMargin = (int) ((car2Progress / (float) car2MaxProgress) * car2ProgressBar.getWidth());
                int car2MaxLeftMargin = car2ProgressBar.getWidth() - car2Image.getWidth();
                car2LayoutParams.leftMargin = Math.min(car2LeftMargin, car2MaxLeftMargin);
                car2Image.setLayoutParams(car2LayoutParams);


                // Vérifier si la course est terminée
                if (totaKmPlayer[0] >= totalKm || totaKmOpponent[0] >= totalKm) {
                    float multiplicator = reward(carSelectedName, carOpponentName, totaKmPlayer[0], totaKmOpponent[0]);
                    if (multiplicator == 0) {multiplicator = 1;};

                    if (totaKmPlayer[0] >= totalKm){
                        // Opponent Win
                        TextView textLose = findViewById(R.id.winString);
                        textLose.setText("You Win !");
                        textLose.setTextColor(Color.GREEN);

                        displayStuff.setVisibility(View.VISIBLE);
                        buttonGoHomeRace.setVisibility(View.VISIBLE);

                        float randomCash = (random.nextInt(38) + 38) * multiplicator;
                        float randomOil = (random.nextInt(20) + 20) * multiplicator;
                        float randomFerrari = (random.nextInt(4) + 4) * multiplicator;

                        cashValueWinLose.setText("" + (int) randomCash);
                        oilValueWinLose.setText("" + (int) randomOil);
                        ferrariValueWinLose.setText("" + (int) randomFerrari);

                        editor.putInt("cash", sharedPreferences.getInt("cash", 0) + (int) randomCash);
                        editor.putInt("oil", sharedPreferences.getInt("oil", 0) + (int) randomOil);
                        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) + (int) randomFerrari);
                        editor.apply();

                        totalKmCar1.setText(totalKm + "km");
                    } else {
                        // Opponent Win
                        TextView textLose = findViewById(R.id.winString);
                        textLose.setText("You Lose !");
                        textLose.setTextColor(Color.RED);

                        displayStuff.setVisibility(View.VISIBLE);
                        buttonGoHomeRace.setVisibility(View.VISIBLE);

                        float randomCash = (random.nextInt(40) + 40) * multiplicator;
                        float randomOil = (random.nextInt(25) + 25) * multiplicator;
                        float randomFerrari = (random.nextInt(5) + 5) * multiplicator;

                        cashValueWinLose.setText("" + (int) randomCash);
                        oilValueWinLose.setText("" + (int) randomOil);
                        ferrariValueWinLose.setText("" + (int) randomFerrari);

                        editor.putInt("cash", sharedPreferences.getInt("cash", 0) + (int) randomCash);
                        editor.putInt("oil", sharedPreferences.getInt("oil", 0) + (int) randomOil);
                        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) + (int) randomFerrari);
                        editor.apply();

                        totalKmCar2.setText(totalKm + "km");
                    }
                    handler.removeCallbacks(this);
                    return;
                } else {
                    // Continuer la mise à jour toutes les 2 secondes
                    handler.postDelayed(this, 200);
                }

                totalKmCar1.setText(decimalFormat.format(totaKmPlayer[0]) + "km");
                totalKmCar2.setText(decimalFormat.format(totaKmOpponent[0]) + "km");
            }
        };

        // Démarrer la mise à jour des kilomètres toutes les 0.2 secondes
        handler.postDelayed(updateKilometersRunnable, 125);
    }


    private void displaySpinner() {
        Spinner carSpinner = findViewById(R.id.carSpinner);
        List<Car> carList = new ArrayList<>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getInt("motogp", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.motogp), "Moto Gp", sharedPreferences.getInt("motogp", 0)));}
        if (sharedPreferences.getInt("rs6", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.rs6), "Audi Rs6", sharedPreferences.getInt("rs6", 0)));}
        if (sharedPreferences.getInt("urus", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.urus), "Urus", sharedPreferences.getInt("urus", 0)));}
        if (sharedPreferences.getInt("gtr", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.gtr), "Nissan Gtr", sharedPreferences.getInt("gtr", 0)));}
        if (sharedPreferences.getInt("gt500", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.gt500), "Shelby Gt500", sharedPreferences.getInt("gt500", 0)));}
        if (sharedPreferences.getInt("aventador", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.aventador), "Aventador", sharedPreferences.getInt("aventador", 0)));}
        if (sharedPreferences.getInt("buggati", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.buggati), "Bugatti", sharedPreferences.getInt("buggati", 0)));}
        if (sharedPreferences.getInt("supra", 0) > 0) {carList.add(new Car(getResources().getDrawable(R.drawable.supra), "Supra", sharedPreferences.getInt("supra", 0)));}

        CarAdapter adapter = new CarAdapter(this, carList);
        carSpinner.setAdapter(adapter);

        carSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = (Car) parent.getItemAtPosition(position);
                String carName = selectedCar.getName();
                int carCount = selectedCar.getCount();
                Drawable carImage = selectedCar.getImage();

                Toast.makeText(Race.this, "Car selected: " + carName + " (x" + carCount + ")", Toast.LENGTH_SHORT).show();

                carSelectedName = carName;
                carSelectedImage = carImage;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Race.this, "No car selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void highlightSelected(LinearLayout selectedLayout) {
        // Définir le background avec bordure pour l'élément sélectionné
        selectedLayout.setBackgroundResource(R.drawable.view_border);

        // Parcourir tous les autres éléments et leur attribuer un background blanc
        LinearLayout chooseCar1 = findViewById(R.id.chooseCar1);
        LinearLayout chooseCar2 = findViewById(R.id.chooseCar2);
        LinearLayout chooseCar3 = findViewById(R.id.chooseCar3);

        if (selectedLayout != chooseCar1) {
            chooseCar1.setBackgroundColor(Color.WHITE);
        }
        if (selectedLayout != chooseCar2) {
            chooseCar2.setBackgroundColor(Color.WHITE);
        }
        if (selectedLayout != chooseCar3) {
            chooseCar3.setBackgroundColor(Color.WHITE);
        }

        // Récupérer le nom et l'image de la voiture sélectionnée
        TextView carTitle;
        ImageView carImage;

        if (selectedLayout == chooseCar1) {
            carTitle = selectedLayout.findViewById(R.id.car1Title);
            carImage = selectedLayout.findViewById(R.id.car1Image);
        } else if (selectedLayout == chooseCar2) {
            carTitle = selectedLayout.findViewById(R.id.car2Title);
            carImage = selectedLayout.findViewById(R.id.car2Image);
        } else if (selectedLayout == chooseCar3) {
            carTitle = selectedLayout.findViewById(R.id.car3Title);
            carImage = selectedLayout.findViewById(R.id.car3Image);
        } else {
            return; // Sortir de la fonction si aucun élément correspondant n'est trouvé
        }

        if (carTitle != null && carImage != null) {
            String selectedCarName = carTitle.getText().toString();
            Drawable selectedCarImage = carImage.getDrawable();

            carOpponentName = selectedCarName;
            carOpponentImage = selectedCarImage;

            Toast.makeText(Race.this, "Opposant sélectionnée : " + selectedCarName, Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseOpponent() {
        Random random = new Random();

        TextView[] carTitles = {findViewById(R.id.car1Title), findViewById(R.id.car2Title), findViewById(R.id.car3Title)};
        ImageView[] carImages = {findViewById(R.id.car1Image), findViewById(R.id.car2Image), findViewById(R.id.car3Image)};

        String[] carNames = {"Moto Gp", "Audi Rs6", "Urus", "Nissan Gtr", "Shelby Gt500", "Aventador", "Buggati", "Supra"};
        int[] carImagesResources = {R.drawable.motogp, R.drawable.rs6, R.drawable.urus, R.drawable.gtr, R.drawable.gt500, R.drawable.aventador, R.drawable.buggati, R.drawable.supra};

        for (int i = 0; i < carTitles.length; i++) {
            int randomCar = random.nextInt(carNames.length);
            carTitles[i].setText(carNames[randomCar]);
            carImages[i].setImageResource(carImagesResources[randomCar]);
        }
    }

    private float fixSpeed(String carName) {
        if (carName == "Moto Gp"){return 0.9f;}
        else if (carName == "Audi Rs6"){return 1.2f;}
        else if (carName == "Urus"){return 1.5f;}
        else if (carName == "Nissan Gtr"){return 1.8f;}
        else if (carName == "Shelby Gt500"){return 2.2f;}
        else if (carName == "Aventador"){return 2.6f;}
        else if (carName == "Bugatti"){return 3.2f;}
        else if (carName == "Supra"){return 3.8f;}
        else {return 0f;}
    }

    private float reward(String carNamePlayer, String carNameOpponent, float totalKmPlayer, float totalKmOpponent) {
        float carPlayerValue = fixSpeed(carNamePlayer);
        float carOpponentValue = fixSpeed(carNameOpponent);

        if (totalKmPlayer > totalKmOpponent) {
            // Player Win
            if (carPlayerValue > carOpponentValue) {
                return (carPlayerValue * carOpponentValue) - (carPlayerValue/2.85f);
            } else if (carPlayerValue == carOpponentValue) {
                return (carPlayerValue * carOpponentValue) - (carPlayerValue/2.5f);
            } else {
                return (carPlayerValue * carOpponentValue) * 1.75f;
            }
        } else {
            // Player Lose
            if (carPlayerValue > carOpponentValue) {
                return (((carPlayerValue * carOpponentValue) - (carPlayerValue/4.5f)) * -1) - (carPlayerValue*2.5f);
            } else if (carPlayerValue == carOpponentValue) {
                return (((carPlayerValue * carOpponentValue) - (carPlayerValue/2.5f)) * -1);
            } else {
                return (((carPlayerValue * carOpponentValue) + carOpponentValue) * -1) - (carPlayerValue);
            }
        }
    }
}
