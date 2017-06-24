package com.apps.fourtech.mapatig.Interface;

import com.apps.fourtech.mapatig.Models.PointsPositions;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by aiolo on 18/06/2017.
 */

public interface APIGetPosition {
    @GET("/api/location")
    Call<List<PointsPositions>> listPosition();
}
