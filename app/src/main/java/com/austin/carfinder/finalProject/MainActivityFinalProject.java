package com.austin.carfinder.finalProject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivityFinalProject extends AppCompatActivity {
    private String TAG = "FinalProjectMainActivity";
    private static final String LEVEL_BLANK = "No Model Found!";
    public static String selectedMake;
    public static String selctedModel;
    public static String selectedYear;
    public static double longitude;
    public static double latitude;
    private static final int INITIAL_REQUEST=1339;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private FusedLocationProviderClient client;





    @Override
    protected void onCreate(Bundle savedInstanceState) {



        requestPermissions();
        client = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},INITIAL_REQUEST);
            return;
        }

        client.getLastLocation().addOnSuccessListener(MainActivityFinalProject.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    System.out.println("location is not null: " + location.toString());

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        });



        Log.i(TAG, "onCreate");
        Log.i(TAG, "lat: " + latitude + " long: " + longitude);


        setContentView(R.layout.final_project_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_project_main);

        final Spinner spinnerModel = findViewById(R.id.spinnerModel2);
        final Spinner spinnerBrand = findViewById(R.id.spinnerBrand);
        final Spinner spinnerYear = findViewById(R.id.spinnerYear);

        //set up the first spinner with the correct array adapter of makes
        ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(this, R.array.makes, android.R.layout.simple_spinner_item);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapterBrand);

        Log.i(TAG, "loading the spinners");
        LoadSpinners loadSpinners = new LoadSpinners();
        loadSpinners.execute();
        Log.i(TAG, "Done loading the spinners");

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);


        //create an onItemSelectedListener for the year spinner
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();

//                Toast toast = Toast.makeText(parent.getContext(), selectedYear, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
//                toast.show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
            }
        });

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, INITIAL_PERMS, 1);
    }







    public void finalProjectAcknowledgmentButton(View view) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.final_project_acknowledgment, null);

        //create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);

        popupView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    public void submitCarSearch(View v) {
        Intent intent = new Intent(this, com.austin.carfinder.finalProject.ResultsActivity.class);
        startActivity(intent);
    }




    /*
     * select the correct array of models based on the given car make
     * @param givenMake the make of the cars selected
     * @return the int correlating to the array of models
     */
    public void selectModelFromMake(String givenMake, Spinner secondSpinner) {

        String models = LEVEL_BLANK;
        int foundModels = R.array.acura_models;
        switch (givenMake) {
            case "Acura":
                foundModels = R.array.acura_models;
                break;
            case "Alfa Romeo":
                foundModels = R.array.alpha_romeo_models;
                break;
            case "Audi":
                foundModels = R.array.audi_models;
                break;
            case "BMW":
                foundModels = R.array.bmw_models;
                break;
            case "Bently":
                foundModels = R.array.bently_models;
                break;
            case "Buik":
                foundModels = R.array.buik_models;
                break;
            case "Cadillac":
                foundModels = R.array.cadillac_models;
                break;
            case "Chevrolet":
                foundModels = R.array.chevrolet_models;
                break;
            case "Chrysler":
                foundModels = R.array.chrysler_models;
                break;
            case "Dodge":
                foundModels = R.array.dodge_models;
                break;
            case "Fiat":
                foundModels = R.array.fiat_models;
                break;
            case "Ford":
                foundModels = R.array.ford_models;
                break;
            case "GMC":
                foundModels = R.array.gmc_models;
                break;
            case "Honda":
                foundModels = R.array.honda_models;
                break;
            case "Hyundai":
                foundModels = R.array.hyundai_models;
                break;
            case "Infiniti":
                foundModels = R.array.infiniti_models;
                break;
            case "Jaguar":
                foundModels = R.array.jaguar_models;
                break;
            case "Jeep":
                foundModels = R.array.jeep_models;
                break;
            case "Kia":
                foundModels = R.array.kia_models;
                break;
            case "Land Rover":
                foundModels = R.array.land_rover_models;
                break;
            case "Lexus":
                foundModels = R.array.lexus_models;
                break;
            case "Maserati":
                foundModels = R.array.maserati_models;
                break;
            case "Mazda":
                foundModels = R.array.mazda_models;
                break;
            case "Mercedes-Benz":
                foundModels = R.array.mercedes_benz_models;
                break;
            case "Mercury":
                foundModels = R.array.mercury_models;
                break;
            case "Mini":
                foundModels = R.array.mini_models;
                break;
            case "Mitsubishi":
                foundModels = R.array.mitsubishi_models;
                break;
            case "Nissan":
                foundModels = R.array.nissan_models;
                break;
            case "Porsche":
                foundModels = R.array.porsche_models;
                break;
            case "Rolls-Royce":
                foundModels = R.array.rolls_royce_models;
                break;
            case "Saab":
                foundModels = R.array.saab_models;
                break;
            case "Scion":
                foundModels = R.array.scion_models;
                break;
            case "Smart":
                foundModels = R.array.smart_models;
                break;
            case "Subaru":
                foundModels = R.array.subaru_models;
                break;
            case "Tesla":
                foundModels = R.array.tesla_models;
                break;
            case "Toyota":
                foundModels = R.array.toyota_models;
                break;
            case "Volkswagen":
                foundModels = R.array.volkswagen_models;
                break;
            case "Volvo":
                foundModels = R.array.volvo_models;
                break;
        }

        //set the adapter for the second spinner
        ArrayAdapter<CharSequence> adapterModel = ArrayAdapter.createFromResource(this, foundModels, android.R.layout.simple_spinner_item);
        adapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondSpinner.setAdapter(adapterModel);
    }

    private class LoadSpinners extends AsyncTask {


        final Spinner spinnerModel = findViewById(R.id.spinnerModel2);
        final Spinner spinnerBrand = findViewById(R.id.spinnerBrand);
        final Spinner spinnerYear = findViewById(R.id.spinnerYear);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            //set up the onItemSelectedListener for the first spinner
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMake = parent.getItemAtPosition(position).toString();

//                Toast toast2 = Toast.makeText(parent.getContext(), selectedMake, Toast.LENGTH_SHORT);
//                toast2.setGravity(Gravity.TOP|Gravity.CENTER, 50, 50);
//                toast2.show();

                //select the correct adapterArray and "set" the second spinner
                selectModelFromMake(selectedMake, spinnerModel);
                select();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }


            /**
             * set the itemListener for the second spinner
             */
            private void select() {
                spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selctedModel = parent.getItemAtPosition(position).toString();
//                        makeText(parent.getContext(), selctedModel, Toast.LENGTH_SHORT).show();Toast.
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //do nothing
                    }
                });
            }
        });

        return null;
        }
    }

    @Override
    protected void onPostResume() {
//        Log.i(TAG, "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onResume() {
//        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
//        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
//        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
//        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
//        Log.i(TAG, "onRestart");
        super.onRestart();
    }
}

