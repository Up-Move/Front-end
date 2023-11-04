package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioAvaliacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings("ALL")
public class TelaComentarioAvaliacao extends AppCompatActivity {

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
            setContentView(R.layout.tela_comentario_avaliacao);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
            imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
            imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
            imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
            imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

            imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaMapaAutenticado.class);
                    startActivity(intent);
                }
            });

            imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaPerfil.class);
                    startActivity(intent);
                }
            });

            imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaInfos.class);
                    startActivity(intent);
                }
            });

            imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaFiltros.class);
                    startActivity(intent);
                }
            });

            /* ESTRELA RANDOM */
            Random random = new Random();

            /* INSTANCIANDO AVALIACAOS */
            Avaliacao[] avaliacoes = new Avaliacao[30]; /* AVALIACAO.length */

            /* MOCANDO */
            for (int i = 1; i <= avaliacoes.length; i++) {
                String nome = "Joana " + i;
                String descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit consequat erat, non tincidunt massa fringilla sed. Proin porta tincidunt felis, eu consequat " + i;
                int estrela = random.nextInt(5) + 1;
                avaliacoes[i - 1] = new Avaliacao(estrela, nome, descricao);
            }

            /* PARA SCROLLAR */
            LinearLayout parentLayout = findViewById(R.id.tela_comentario_vaga_avaliacao_viewScroll_linear);

            /* DESENHANDO LAYOUT */
            for (Avaliacao avaliacao : avaliacoes) {
                LinearLayout avaliacaoLayout = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        260
                );
                params.setMargins(16, 16, 16, 0);
                avaliacaoLayout.setLayoutParams(params);
                avaliacaoLayout.setOrientation(LinearLayout.VERTICAL);
                avaliacaoLayout.setBackgroundResource(R.color.background);
                avaliacaoLayout.setPadding(16, 8, 16, 8);

                LinearLayout estrelaNomeLayout = new LinearLayout(this);
                estrelaNomeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                estrelaNomeLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView nomeTextView = new TextView(this);
                nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                nomeTextView.setText(avaliacao.getNome());
                nomeTextView.setTextColor(ContextCompat.getColor(this, R.color.primary));
                nomeTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
                nomeTextView.setTextSize(12);
                nomeTextView.setTypeface(null, Typeface.NORMAL);

                LinearLayout estrelaLayout = new LinearLayout(this);
                estrelaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                estrelaLayout.setOrientation(LinearLayout.HORIZONTAL);
                estrelaLayout.setGravity(Gravity.RIGHT);
                estrelaLayout.setPadding(0,2,32,0);

                for (int j = 0; j < 5; j++) {
                    ImageView estrelaImageView = new ImageView(this);
                    estrelaImageView.setLayoutParams(new LinearLayout.LayoutParams(
                            30, 30
                    ));
                    if (j < avaliacao.getEstrela()) {
                        estrelaImageView.setImageResource(R.drawable.icon_star_gold); // Estrela preenchida
                    } else {
                        estrelaImageView.setImageResource(R.drawable.icon_star_filled); // Estrela vazia
                    }
                    estrelaLayout.addView(estrelaImageView);
                }

                estrelaNomeLayout.addView(nomeTextView);
                estrelaNomeLayout.addView(estrelaLayout);

                TextView descricaoTextView = new TextView(this);
                descricaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        140
                ));
                descricaoTextView.setText(avaliacao.getDescricao());
                descricaoTextView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
                descricaoTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
                descricaoTextView.setTextSize(10);

                LinearLayout avaliacaoRespostaLayout = new LinearLayout(this);
                avaliacaoRespostaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                avaliacaoRespostaLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView avaliacaoTextView = new TextView(this);
                avaliacaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                avaliacaoTextView.setText(getString(R.string.tela_comentario_avaliacao_avaliacao));
                avaliacaoTextView.setTextColor(ContextCompat.getColor(this, R.color.primary));
                avaliacaoTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
                avaliacaoTextView.setTextSize(10);
                avaliacaoTextView.setTypeface(null, Typeface.NORMAL);

                TextView responderTextView = new TextView(this);
                LinearLayout.LayoutParams responderLayoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                responderLayoutParams.setMargins(40, 0, 0, 0);
                responderTextView.setLayoutParams(responderLayoutParams);

                responderTextView.setText(getString(R.string.tela_comentario_individual_responder));
                responderTextView.setTextColor(ContextCompat.getColor(this, R.color.primary));
                responderTextView.setTypeface(ResourcesCompat.getFont(this, R.font.jura));
                responderTextView.setTextSize(10);
                responderTextView.setTypeface(null, Typeface.NORMAL);

                avaliacaoRespostaLayout.addView(avaliacaoTextView);
                avaliacaoRespostaLayout.addView(responderTextView);

                avaliacaoLayout.addView(estrelaNomeLayout);
                avaliacaoLayout.addView(descricaoTextView);
                avaliacaoLayout.addView(avaliacaoRespostaLayout);

                parentLayout.addView(avaliacaoLayout);
            }
        } else {
            redirect();
        }
    }

    // Função para verificar se a pessoa esta autenticada
    private boolean isAuthenticated() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("AUTH_KEY", "");
        // Verifique se o token está presente e é válido
        if (authToken != null && !authToken.isEmpty()) {
            return true;
        }
        return false;
    }

    // Função para a Pessoa caso ela não seja autenticada
    private void redirect() {
        Intent intent = new Intent(this, TelaLogin.class);
        startActivity(intent);
        finish();
    }
}