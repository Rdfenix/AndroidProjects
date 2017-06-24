package rudneioliveira.retrofitconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rudneioliveira.retrofitconnection.CustomAdapter.CustomAdapter;
import rudneioliveira.retrofitconnection.Users.User;
import rudneioliveira.retrofitconnection.retrofit.api_connection.Connection;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<User> users;
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listID);

        Connection.get().getUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                users = response.body();

                adapter = new CustomAdapter(MainActivity.this, users);

                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
