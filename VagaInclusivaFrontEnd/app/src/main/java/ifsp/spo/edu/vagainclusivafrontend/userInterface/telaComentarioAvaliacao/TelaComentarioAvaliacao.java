package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioAvaliacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario.TelaComentarioFormulario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioIndividual.TelaComentarioIndividual;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Comentario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Vaga;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.VagaManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class TelaComentarioAvaliacao extends AppCompatActivity {

    Vaga existingVaga;
    Integer vagaId;

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_avaliacao);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();

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
            }
        } else {
            redirect();
        }

        Intent intent = getIntent();
        vagaId = intent.getIntExtra("id", 0); // You can provide a default value
        VagaManager vagaManager = VagaManager.getInstance();
        existingVaga = vagaManager.getVagaById(vagaId);
        Log.e("Avaliação", vagaId.toString());

        //Pegar comentários
        Retrofit retrofit2 = new Retrofit.Builder()
                // Android Studio
                //.baseUrl("http://10.0.2.2:8000/")
                // Celular Fisico
                .baseUrl("http://18.209.252.205/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API apiService = retrofit2.create(API.class);

        Call<List<Comentario>> call2 = apiService.getComentarios(vagaId, 1, 2); // Adjust the first and last parameters as needed

        call2.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call2, Response<List<Comentario>> response2) {
                if (response2.isSuccessful()) {
                    List<Comentario> comentarios = response2.body();

                    Gson gson = new GsonBuilder()
                            .create();

                    String jsonResponse = gson.toJson(response2.body());

                    Log.e("Comentario", jsonResponse);

                    Log.e("Comentario","cometnario" + comentarios.size());

                    LinearLayout parentLayout = findViewById(R.id.tela_comentario_vaga_avaliacao_viewScroll_linear);

                    // Loop through your comments and show/hide comment sections
                    for (int i = 0; i < comentarios.size(); i++) {

                        Comentario comentario = comentarios.get(i);

                        LinearLayout avaliacaoLayout = new LinearLayout(TelaComentarioAvaliacao.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                260
                        );
                        params.setMargins(16, 16, 16, 0);
                        avaliacaoLayout.setLayoutParams(params);
                        avaliacaoLayout.setOrientation(LinearLayout.VERTICAL);
                        avaliacaoLayout.setBackgroundResource(R.color.background);
                        avaliacaoLayout.setPadding(16, 8, 16, 8);

                        LinearLayout estrelaNomeLayout = new LinearLayout(TelaComentarioAvaliacao.this);
                        estrelaNomeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        estrelaNomeLayout.setOrientation(LinearLayout.HORIZONTAL);

                        TextView nomeTextView = new TextView(TelaComentarioAvaliacao.this);
                        nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        nomeTextView.setText(comentario.getUsername());
                        nomeTextView.setTextColor(ContextCompat.getColor(TelaComentarioAvaliacao.this, R.color.primary));
                        nomeTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioAvaliacao.this, R.font.jura));
                        nomeTextView.setTextSize(12);
                        nomeTextView.setTypeface(null, Typeface.NORMAL);

                        LinearLayout estrelaLayout = new LinearLayout(TelaComentarioAvaliacao.this);
                        estrelaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        estrelaLayout.setOrientation(LinearLayout.HORIZONTAL);
                        estrelaLayout.setGravity(Gravity.RIGHT);
                        estrelaLayout.setPadding(0,2,32,0);

                        for (int j = 0; j < 5; j++) {
                            ImageView estrelaImageView = new ImageView(TelaComentarioAvaliacao.this);
                            estrelaImageView.setLayoutParams(new LinearLayout.LayoutParams(
                                    30, 30
                            ));
                            if (j < comentario.getNota()) {
                                estrelaImageView.setImageResource(R.drawable.icon_star_gold); // Estrela preenchida
                            } else {
                                estrelaImageView.setImageResource(R.drawable.icon_star_filled); // Estrela vazia
                            }
                            estrelaLayout.addView(estrelaImageView);
                        }

                        estrelaNomeLayout.addView(nomeTextView);
                        estrelaNomeLayout.addView(estrelaLayout);

                        TextView descricaoTextView = new TextView(TelaComentarioAvaliacao.this);
                        descricaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                140
                        ));
                        descricaoTextView.setText(comentario.getComentario());
                        descricaoTextView.setTextColor(ContextCompat.getColor(TelaComentarioAvaliacao.this, R.color.primary_text));
                        descricaoTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioAvaliacao.this, R.font.jura));
                        descricaoTextView.setTextSize(10);

                        LinearLayout avaliacaoRespostaLayout = new LinearLayout(TelaComentarioAvaliacao.this);
                        avaliacaoRespostaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        avaliacaoRespostaLayout.setOrientation(LinearLayout.HORIZONTAL);
                        TextView avaliacaoTextView = new TextView(TelaComentarioAvaliacao.this);
                        avaliacaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        avaliacaoTextView.setText(getString(R.string.tela_comentario_avaliacao_avaliacao));
                        avaliacaoTextView.setTextColor(ContextCompat.getColor(TelaComentarioAvaliacao.this, R.color.primary));
                        avaliacaoTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioAvaliacao.this, R.font.jura));
                        avaliacaoTextView.setTextSize(10);
                        avaliacaoTextView.setTypeface(null, Typeface.NORMAL);

                        TextView responderTextView = new TextView(TelaComentarioAvaliacao.this);
                        LinearLayout.LayoutParams responderLayoutParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        responderLayoutParams.setMargins(40, 0, 0, 0);
                        responderTextView.setLayoutParams(responderLayoutParams);

                        responderTextView.setText(getString(R.string.tela_comentario_individual_responder));
                        responderTextView.setTextColor(ContextCompat.getColor(TelaComentarioAvaliacao.this, R.color.primary));
                        responderTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioAvaliacao.this, R.font.jura));
                        responderTextView.setTextSize(10);
                        responderTextView.setTypeface(null, Typeface.NORMAL);

                        avaliacaoRespostaLayout.addView(avaliacaoTextView);
                        avaliacaoRespostaLayout.addView(responderTextView);

                        avaliacaoLayout.addView(estrelaNomeLayout);
                        avaliacaoLayout.addView(descricaoTextView);
                        avaliacaoLayout.addView(avaliacaoRespostaLayout);

                        parentLayout.addView(avaliacaoLayout);

                        avaliacaoTextView.setText("Avaliação tem " + comentario.getRespostas() + " comentários");
                        responderTextView.setText("Responder");

                        responderTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Verificando Controle de Sessão
                                // Redirecionar para a tela de avaliação
                                Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaComentarioFormulario.class);
                                intent.putExtra("id", comentario.getId());
                                intent.putExtra("user", comentario.getUsername());
                                intent.putExtra("comentario", comentario.getComentario());
                                startActivity(intent);

                            }
                        });

                        avaliacaoTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Verificando Controle de Sessão
                                if (isAuthenticated()) {
                                    // Redirecionar para a tela de avaliação
                                    Intent intent = new Intent(TelaComentarioAvaliacao.this, TelaComentarioIndividual.class);
                                    intent.putExtra("id", comentario.getId());
                                    intent.putExtra("user", comentario.getUsername());
                                    intent.putExtra("comentario", comentario.getComentario());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(TelaComentarioAvaliacao.this, "Para responder, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }


                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.e("Comentario", "Error: " + t.getMessage());
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

    // Função para a Pessoa caso ela não seja autenticada
    private void redirect() {
        Intent intent = new Intent(this, TelaLogin.class);
        startActivity(intent);
        finish();
    }
}