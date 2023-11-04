package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioIndividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings("Convert2Lambda")
public class TelaComentarioIndividual extends AppCompatActivity {

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_individual);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /* INSTANCIANDO OBJETO DO COMENTARIO INDIVIDUAL */
        Avaliacao avaliacaoIndividual = new Avaliacao(4, "Joana", "Lorem");

        /* DESENHANDO O COMENTARIO INDIVIDUAL */
        /* NOME */
        TextView nomeIndividualTextView = findViewById(R.id.tela_comentario_individual_nome);
        nomeIndividualTextView.setText(avaliacaoIndividual.getNome());

        /* ESTRELA POR PADRÃO É 5 APAGADAS, DEPENDENDO DA AVALIACAO VAI DESENHAR ESTRELA DOURADAS */
        LinearLayout estrelasLayout = findViewById(R.id.tela_comentario_individual_estrela);
        int vetorEstrelas = R.drawable.icon_star_gold;
        for (int i = 1; i <= 5; i++) {
            ImageView estrelaImageView = estrelasLayout.findViewById(getResources().getIdentifier("tela_comentario_individual_estrela_" + i, "id", getPackageName()));
            if (i <= avaliacaoIndividual.getEstrela()) {
                estrelaImageView.setImageResource(vetorEstrelas);
            } else {
                estrelaImageView.setImageResource(R.drawable.icon_star_filled);
            }
        }
        /* DESCRIÇÃO */
        TextView descricaoIndividualTextView = findViewById(R.id.tela_comentario_individual_descricao);
        descricaoIndividualTextView.setText(avaliacaoIndividual.getDescricao());

        /* AQUI É AS AVALIAÇÕES DO COMENTÁRIO */
        /* DO POST DA API VAI VIR UMA QUANTIDADE DE COMENTÁRIO DA AVALIACAO DA VAGA, AQUI VAI SE INSTANCIAR, OBJETOS PARA ELAS */
        Avaliacao[] avaliacoes = new Avaliacao[30]; /* AVALIACÃO.length */

        /* AQUI ELE VAI VER SE A QUANTIDADE DE AVALIACAO É IGUAL 0, SE FOR VAI DAR HIDDEN NO SCROLLVIEW E VISIBLE NO TEXTO DE NÃO TEM AVALIACAO NO COMENTÁRIO */
        if (avaliacoes.length == 0) {
            ScrollView scrollView = findViewById(R.id.tela_comentario_individual_viewScroll);
            scrollView.setVisibility(View.GONE);
            TextView avisoTextView = findViewById(R.id.tela_comentario_individual_sem_avaliacao);
            avisoTextView.setVisibility(View.VISIBLE);
        }

        /* PARA CADA QUANTIDADE DE AVALIACAO NO CASO ESTOU MOCKANDO 30 VAI ATRIBUIR ID PARA CADA COMPONENTE E PARA O NOME: JOANA_n? E PARA DESCRICAO: LOREM IPSUM_n? */
        /* DESCRICAOID, NOMEID: EU NÃO PRECISO DISSO, É APENAS PARA MOCKAR E FUNCIONAR*/
        for (int i = 1; i <= avaliacoes.length; i++) {
            String nome = "Joana " + i;
            String descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit consequat erat, non tincidunt massa fringilla sed. Proin porta tincidunt felis, eu consequat " + i;
            avaliacoes[i - 1] = new Avaliacao(nome, descricao);
        }

        /* RECUPERA O LAYOUT LINEAR QUE ESTÁ DENTRO DO SCROLLVIEW */
        LinearLayout parentLayout = findViewById(R.id.tela_comentario_individual_viewScroll_linear);

        /* BOTA NA TELA PARA CADA QUANTIDADE DE AVALIACAO */
        for (Avaliacao avaliacao : avaliacoes) {
            LinearLayout avaliacaoLayout = new LinearLayout(this);
            avaliacaoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    200));
            avaliacaoLayout.setOrientation(LinearLayout.VERTICAL);
            avaliacaoLayout.setBackgroundResource(R.color.background);
            avaliacaoLayout.setPadding(26, 16, 26, 0);

            TextView nomeTextView = new TextView(this);
            nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            nomeTextView.setText(avaliacao.getNome());
            nomeTextView.setTextColor(ContextCompat.getColor(this, R.color.primary));
            nomeTextView.setTextSize(12);
            nomeTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
            nomeTextView.setPadding(8, 8, 8, 0);
            nomeTextView.setTypeface(null, Typeface.NORMAL);

            TextView descricaoTextView = new TextView(this);
            descricaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    146));
            descricaoTextView.setText(avaliacao.getDescricao());
            descricaoTextView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            descricaoTextView.setTextSize(10);
            descricaoTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
            descricaoTextView.setPadding(8, 0, 8, 0);

            avaliacaoLayout.addView(nomeTextView);
            avaliacaoLayout.addView(descricaoTextView);

            parentLayout.addView(avaliacaoLayout);
        }

        // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
        imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
        imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
        imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
        imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

        imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioIndividual.this, TelaMapaAutenticado.class);
                startActivity(intent);
            }
        });

        imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioIndividual.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioIndividual.this, TelaInfos.class);
                startActivity(intent);
            }
        });

        imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaComentarioIndividual.this, TelaFiltros.class);
                startActivity(intent);
            }
        });
    }
}