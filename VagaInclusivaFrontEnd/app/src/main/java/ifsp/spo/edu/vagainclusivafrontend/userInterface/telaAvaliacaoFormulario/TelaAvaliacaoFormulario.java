package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Vaga;

@SuppressWarnings({"Convert2Lambda", "CanBeFinal", "RedundantIfStatement"})
public class TelaAvaliacaoFormulario extends AppCompatActivity {
    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];

    Button botaoAvaliar;

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
            setContentView(R.layout.tela_avaliacao_formulario);
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
                    Intent intent = new Intent(TelaAvaliacaoFormulario.this, TelaMapaAutenticado.class);
                    startActivity(intent);
                }
            });

            imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaAvaliacaoFormulario.this, TelaPerfil.class);
                    startActivity(intent);
                }
            });

            imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaAvaliacaoFormulario.this, TelaInfos.class);
                    startActivity(intent);
                }
            });

            imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaAvaliacaoFormulario.this, TelaFiltros.class);
                    startActivity(intent);
                }
            });

            // Avaliação
            /* INSTANCIA UMA CLASSE ACESSIBILIDADE */
            Acessibilidade[] acessibilidades = Acessibilidade.values();

            /* INSTANCIA UMA CLASSE VAGA PARA A FORMULARIO, ISSO VAI VIR DA API */
            Vaga vaga = new Vaga("R. Cavalheiro Nabuco", "1234567890123456789", "R. Joaquin Nabuco", "Bras", 10, 2.39, acessibilidades);

            /* ARREDONDA ESTRELINHA */
            int qtdEstrela = (int) Math.round(vaga.getMedia());

            /* DESENHA ESTRELINHA CONFORME A MÉDIA */
            for (int i = 1; i <= 5; i++) {
                int imageViewId = getResources().getIdentifier("estrela_" + i, "id", getPackageName());
                ImageView imageView = findViewById(imageViewId);

                if (i <= qtdEstrela) {
                    imageView.setImageResource(R.drawable.icon_star_gold);
                } else {
                    imageView.setImageResource(R.drawable.icon_star_filled);
                }
            }

            /* DESENHANDO NA TELA */
            /* DESENHANDO NO DADOS DA VAGA */
            TextView mediaTitle = findViewById(R.id.titulo_media);
            TextView localTextView = findViewById(R.id.container_local);
            TextView proximidadeTextView = findViewById(R.id.container_proximidade);
            TextView numeroDeVagasTextView = findViewById(R.id.container_numero_vagas);
            TextView areaTextView = findViewById(R.id.container_area);
            TextView coordenadasTextView = findViewById(R.id.container_coordenadas);

            mediaTitle.setText(getString(R.string.tela_vaga_individual_media) + " " + vaga.getMedia());
            localTextView.setText(getString(R.string.tela_avaliacao_container_local) + " " + vaga.getLocalidade());
            proximidadeTextView.setText(getString(R.string.tela_avaliacao_container_proximidade) + " " + vaga.getProximidade());
            numeroDeVagasTextView.setText(getString(R.string.tela_avaliacao_container_vagas) + " " + vaga.getQtdVaga());
            areaTextView.setText(getString(R.string.tela_avaliacao_container_area) + " " + vaga.getArea());
            coordenadasTextView.setText(getString(R.string.tela_avaliacao_container_coordenadas) + ":\n" + vaga.getCoordenada());

            LinearLayout linearLayoutAC = findViewById(R.id.tela_avaliacao_formulario_container_acessibilidades);

            /* DESENHANDO ACESSIBILIDADE */
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

            estrelas[0] = findViewById(R.id.estrela_avaliacao1);
            estrelas[1] = findViewById(R.id.estrela_avaliacao2);
            estrelas[2] = findViewById(R.id.estrela_avaliacao3);
            estrelas[3] = findViewById(R.id.estrela_avaliacao4);
            estrelas[4] = findViewById(R.id.estrela_avaliacao5);

            checkboxes[0] = findViewById(R.id.checkbox_acessibilidade1);
            checkboxes[1] = findViewById(R.id.checkbox_acessibilidade2);
            checkboxes[2] = findViewById(R.id.checkbox_acessibilidade3);
            checkboxes[3] = findViewById(R.id.checkbox_acessibilidade4);
            checkboxes[4] = findViewById(R.id.checkbox_acessibilidade5);
            checkboxes[5] = findViewById(R.id.checkbox_acessibilidade6);
            checkboxes[6] = findViewById(R.id.checkbox_acessibilidade7);
            checkboxes[7] = findViewById(R.id.checkbox_acessibilidade8);
            checkboxes[8] = findViewById(R.id.checkbox_acessibilidade9);

            for (int i = 0; i < maxRating; i++) {
                final int finalI = i;
                estrelas[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentRating = finalI + 1;
                        updateRatingImages();
                    }
                });
            }

            for (int i = 0; i < checkboxes.length; i++) {
                final int index = i;
                checkboxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleCheckbox(index);
                    }
                });
            }
        } else {
            redirect();
        }
    }

    private void updateRatingImages() {
        for (int i = 0; i < maxRating; i++) {
            if (i < currentRating) {
                estrelas[i].setImageResource(R.drawable.icon_star_gold);
            } else {
                estrelas[i].setImageResource(R.drawable.icon_star);
            }
        }
    }

    private void toggleCheckbox(int index) {
        if (checkboxStatus[index] == 0) {
            checkboxes[index].setImageResource(R.drawable.icon_checkbox);
            checkboxStatus[index] = 1;
        } else {
            checkboxes[index].setImageResource(R.drawable.icon_check2square);
            checkboxStatus[index] = 0;
        }
    }

    // Função para verificar se a pessoa esta autenticada
    @SuppressWarnings("ConstantConditions")
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