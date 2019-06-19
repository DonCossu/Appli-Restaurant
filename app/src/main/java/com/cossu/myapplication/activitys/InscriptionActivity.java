package com.cossu.myapplication.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cossu.myapplication.R;
import com.cossu.myapplication.ws.WSManager;
import com.cossu.myapplication.ws.WsListener;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;



public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener , WsListener {

    private EditText inscriptionPrenom;
    private EditText inscriptionNom;
    private EditText inscriptionEmail;
    private EditText inscriptionMdp;
    private Button buttonValidationInscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        inscriptionPrenom = findViewById(R.id.InscriptionPrenom);
        inscriptionNom = findViewById(R.id.InscriptionNom);
        inscriptionEmail = findViewById(R.id.InscriptionEmail);
        inscriptionMdp = findViewById(R.id.InscriptionMdp);
        buttonValidationInscription = findViewById(R.id.buttonValidationInscription);


        buttonValidationInscription.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == this.buttonValidationInscription) {

            if(!isValidEmailAddress(inscriptionEmail.getText().toString())) {
                // SI ERREUR MAIL
                Toast.makeText(getApplicationContext(), "Le mail entré n'est pas valide", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> mesInfosInscription = new HashMap<>();
            mesInfosInscription.put("prenom", inscriptionPrenom.getText().toString());
            mesInfosInscription.put("nom", inscriptionNom.getText().toString());
            mesInfosInscription.put("login", inscriptionEmail.getText().toString());
            mesInfosInscription.put("pass", inscriptionMdp.getText().toString());

            WSManager manager = new WSManager(this);
            manager.sendRequest(1, "addCompte", mesInfosInscription);

        }
    }

    private boolean isValidEmailAddress(String email)
    {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void errorRequest(int id) {

    }

    @Override
    public void successRequest(int id, String data) {
        try{
            JSONObject obj = new JSONObject(data);
            if (obj.has("error")){

                String monErreur = obj.getString("error");
                Toast.makeText(getApplicationContext(), monErreur, Toast.LENGTH_SHORT).show();
                return;
            }



            SharedPreferences preferences = getSharedPreferences("APP", MODE_PRIVATE);
            preferences.edit()
                    .putString("connected", "oui")
                    .putString("prenom", inscriptionPrenom.getText().toString())
                    .putString("nom", inscriptionNom.getText().toString())
                    .putString("login", inscriptionEmail.getText().toString())
                    .putString("pass", inscriptionMdp.getText().toString())
                    .apply();




            Intent intent = new Intent(InscriptionActivity.this, ListeRecettesActivity.class);
            startActivity(intent);


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}