package com.cossu.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cossu.myapplication.R;
import com.cossu.myapplication.Restaurant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class InfoWindowCustom implements GoogleMap.InfoWindowAdapter, Target{
    Context context;
    LayoutInflater inflater;
    ImageView mapPhoto ;
    Marker marker ;
    public InfoWindowCustom(Context context) {
        this.context = context;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
    @Override
    public View getInfoWindow(Marker marker) {

        this.marker = marker;
        Gson gson = new Gson();
        Restaurant monRestau = gson.fromJson (marker.getSnippet(), Restaurant.class);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // R.layout.echo_info_window is a layout in my
        // res/layout folder. You can provide your own
        View v = inflater.inflate(R.layout.map_restau_display, null);

        TextView mapTitle =  v.findViewById(R.id.mapRestauTitre);
        TextView mapAdresse =  v.findViewById(R.id.mapRestauAdresse);
        TextView mapTel =  v.findViewById(R.id.mapRestauTelephone);
        this.mapPhoto = v.findViewById(R.id.mapRestauPhoto);


        String numTelRestau = "Tel : " +monRestau.getTel();

        mapTitle.setText(monRestau.getNom());
        mapAdresse.setText(monRestau.getAdresse());
        mapTel.setText(numTelRestau);


        Picasso.with(context).
                load(monRestau.getPhoto()).
                into(this);

        return v;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        this.mapPhoto.setImageBitmap(bitmap);
        if (marker == null)
        {
            return;
        }

        if (!marker.isInfoWindowShown())
        {
            return;
        }

        // If Info Window is showing, then refresh it's contents:

        marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
        marker.showInfoWindow();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}