package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class TelaConexaoInternet extends AppCompatActivity {

    public void requestReadPhoneStatePermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{
                                android.Manifest.permission.READ_PHONE_STATE
                        },
                        0x1
                );
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conexao_internet);
        getSupportActionBar().hide();

        // Verifica a conexão com a Internet
        if (isConnected()) {
            // Caso esteja conectado, vá para a próxima página
            startActivity(new Intent(TelaConexaoInternet.this, TelaGeolocalizacao.class));
            finish();
        } else {
            Toast.makeText(this, "Por favor, conecte-se à internet para continuar", Toast.LENGTH_LONG).show();

            // Obtém o botão da layout
            Button button = findViewById(R.id.botaotentenovamente);

            // Define um OnClickListener para o botão
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Verifica a conexão com a Internet novamente
                    if (isConnected()) {
                        // Caso esteja conectado, vá para a próxima página
                        startActivity(new Intent(TelaConexaoInternet.this, TelaGeolocalizacao.class));
                        this.requestReadPhoneStatePermission(TelaConexaoInternet.this);
                        finish();
                    } else {
                        Toast.makeText(TelaConexaoInternet.this, "Por favor, conecte-se à internet para continuar", Toast.LENGTH_LONG).show();
                    }
                }

                public void requestReadPhoneStatePermission(Activity activity)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    {
                        if (ActivityCompat.checkSelfPermission(
                                activity,
                                android.Manifest.permission.READ_PHONE_STATE
                        ) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{
                                            android.Manifest.permission.READ_PHONE_STATE
                                    },
                                    0x1
                            );
                        }
                    }
                }
            });
        }
    }

    // Verifica a conexão com a Internet
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}