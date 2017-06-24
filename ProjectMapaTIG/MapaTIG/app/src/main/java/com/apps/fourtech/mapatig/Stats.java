package com.apps.fourtech.mapatig;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.apps.fourtech.mapatig.Interface.APIGetDataStatistic;
import com.apps.fourtech.mapatig.Models.GetDataStatistic;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Stats extends AppCompatActivity {

    //adicionar nesse local a URL da api que esta sendo usada
    //ou caso esteja em um servidor local adicionar o ipv4 da sua maquina
    // :3000 - referente ao api que esta trasendo o json.
    String BASE_URL = "http://192.168.0.13:3000";

    //variavel de log
    private static final String TAG = "conexao";

    WebView graph;
    String strUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIGetDataStatistic service = retrofit.create(APIGetDataStatistic.class);

        Call<GetDataStatistic> reqStatistic = service.getStatistic();

        reqStatistic.enqueue(new Callback<GetDataStatistic>() {
            @Override
            public void onResponse(Response<GetDataStatistic> response) {
                if (!response.isSuccess()){
                    Log.e(TAG, "Error: " + response.code());
                } else {
                    try {
                        GetDataStatistic statistic = response.body();

                        String roubo = String.valueOf(statistic.roubo);
                        String acidente = String.valueOf(statistic.acidente);
                        String furto = String.valueOf(statistic.furto);
                        String abuso = String.valueOf(statistic.abuso);

                        strUrl = "https://chart.googleapis.com/chart?cht=p3&chs=340x155&chd=t:" + roubo + "," + acidente + "," + furto + "," + abuso + "&chl=roubo|acidente|furto|abuso";

                        graph = (WebView) findViewById(R.id.graph);
                        graph.loadUrl(strUrl);

                    } catch (Exception e){
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });
    }
}
