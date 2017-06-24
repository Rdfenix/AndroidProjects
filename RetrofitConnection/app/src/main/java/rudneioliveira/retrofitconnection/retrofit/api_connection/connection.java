/**
 * Created by rudnei.oliveira on 23/06/2016.
 */
package rudneioliveira.retrofitconnection.retrofit.api_connection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rudneioliveira.retrofitconnection.Users.User;

public class Connection {

    private static String URL = "http://192.168.0.217:3000";
    private static Retrofit restAdapter = null;

    public static getAllApi get(){
        if(restAdapter == null){
            restAdapter = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return restAdapter.create(getAllApi.class);
    }

    public interface getAllApi{
        @GET("/")
        Call<List<User>> getUser();
    }
}
