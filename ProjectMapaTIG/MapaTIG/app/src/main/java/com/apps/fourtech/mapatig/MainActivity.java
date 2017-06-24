package com.apps.fourtech.mapatig;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.fourtech.mapatig.Interface.RegisterLatLon;
import com.apps.fourtech.mapatig.Models.PointsPositions;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;

    //adicionar nesse local a URL da api que esta sendo usada
    //ou caso esteja em um servidor local adicionar o ipv4 da sua maquina
    // :3000 - referente ao api que esta trasendo o json.
    String BASE_URL = "http://192.168.0.13:3000";

    //variavel de log
    private static final String TAG = "conexao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        latitude = (EditText) findViewById(R.id.lat);
        longitude = (EditText) findViewById(R.id.lon);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recebe as coordenadas como String
                String latLocation = latitude.getText().toString().trim();
                String lonLocation = longitude.getText().toString().trim();
                //Transforma a String recebida em Double
                Double lat = Double.valueOf(latLocation);
                Double lon = Double.valueOf(lonLocation);
                //Função para salvar no banco as coordenadas passada.
                addPointerInMaap(lat, lon);
            }
        });

        FloatingActionButton fabStats = (FloatingActionButton) findViewById(R.id.fabStats);
        fabStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getBaseContext(), Stats.class);
                startActivity(intent);

            }
        });

        FloatingActionButton fabSair = (FloatingActionButton) findViewById(R.id.fabSair);
        fabSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();

            }
        });

        if (savedInstanceState == null) {
            //Cria um novo Fragmento(pense como se fosse uma UserControl) e o adiciona no layout desta activity
            MapaFragment mapaFragment = new MapaFragment();
            mapaFragment.setArguments(getIntent().getExtras());

            //Essa linha com o "begin" é que faz startar o fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout, mapaFragment).commit();
        }
    }

    //Este método é necessário para inserir os botões da Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void addPointerInMaap(Double lat, Double lon){

        Toast.makeText(MainActivity.this, "Latitude: " + lat + " Longitude: " + lon + " salvos.", Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterLatLon service = retrofit.create(RegisterLatLon.class);

        Call<PointsPositions> sendPoints = service.saveLocation(lat, lon);

        sendPoints.enqueue(new Callback<PointsPositions>() {
            @Override
            public void onResponse(Response<PointsPositions> response) {
                if (response.isSuccess()){
                    Toast.makeText(MainActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });

        Intent intent = new Intent (getBaseContext(), TelaRegistros.class);
        startActivity(intent);
    }

}
