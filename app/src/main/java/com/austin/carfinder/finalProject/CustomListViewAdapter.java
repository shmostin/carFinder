package com.austin.carfinder.finalProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CustomListViewAdapter extends ArrayAdapter<CarInfoDataModel> {

    private final String TAG = "customListViewAdapter";
    private ArrayList<CarInfoDataModel> carData;
    Context myContext;
    ViewsHolder viewsHolder;

    private static class ViewsHolder {
        TextView txtPrice;
        TextView txtMiles;
        TextView txtTrim;
        TextView txtLink;
        ImageView carImage;
    }







    public CustomListViewAdapter(Context context, int resource, ArrayList<CarInfoDataModel> carData) {
        super(context, resource, carData);
        this.carData = carData;
        this.myContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CarInfoDataModel carInfoDataModel = getItem(position);



        final View result;

        if (convertView == null) {
            viewsHolder = new ViewsHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.car_row_item, parent, false);

            viewsHolder.txtPrice = convertView.findViewById(R.id.price);
            viewsHolder.txtMiles = convertView.findViewById(R.id.miles);
            viewsHolder.txtTrim = convertView.findViewById(R.id.trim);
            viewsHolder.txtLink = convertView.findViewById(R.id.dealership);
            viewsHolder.carImage = convertView.findViewById(R.id.car_image);



            result = convertView;

            convertView.setTag(viewsHolder);
        } else {
            viewsHolder = (ViewsHolder) convertView.getTag();
            result = convertView;
        }

        viewsHolder.txtPrice.setText("PRICE: $" + carInfoDataModel.getPrice());
        viewsHolder.txtMiles.setText("MILEAGE: " + carInfoDataModel.getMiles());
        viewsHolder.txtTrim.setText("TRIM PACKAGE: " + carInfoDataModel.getTrim());
        viewsHolder.txtLink.setText("DEALERSHIP: " + carInfoDataModel.getDealerWebsite());
        Picasso.get().load(carInfoDataModel.getCarImageUrl()).resize(285,285).into(viewsHolder.carImage);
//        return super.getView(position, convertView, parent);
        return convertView;
    }



}
