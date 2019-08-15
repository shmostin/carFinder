package com.austin.carfinder.finalProject;//package edu.neu.madcourse.austinvanderwel.finalProject;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import edu.neu.madcourse.austinvanderwel.R;
//
//public class LoadSpinnersTask extends AsyncTask {
//
//
//    private Context context;
////    final Spinner spinnerBrand = findViewById(R.id.spinnerBrand);
////    final Spinner spinnerModel = findViewById(R.id.spinnerModel2);
////    final Spinner spinnerYear = findViewById(R.id.spinnerYear);
//
//
//    public LoadSpinnersTask(Context myContext) {
//        this.context = myContext;
//    }
//
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
//    }
//
//    @Override
//    protected void onProgressUpdate(Object[] values) {
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected Object doInBackground(Object[] objects) {
//        //set up the onItemSelectedListener for the first spinner
//        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedMake = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), selectedMake, Toast.LENGTH_SHORT).show();
//
//                //select the correct adapterArray and "set" the second spinner
//                selectModelFromMake(selectedMake, spinnerModel);
//                select();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //do nothing
//            }
//
//
//            /**
//             * set the itemListener for the second spinner
//             */
//            private void select() {
//                spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        selctedModel = parent.getItemAtPosition(position).toString();
//                        Toast.makeText(parent.getContext(), selctedModel, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        //do nothing
//                    }
//                });
//            }
//        });
//    }
//}
