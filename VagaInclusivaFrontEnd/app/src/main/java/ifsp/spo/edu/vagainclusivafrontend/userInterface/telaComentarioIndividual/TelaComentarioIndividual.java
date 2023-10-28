package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioIndividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

public class TelaComentarioIndividual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_individual);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}