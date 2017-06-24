package com.apps.fourtech.mapatig.Interface;

import com.apps.fourtech.mapatig.Models.GetDataStatistic;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by aiolo on 20/06/2017.
 */

public interface APIGetDataStatistic {
    @GET("/api/statistic")
    Call<GetDataStatistic> getStatistic();
}
