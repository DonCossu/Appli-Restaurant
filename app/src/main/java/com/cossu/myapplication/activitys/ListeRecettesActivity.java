package com.cossu.myapplication.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.cossu.myapplication.R;
import com.cossu.myapplication.Recette;
import com.cossu.myapplication.adapter.RecyclerViewAdapter;
import com.cossu.myapplication.ws.WSManager;
import com.cossu.myapplication.ws.WsListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ListeRecettesActivity extends AppCompatActivity implements WsListener {

    List<Recette> maListeDeRecettes = new ArrayList<>();
    RecyclerView rv;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listerecettes);

        this.rv = findViewById(R.id.listRecettes);

            WSManager manager = new WSManager(this);
            manager.sendRequest(1, "listRecettes", null);


        }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.adapter != null){
            this.adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void errorRequest(int id) {

    }

    @Override
    public void successRequest(int id, String data) {

        Gson googleJson = new Gson();

        this.maListeDeRecettes = Arrays.asList(googleJson.fromJson(data, Recette[].class));

        System.out.println(maListeDeRecettes);


        rv.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new RecyclerViewAdapter( this, this, this.maListeDeRecettes);
        rv.setAdapter(adapter);

    }

    // Gestion du clic du RecyclerView
    public void onClickCell(Recette recette, int position) {
        Intent intent = new Intent(this, InfoRecette.class);
        intent.putExtra("id", recette.getId());
        startActivity(intent);
    }


    // Gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.listerecettes, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_deco:

                SharedPreferences preferences = getSharedPreferences("APP", MODE_PRIVATE);
                preferences.edit()
                        .putString("connected", "non")
                        .putString("prenom", "")
                        .putString("nom", "")
                        .putString("login", "")
                        .putString("pass", "")
                        .apply();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
