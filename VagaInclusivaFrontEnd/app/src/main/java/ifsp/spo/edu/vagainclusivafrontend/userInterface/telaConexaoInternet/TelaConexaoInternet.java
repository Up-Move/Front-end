package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaConexaoInternet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaGeolocalizacao.TelaGeolocalizacao;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
@SuppressWarnings("SpellCheckingInspection")
public class TelaConexaoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conexao_internet);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        // Verifica a conexão com a Internet
        if (isConnected()) {
            // Caso esteja conectado, vá para a próxima página
            startActivity(new Intent(TelaConexaoInternet.this, TelaGeolocalizacao.class));
            finish();
        } else {
            Toast.makeText(this, "Por favor, conecte-se à internet para continuar", Toast.LENGTH_LONG).show();

            // Obtém o botão da layout
            Button button = findViewById(R.id.btn_tente_novamente);

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