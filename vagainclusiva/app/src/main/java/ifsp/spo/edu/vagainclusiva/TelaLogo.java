package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TelaLogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_logo);
        getSupportActionBar().hide();

        int tempoDeExibicao = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent proximaTela = new Intent(TelaLogo.this, TelaConexaoInternet.class);
                startActivity(proximaTela);
                finish();
            }
        }, tempoDeExibicao);
    }
}