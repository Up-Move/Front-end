package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioAvaliacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

public class TelaComentarioAvaliacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_avaliacao);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}