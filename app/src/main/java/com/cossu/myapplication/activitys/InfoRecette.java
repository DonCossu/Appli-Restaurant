package com.cossu.myapplication.activitys;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cossu.myapplication.R;
import com.cossu.myapplication.Recette;
import com.cossu.myapplication.Restaurant;
import com.cossu.myapplication.adapter.InfoWindowCustom;
import com.cossu.myapplication.ws.WSManager;
import com.cossu.myapplication.ws.WsListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.HashMap;
import java.util.Map;



public class InfoRecette extends AppCompatActivity implements WsListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private int idRecette;
    private Context mContext = this;
    private Recette laRecette;
    private SharedPreferences preferences;
    private Menu menu;

    // For map
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker monMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_recette);

        idRecette = getIntent().getIntExtra("id", 0);

        WSManager manager = new WSManager(this);

        Map<String, String> mesInfosRecette = new HashMap<>();
        mesInfosRecette.put("id", String.valueOf(idRecette));
        manager.sendRequest(1, "infoRecette/"+idRecette, mesInfosRecette);

        // For map
        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void errorRequest(int id) {

    }

    @Override
    public void successRequest(int id, String data) {

        TextView champNom = findViewById(R.id.nomRecette);
        final RelativeLayout photo = findViewById(R.id.recettePhoto);
        ImageView imageNote0 = findViewById(R.id.recetteInfoNote0);
        ImageView imageNote1 = findViewById(R.id.recetteInfoNote1);
        ImageView imageNote2 = findViewById(R.id.recetteInfoNote2);
        ImageView imageNote3 = findViewById(R.id.recetteInfoNote3);
        ImageView imageNote4 = findViewById(R.id.recetteInfoNote4);


        Gson googleJson = new Gson();
        laRecette = googleJson.fromJson(data, Recette.class);

        setTitle(laRecette.getTitle());

        Restaurant restau = new Restaurant();
        restau.setNom (laRecette.getRestaurant().getNom());
        restau.setAdresse (laRecette.getRestaurant().getAdresse());
        restau.setPhoto(laRecette.getRestaurant().getPhoto());
        restau.setTel (laRecette.getRestaurant().getTel());
        Gson gson = new Gson();
        String restauString = gson.toJson(restau);

        // Map
        LatLng latLng = new LatLng(laRecette.getRestaurant().getLat(), laRecette.getRestaurant().getLng());
        float zoom = 12;
        // pour afficher la map avec les données instanciées
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        // pour ajouter un marqueur pour le restaurant
        // position() : pour placer le marqueur au bon endroit (en fonction des coordonnées instanciées)
        // title() : pour ajouter un nom
        monMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(laRecette.getRestaurant().getLat(),laRecette.getRestaurant().getLng()))
                .title(laRecette.getRestaurant().getNom())
                .snippet(restauString));

        googleMap.setOnMarkerClickListener(this);

        googleMap.setInfoWindowAdapter(new InfoWindowCustom(this));




        ////////////////
        /////// HAUT DE PAGE (PHOTO, NOTE, FAV)
        ////////////////
        champNom.setText(laRecette.getTitle());
        Picasso.with(mContext).
                load(String.valueOf(laRecette.getPhoto())).
                into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        photo.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        if(laRecette.getNote() >= 1) {
            imageNote0.setImageResource(R.drawable.start_on);
        }
        if(laRecette.getNote() >= 2) {
            imageNote1.setImageResource(R.drawable.start_on);
        }
        if(laRecette.getNote() >= 3) {
            imageNote2.setImageResource(R.drawable.start_on);
        }
        if(laRecette.getNote() >= 4) {
            imageNote3.setImageResource(R.drawable.start_on);
        }
        if(laRecette.getNote() >= 5) {
            imageNote4.setImageResource(R.drawable.start_on);
        }


        System.out.println(laRecette.getId());
        System.out.println(laRecette.getCalories());
        System.out.println(laRecette.getTempsCookRime());
        System.out.println(laRecette.getTempsPreparation());


        ////////////////
        /////// MILIEU DE PAGE (INFOS PREPA, COOK TIME, CALORIES)
        ////////////////

        TextView champPreparation = findViewById(R.id.preparationValeur);
        TextView champCookTime = findViewById(R.id.cooktimeValeur);
        TextView champCalories = findViewById(R.id.caloriesValeur);


        String affichageCalories = laRecette.getCalories() + " kcal";
        String affichageCookTime = laRecette.getTempsCookRime() + " min";
        String affichagePreparation= laRecette.getTempsPreparation() + " min";

        champPreparation.setText(affichagePreparation);
        champCookTime.setText(affichageCookTime);
        champCalories.setText(affichageCalories);



        ////////////////
        /////// BAS DE PAGE (INGREDIENTS, INSTRUCTIONS)
        ////////////////

        TextView champIngredients = findViewById(R.id.ingredientsValeur);
        TextView champInstructions = findViewById(R.id.instructionsValeur);

        champIngredients.setText(laRecette.getIngredients());
        champInstructions.setText(laRecette.getInstructions());

    }


    // Gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.inforecette, menu);
        preferences = getSharedPreferences("APP", MODE_PRIVATE);
        String sharedFavoris = preferences.getString("favoris", null);
        if(sharedFavoris != null) {
            String[] splitArray = sharedFavoris.split(",");
            for(int i = 0; i< splitArray.length;i++){
                if(splitArray[i].equals( String.valueOf(idRecette))) {
                    MenuItem coeurFav = menu.findItem(R.id.addFavoris);
                    coeurFav.setIcon(R.drawable.like);
                }
            }
        }


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addFavoris:

                MenuItem coeurFav = menu.findItem(R.id.addFavoris);
                preferences = getSharedPreferences("APP", MODE_PRIVATE);
                String mesFavorisActuels = preferences.getString("favoris", "");
                boolean isIntoArray = false; // Vérifie si id Recette existe dans les favs actuels

                    String[] splitArray = mesFavorisActuels.split(","); // Split des favs actuels
                    String nouveauxFavs = ""; // Préparation des nouveaux favs

                    for(int i = 0; i < splitArray.length; i++) {
                        if(splitArray[i].equals(String.valueOf(idRecette))) {
                            isIntoArray = true;
                        }
                        else {
                            nouveauxFavs += String.valueOf(splitArray[i]);
                            if(i != splitArray.length-1) {
                                nouveauxFavs += ",";
                            }
                        }

                    }

                    // Si l'id Recette n'existe pas dans les favs,
                    if(!isIntoArray) {
                        String ajoutFav = "," + idRecette;
                        nouveauxFavs += ajoutFav;
                        coeurFav.setIcon(R.drawable.like);
                        Toast.makeText(mContext, "La recette a été ajoutée aux favoris", Toast.LENGTH_SHORT).show();
                    }
                    // Si l'id Recette est dans les favs
                    else {
                        coeurFav.setIcon(R.drawable.likeoff);
                        Toast.makeText(mContext, "La recette a été retirée des favoris", Toast.LENGTH_SHORT).show();
                    }
                    preferences.edit().putString("favoris", nouveauxFavs).apply();
            return true;


            case R.id.menu_item_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = laRecette.getInstructions();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Comment réaliser "+laRecette.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Partager avec"));
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // initialisation de la variable globale googleMap (déclarée au 5.2.3)
        this.googleMap = googleMap;

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(monMarker))
        {

        }
        return false;
    }
}

