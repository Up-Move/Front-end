package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaGeolocalizacao;

import androidx.appcompat.app.AppCompatActivity;

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
                // Caso o usuário permita o acesso à geolocalização, abrimos a próxima Activity
                Intent intent = new Intent(TelaGeolocalizacao.this, TelaMapa.class);
                startActivity(intent);
                finish();
            }
        });
    }
}