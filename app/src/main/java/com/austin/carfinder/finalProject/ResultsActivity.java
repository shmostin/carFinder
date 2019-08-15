package com.austin.carfinder.finalProject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private String apiKey = BuildConfig.ApiKey;
    private static final String TAG = "ResultsActivity";

    private static final String MARKET_CHECKER_URL_KEY = "http://api.marketcheck.com/v1/search?api_key=";
    private static final String MARKET_CHECKER_URL_YEAR = "&year=";
    private static final String MARKET_CHECKER_URL_MAKE = "&make=";
    private static final String MARKET_CHECKER_URL_MODEL = "&model=";
    private static final String MARKET_CHECKER_URL_LAT = "&latitude=";
    private static final String MARKET_CHECKER_URL_LONG = "&longitude=";
    private static final String MARKET_CHECKER_URL_END = "&radius=10000&car_type=used&start=0&rows=10";

    int totalPrice = 0;
    int totalCars = 0;

    private final String completeURL = MARKET_CHECKER_URL_KEY + apiKey
            + MARKET_CHECKER_URL_YEAR + MainActivityFinalProject.selectedYear
            + MARKET_CHECKER_URL_MAKE + MainActivityFinalProject.selectedMake
            + MARKET_CHECKER_URL_MODEL + MainActivityFinalProject.selctedModel
            + MARKET_CHECKER_URL_LAT + MainActivityFinalProject.latitude
            + MARKET_CHECKER_URL_LONG + MainActivityFinalProject.longitude
            + MARKET_CHECKER_URL_END;

    private JSONObject resJsonObject;


    ArrayList<CarInfoDataModel> carInfoDataModels;
    private static CustomListViewAdapter carAdapter;
    ArrayList<CarInfoDataModel> carInfoArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_results);


        //set text view for make
        TextView makeTextView = findViewById(R.id.textViewMake);
        makeTextView.setText(MainActivityFinalProject.selectedMake);

        //set text view for model
        TextView modelTextView = findViewById(R.id.textViewModel);
        modelTextView.setText(MainActivityFinalProject.selctedModel);

        //set text view for year
        TextView yearTextView = findViewById(R.id.textViewYear);
        yearTextView.setText(MainActivityFinalProject.selectedYear);

        TextView detailTextView = findViewById(R.id.detailTextView);

        startCarListThread();


        //custom list view adapter
        carAdapter = new CustomListViewAdapter(getApplicationContext(), R.layout.car_row_item, carInfoArray);


        //set the car_info_listView with the car adapter list view
        ListView listView = findViewById(R.id.car_info_listView);
        listView.setAdapter(carAdapter);
        addClickListener();


    }


    /**
     * Starts a new thread to call the api and retrieve the listing of cars.
     */
    private void startCarListThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    buildCarInfoList(getCarSalesInfo());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * add a click listener to each of the listView items that will take the user to the car listing
     */
    private void addClickListener() {
        ListView carResults = (ListView) (findViewById(R.id.car_info_listView));
        carResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarInfoDataModel currentCarInfo = carInfoArray.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentCarInfo.getDealerUrl()));
                startActivity(intent);
            }
        });
    }


    /**
     * take the user back to the previous page from the error generated
     * @param v the view
     */
    public void backToMainProjectActivity(View v) {
        Intent intent = new Intent(this, MainActivityFinalProject.class);
        startActivity(intent);
    }


    /**
     * Builds and Sends the request to the 'marketChecker' api
     * @return the JSON object of found car results
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject getCarSalesInfo() throws IOException, JSONException {
        System.out.println(completeURL);
        //create and execute the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(completeURL)
                .get()
                .addHeader("Host", "marketcheck-prod.apigee.net")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "1e98cec0-847c-44cd-8a25-b588904908a2")
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Bad Response " + response);
        }

        //grab the response as a string
        final String responseData = response.body().string();

        Log.i(TAG, responseData);

        //create the json object for parsing
        try {
            resJsonObject = new JSONObject(responseData);
            if (resJsonObject.getInt("num_found") == 0) {
                Log.i(TAG, "no cars were found");

                displayNoCarsFoundError();

            }

        } catch (JSONException e) {
            Log.i(TAG, "problem storing response as JSON Object: " + e.toString());
        }

        return resJsonObject;
    }


    /**
     * Displays the 'createNoCarFoundMessage' to display if the call to the api does not find any results.
     */
    private void displayNoCarsFoundError() {
        Handler handler = new Handler(Looper.getMainLooper());
        final View thisRootView = findViewById(R.id.car_result_root);

        handler.post(new Runnable() {
            @Override
            public void run() {
                createNoCarFoundMessage(thisRootView);
            }
        });
    }


    /**
     * Creates an error message if the api call does not find any results
     * @param view
     */
    private void createNoCarFoundMessage(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.no_vehicle_found, null);

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


    /**
     * Takes in the JSON object returned from the api call and turns it into a list of CarInfoDataModel objects
     * @param vehicleJson
     * @throws JSONException
     */
    private void buildCarInfoList(JSONObject vehicleJson) throws JSONException {

        //create a temp array for adding "price" to our list view
        carInfoDataModels = new ArrayList<>();

        JSONArray jsonArray = resJsonObject.getJSONArray("listings");

        //Loop through all the json objects and pull out the info we want
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentObject = jsonArray.getJSONObject(i);
            String price;
            String miles;
            String imageUrl;
            try {
                price = currentObject.getString("price");
                miles = currentObject.getString("miles");
                imageUrl = currentObject.getJSONObject("media").getJSONArray("photo_links").get(0).toString();
                Log.i(TAG, "image url: " + imageUrl);
                totalPrice += Integer.parseInt(price);
                totalCars += 1;

            } catch (Exception e) {
                continue;
            }

            carInfoDataModels.add(new CarInfoDataModel(price, miles,
                    currentObject.getJSONObject("build").getString("trim"),
                    currentObject.getJSONObject("dealer").getString("name"),
                    imageUrl,
                    currentObject.getString("vdp_url")));
        }
        updateCarListUI();
    }


    /**
     * Creates a Handler to update the UI with the found car items
     */
    private void updateCarListUI() {


        // Attach a Handler to the UI (Main) thread so we can update the UI
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new

                             Runnable() {
                                 @Override
                                 public void run() {
//                             Log.i(TAG, responseData);

                                     //clear any previously stored pricing
                                     //add all price info to the carInfo to the list adapter
                                     carAdapter.clear();
                                     carAdapter.addAll(carInfoDataModels);
                                 }
                             }
        );
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
