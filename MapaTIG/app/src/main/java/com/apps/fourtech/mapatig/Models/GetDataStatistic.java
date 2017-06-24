package com.apps.fourtech.mapatig.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aiolo on 20/06/2017.
 */

public class GetDataStatistic {
    @SerializedName("roubo")
    public int roubo;
    @SerializedName("acidente")
    public int acidente;
    @SerializedName("furto")
    public int furto;
    @SerializedName("abuso")
    public int abuso;
}
