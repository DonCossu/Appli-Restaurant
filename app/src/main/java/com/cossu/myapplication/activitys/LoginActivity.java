package com.cossu.myapplication.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.cossu.myapplication.R;
import com.cossu.myapplication.ws.WSManager;
import com.cossu.myapplication.ws.WsListener;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WsListener {

    private TextView email;
    private TextView password;
    private Button bConnexion;
    private Button bInscritpion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.email = findViewById(R.id.loginEmail);
        this.password = findViewById(R.id.loginMdp);
        this.bConnexion = findViewById(R.id.buttonConnexion);
        this.bInscritpion = findViewById(R.id.buttonInscription);

        this.bInscritpion.setOnClickListener(this);
        this.bConnexion.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == this.bInscritpion) {
            Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
            startActivity(intent);
        }
        if (v == this.bConnexion) {
            if(!isValidEmailAddress(email.getText().toString())) {
                // SI ERREUR MAIL
                Toast.makeText(getApplicationContext(), "Le mail entré n'est pas valide", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> mesInfosConnexion = new HashMap<>();
            mesInfosConnexion.put("login", email.getText().toString());
            mesInfosConnexion.put("pass", password.getText().toString());

            WSManager manager = new WSManager(this);
            manager.sendRequest(1, "connexion", mesInfosConnexion);
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
                    .putString("login", email.getText().toString())
                    .putString("pass", password.getText().toString())
                    .apply();




            Intent intent = new Intent(LoginActivity.this, ListeRecettesActivity.class);
            startActivity(intent);


        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
}
