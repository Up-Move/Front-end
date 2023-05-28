package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TelaGeolocalizacao extends AppCompatActivity {
    private Button btnPermitir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_geolocalizacao);
        getSupportActionBar().hide();

        btnPermitir = findViewById(R.id.btn_permitir);

        btnPermitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Caso o usuário permita o acesso à geolocalização, abrimos a próxima Activity
                Intent intent = new Intent(TelaGeolocalizacao.this, Map.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
