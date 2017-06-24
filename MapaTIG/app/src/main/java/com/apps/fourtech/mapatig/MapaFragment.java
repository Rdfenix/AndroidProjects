package com.apps.fourtech.mapatig;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.fourtech.mapatig.Interface.APIGetPosition;
import com.apps.fourtech.mapatig.Models.PointsPositions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by andre.portes on 14/10/2016.
 */

public class MapaFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener{

    //Variáveis
    private GoogleMap map;
    private final int REQUEST_ACCESS_FINE_LOCATION = 1;
    private final int REQUEST_ACCESS_COARSE_LOCATION = 2;

    //variavel de log
    private static final String TAG = "conexao";

    private GoogleApiClient googleApiClient;
    private double longitude;
    private double latitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Recupera o fragment que está no layout (definido na MainActivity)
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        // Inicia o Google Maps dentro do fragment
        mapFragment.getMapAsync(this);

        //Inicializa o google API client para poder trabalhar com localização
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        //Conecta a API do google
        googleApiClient.connect();

        //Essa linha informa ao layout que este fragmento pode utilizar os botões da Action Bar
        setHasOptionsMenu(true);

        return view;
    }

    //Método que é disparado toda vez que algum botão da action bar é clicado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    //Adicionar multiplos marcadores
    public void AddMakerPoints(){
        //adicionar nesse local a URL da api que esta sendo usada
        //ou caso esteja em um servidor local adicionar o ipv4 da sua maquina
        // :3000 - referente ao api que esta trasendo o json.
        String BASE_URL = "http://192.168.0.13:3000";

        //monta o objeto do JSON recebido
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //monta o objeto que foi gerado em uma classe
        APIGetPosition service = retrofit.create(APIGetPosition.class);

        Call<List<PointsPositions>> requestPosition = service.listPosition();

        requestPosition.enqueue(new Callback<List<PointsPositions>>() {
            @Override
            public void onResponse(Response<List<PointsPositions>> response) {
                if (!response.isSuccess()){
                    Log.e(TAG, "Erro: " + response.code());
                } else {
                    try {
                        List<PointsPositions> points = response.body();
                        for (int i = 0; i < points.size(); i++){
                            Double lat = points.get(i).lat;
                            Double lon = points.get(i).lon;
                            String title = points.get(i).ocorrencia;
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(lat, lon);
                            markerOptions.position(latLng);
                            markerOptions.title(title);
                            map.addMarker(markerOptions);
                            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            map.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    } catch (Exception e){
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "ERRO: " + t.getMessage());
            }
        });
    }

    //Método acionado quando o mapa está carregado e pronto para ser trabalhado
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        //Não se preocupe com estes métodos...é só uma "receita de bolo" para solicitar permissão para o usuário
        solicitaPermissoesLocalizacao(REQUEST_ACCESS_FINE_LOCATION);
        solicitaPermissoesLocalizacao(REQUEST_ACCESS_COARSE_LOCATION);

    }

    private void configuraMapa(){
        // Tipo do mapa : MAP_TYPE_NORMAL, MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        AddMakerPoints();
    }

    //Método que é disparado quando o GOOGLE API está conectado
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Limpa o mapa
        map.clear();

        //Ignorar..."receita de bolo" para permissão
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Busca a localização atual e atualiza nossas variáveis que guardam LATITUDE e LONGITUDE
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            configuraMapa();
        }
    }

    //INGORAR
    @Override
    public void onConnectionSuspended(int i) {

    }

    //INGORAR
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //INGORAR
    @Override
    public void onMarkerDragStart(Marker marker) {}

    //INGORAR
    @Override
    public void onMarkerDrag(Marker marker) {}

    //Método que é disparado quando o usuário termina de "arrastar" o marcador pelo mapa.
    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Atualiza a localização do marcador atual com a posição onde o usuário terminou de arrastá-lo
        marker.setPosition(marker.getPosition());
    }

    //INGORAR OS MÉTODOS DE PERMISSÃO ABAIXO

    private void solicitaPermissoesLocalizacao(int tipoPermissao) {
        int permissionCheck;

        switch (tipoPermissao) {
            case REQUEST_ACCESS_FINE_LOCATION:
                permissionCheck = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showExplanation("É necessário a permissão de acesso ao GPS", "Atenção",
                                Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_ACCESS_FINE_LOCATION);
                    } else {
                        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_ACCESS_FINE_LOCATION);
                    }
                } else {

                    map.setMyLocationEnabled(true);
                    Toast.makeText(getContext(), "Permissão de GPS já concedida", Toast.LENGTH_SHORT).show();
                }
            case REQUEST_ACCESS_COARSE_LOCATION:
                permissionCheck = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        showExplanation("É necessário a permissão de acesso à localização", "Atenção",
                                Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_ACCESS_COARSE_LOCATION);
                    } else {
                        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_ACCESS_COARSE_LOCATION);
                    }
                } else {
                    map.setMyLocationEnabled(true);
                    Toast.makeText(getContext(), "Permissão de acesso à localização já concedida", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {

        if (grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                configuraMapa();
            }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permissionName}, permissionRequestCode);
    }
}
