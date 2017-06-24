package connectjson.rudneioliveira.connectjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<usuario> user;
    private adapterCustom adapterCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewID);

        api.get().getUsuarios().enqueue(new Callback<List<usuario>>() {
            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response) {
                //response.body
                List<usuario> users = response.body();
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {

            }
        });


    }


}
