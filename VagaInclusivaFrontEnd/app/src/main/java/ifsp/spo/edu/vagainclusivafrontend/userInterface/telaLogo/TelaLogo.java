package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaConexaoInternet.TelaConexaoInternet;

import android.content.Intent;
import android.os.Handler;
@SuppressWarnings({"Convert2Lambda", "SpellCheckingInspection"})
public class TelaLogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_logo);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

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