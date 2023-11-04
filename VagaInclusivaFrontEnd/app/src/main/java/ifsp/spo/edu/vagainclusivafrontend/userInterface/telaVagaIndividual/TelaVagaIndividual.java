package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.TelaAvaliacaoFormulario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa.TelaMapa;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings({"WrapperTypeMayBePrimitive", "ConstantConditions", "Convert2Lambda", "RedundantIfStatement"})
public class TelaVagaIndividual extends AppCompatActivity {

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vaga_individual);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /* MOCANDO AS ACESSIBILIDADES VAI VIR DA API AS ACESSIBILIDADES DA VAGA */
        /* ESTOU MOCANDO ACESSIBILIDADE  AC1 e AC5 */
        /* Acessibilidade[] acessibilidades = { Acessibilidade.AC1, Acessibilidade.AC5}; */
        /* MOCANDO TODAS AS ACESSIBLIDADES */
        Acessibilidade[] acessibilidades = Acessibilidade.values();

        /* INSTANCIANDO AS 3 ULTIMAS AVALIACAO DA VAGA ISSO VIRA DA API */
        Avaliacao[] avaliacoes = new Avaliacao[3]; /* AVALIACÃO.length */

        /* SE NÃO TIVER AVALIACAO VAI TIRAR O TITULO*/
        if (avaliacoes.length > 0) {
            TextView avisoTextView = findViewById(R.id.titulo_comentarios_dessa_vaga_sem_comentarios);
            TextView VerMaisTextView = findViewById(R.id.button_inline_veja_mais_comentarios);
            ImageView SetaImageView = findViewById(R.id.veja_comentarios_seta);
            avisoTextView.setVisibility(View.GONE);
            VerMaisTextView.setVisibility(View.VISIBLE);
            SetaImageView.setVisibility(View.VISIBLE);
        }

        /* ESTRELA RANDOM */
        Random random = new Random();

        /* MOCANDO */
        for (int i = 1; i <= avaliacoes.length; i++) {
            String nome = "Joana " + i;
            String descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit consequat erat, non tincidunt massa fringilla sed. Proin porta tincidunt felis, eu consequat " + i;
            int estrela = random.nextInt(5) + 1;
            avaliacoes[i - 1] = new Avaliacao(estrela, nome, descricao);
        }

        /* INSTANCIA UMA CLASSE VAGA PARA A VAGA INDIVIDUAL, ISSO VAI VIR DA API */
        Vaga vaga = new Vaga("R. Cavalheiro Nabuco", "1234567890123456789", "R. Joaquin Nabuco", "Bras", 10, 4.15, acessibilidades);

        /* ARREDONDA ESTRELINHA */
        int qtdEstrela = (int) Math.round(vaga.getMedia());

        /* DESENHA ESTRELINHA CONFORME A MÉDIA */
        for (int i = 1; i <= 5; i++) {
            int imageViewId = getResources().getIdentifier("tela_avaliacao_estrela_" + i, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);

            if (i <= qtdEstrela) {
                imageView.setImageResource(R.drawable.icon_star_gold);
            } else {
                imageView.setImageResource(R.drawable.icon_star_filled);
            }
        }

        /* DESENHANDO NA TELA */

        /* DESENHANDO NO DADOS DA VAGA */
        TextView mediaTitle = findViewById(R.id.tela_avaliacao_titulo_media);
        TextView localTextView = findViewById(R.id.tela_avaliacao_container_local);
        TextView proximidadeTextView = findViewById(R.id.tela_avaliacao_container_proximidade);
        TextView numeroDeVagasTextView = findViewById(R.id.tela_avaliacao_container_numero_vagas);
        TextView areaTextView = findViewById(R.id.tela_avaliacao_container_area);
        TextView coordenadasTextView = findViewById(R.id.tela_avaliacao_container_coordenadas);

        mediaTitle.setText(getString(R.string.tela_vaga_individual_media) + " " + vaga.getMedia());
        localTextView.setText(getString(R.string.tela_avaliacao_container_local) + " " + vaga.getLocalidade());
        proximidadeTextView.setText(getString(R.string.tela_avaliacao_container_proximidade) + " " + vaga.getProximidade());
        numeroDeVagasTextView.setText(getString(R.string.tela_avaliacao_container_vagas) + " " + vaga.getQtdVaga());
        areaTextView.setText(getString(R.string.tela_avaliacao_container_area) + " " + vaga.getArea());
        coordenadasTextView.setText(getString(R.string.tela_avaliacao_container_coordenadas) + ":\n" + vaga.getCoordenada());

        LinearLayout linearLayoutAC = findViewById(R.id.tela_avaliacao_container_acessibilidades);
        LinearLayout linearLayoutAV = findViewById(R.id.container_comentarios_avaliacao);

        /* DESENHANDO ACESSIBILIDADES */
        for (Acessibilidade acessibilidade : acessibilidades) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 8);
            textView.setLayoutParams(params);
            textView.setText(acessibilidade.getDescricao(this));
            textView.setMaxEms(9);
            textView.setFontFeatureSettings(String.valueOf(R.font.jura));
            textView.setTextColor(getResources().getColor(R.color.primary_text));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            linearLayoutAC.addView(textView);
        }

        /* DESENHANDO AS 3 AVALIACAOES, PODE SER 1 OU 2 TAMBÉM */
        for (Avaliacao avaliacao : avaliacoes) {
            LinearLayout avaliacaoLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    220
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
                    100
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

            linearLayoutAV.addView(avaliacaoLayout);
        }

        // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
        imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
        imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
        imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
        imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

        imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAuthenticated()) {
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaMapaAutenticado.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaMapa.class);
                    startActivity(intent);
                }
            }
        });

        imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAuthenticated()) {
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaPerfil.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para Acessar a Tela de Perfil, é necessário que seja feito o login no aplicativo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAuthenticated()) {
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaInfos.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para Acessar a Tela de Informações, é necessário que seja feito o login no aplicativo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAuthenticated()) {
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaFiltros.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para Acessar a Tela de Filtros, é necessário que seja feito o login no aplicativo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Recupere os valor da vaga Clicada na Tela Mapa
        int retrievedId = getIntent().getIntExtra("index", 0);
        System.out.println(retrievedId);
        int retrievedQuantidadeV = getIntent().getIntExtra("quantidadeV", 0);
        String retrievedArea = getIntent().getStringExtra("area");
        String retrievedComplemento = getIntent().getStringExtra("complemento");
        String retrievedLocal = getIntent().getStringExtra("local");
        Double retrievedLatitude = getIntent().getDoubleExtra("lat", 0);
        Double retrievedLongitude = getIntent().getDoubleExtra("lng", 0);

        localTextView.setText("Local: " + retrievedLocal);
        proximidadeTextView.setText("Complemento: " + retrievedComplemento);
        numeroDeVagasTextView.setText("Número de Vagas: " + retrievedQuantidadeV);
        areaTextView.setText("Área: " + retrievedArea);
        coordenadasTextView.setText("Coordenadas: " + retrievedLatitude + "," + retrievedLongitude);

        Button buttonAvaliar = findViewById(R.id.button_Avaliar_essa_vaga);

        buttonAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificando Controle de Sessão
                if (isAuthenticated()) {
                    // Redirecionar para a tela de avaliação
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaAvaliacaoFormulario.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para avaliar as vagas, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
                }
            }
        });
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
}