package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoVaga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

public class TelaAvaliacaoVaga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_avaliacao_vaga);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}