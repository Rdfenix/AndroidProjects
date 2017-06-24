package com.apps.fourtech.mapatig.Interface;

import com.apps.fourtech.mapatig.Models.PointsPositions;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by aiolo on 21/06/2017.
 */

public interface RegisterLatLon {
    @POST("/api/save/location")
    Call<PointsPositions> saveLocation(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );

    @POST("/api/update/location")
    Call<PointsPositions> update(
            @Query("ocorrencia") String ocorrencia
    );
}
