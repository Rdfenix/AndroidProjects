package com.apps.fourtech.mapatig;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.apps.fourtech.mapatig.Interface.RegisterLatLon;
import com.apps.fourtech.mapatig.Models.PointsPositions;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TelaRegistros extends AppCompatActivity {

    //adicionar nesse local a URL da api que esta sendo usada
    //ou caso esteja em um servidor local adicionar o ipv4 da sua maquina
    // :3000 - referente ao api que esta trasendo o json.
    String BASE_URL = "http://192.168.0.13:3000";

    //variavel de log
    private static final String TAG = "conexao";

    private String[] occ = new String[]{"roubo", "furto", "abuso", "acidente"};
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registros);

        save = (Button) findViewById(R.id.salvar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, occ);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Item selecionado: "+ parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ocorrencia = String.valueOf(sp.getSelectedItem());

                AddOcorrence(ocorrencia);
            }
        });

    }

    public void AddOcorrence(String ocorrencia){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterLatLon service = retrofit.create(RegisterLatLon.class);

        Call<PointsPositions> sendOcc = service.update(ocorrencia);

        sendOcc.enqueue(new Callback<PointsPositions>() {
            @Override
            public void onResponse(Response<PointsPositions> response) {
                if (response.isSuccess()){
                    Toast.makeText(TelaRegistros.this, "Salvo", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });

        Intent intent = new Intent (getBaseContext(), MainActivity.class);
        startActivity(intent);

    }
}
