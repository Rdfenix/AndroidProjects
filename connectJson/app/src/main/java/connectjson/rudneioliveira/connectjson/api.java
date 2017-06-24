package connectjson.rudneioliveira.connectjson;


import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class api {

    private static String service_url = "http://192.168.0.217:3000";
    private  static Retrofit restAdapter = null;

    public static getAllApi get() {
        if(restAdapter == null){
            restAdapter = new Retrofit.Builder()
                    .baseUrl(service_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return restAdapter.create(getAllApi.class);
    }

    public interface getAllApi{
        @GET("/")
        Call<List<usuario>> getUsuarios();
    }
}
