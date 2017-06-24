package com.apps.fourtech.mapatig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Busca o botão de LOGIN definido no layout desta Activity
        btnLogin = (Button) findViewById(R.id.btn_login);

        //Seta o método OnClick no botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Chama a tela principal (onde será carregado o mapa)
                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentMain);

            }
        });
    }
}
