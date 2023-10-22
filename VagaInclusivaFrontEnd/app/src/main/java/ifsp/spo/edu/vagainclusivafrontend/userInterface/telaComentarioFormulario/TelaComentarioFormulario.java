package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

public class TelaComentarioFormulario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_formulario);
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}