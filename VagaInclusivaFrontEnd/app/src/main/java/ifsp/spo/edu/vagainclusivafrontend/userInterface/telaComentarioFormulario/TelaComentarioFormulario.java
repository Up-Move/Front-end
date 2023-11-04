package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings("Convert2Lambda")
public class TelaComentarioFormulario extends AppCompatActivity {

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_formulario);
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Avaliacao avaliacao = new Avaliacao( "Joana", "Lorem");

        TextView nomeTitle = findViewById(R.id.tela_comentario_formulario_title);

        nomeTitle.setText(getString(R.string.tela_comentario_formulario_titulo) + " " + avaliacao.getNome());
        TextView nome = findViewById(R.id.tela_comentario_formulario_nome);
        nome.setText(avaliacao.getNome());
        TextView descricao = findViewById(R.id.tela_comentario_formulario_descricao);
        descricao.setText(avaliacao.getDescricao());

        // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
        imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
        imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
        imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
        imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

        imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioFormulario.this, TelaMapaAutenticado.class);
                startActivity(intent);
            }
        });

        imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioFormulario.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioFormulario.this, TelaInfos.class);
                startActivity(intent);
            }
        });

        imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioFormulario.this, TelaFiltros.class);
                startActivity(intent);
            }
        });

    }
}