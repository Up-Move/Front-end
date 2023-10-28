package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaGeolocalizacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import android.os.Bundle;
import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa.TelaMapa;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

@SuppressWarnings({"Convert2Lambda", "SpellCheckingInspection"})
public class TelaGeolocalizacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_geolocalizacao);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Button btnPermitir = findViewById(R.id.btn_permitir);

        btnPermitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifique se já temos permissão de geolocalização
                if (ActivityCompat.checkSelfPermission(TelaGeolocalizacao.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Se não tivermos permissão, solicite-a ao usuário
                    ActivityCompat.requestPermissions(TelaGeolocalizacao.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    // Se já tivermos permissão, abra a próxima Activity
                    Intent intent = new Intent(TelaGeolocalizacao.this, TelaMapa.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}