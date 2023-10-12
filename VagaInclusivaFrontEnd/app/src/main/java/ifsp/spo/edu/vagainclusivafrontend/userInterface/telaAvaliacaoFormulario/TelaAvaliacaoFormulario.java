package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

public class TelaAvaliacaoFormulario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_avaliacao_formulario);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}