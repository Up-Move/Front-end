package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.TelaAvaliacaoFormulario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioAvaliacao.TelaComentarioAvaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario.TelaComentarioFormulario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioIndividual.TelaComentarioIndividual;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa.TelaMapa;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"WrapperTypeMayBePrimitive", "ConstantConditions", "Convert2Lambda", "RedundantIfStatement"})
public class TelaVagaIndividual extends AppCompatActivity {

    private String local;
    private String proximidade;
    private int numeroDeVagas;
    private String area;
    private String coordenadas;

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    String[] nomes = {"Joana", "João", "Pedro"};
    int[] estrelas = {5, 3, 4};
    String[] descricoes = {"blablabla1", "blablabla2", "blablabla3"};
    int[] quantidadesComentario = {4, 0, 1};

    Vaga existingVaga;
    Integer vagaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vaga_individual);
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


        Acessibilidade[] acessibilidades = Acessibilidade.values();

        // Pegar informações da vaga
        Log.e("Avaliação", "getAvaliacaoNota");

        Intent intent = getIntent();
        vagaId = intent.getIntExtra("id", 0); // You can provide a default value
        VagaManager vagaManager = VagaManager.getInstance();
        existingVaga = vagaManager.getVagaById(vagaId);
        Log.e("Avaliação", vagaId.toString());

        List<Integer> acess = existingVaga.getAcess();
        Log.e("Avaliação", acess.toString());

        Retrofit retrofit = new Retrofit.Builder()
                // Android Studio
                //.baseUrl("http://10.0.2.2:8000/")
                // Celular Fisico
                .baseUrl("http://18.209.252.205/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .build();
        API vagaService = retrofit.create(API.class);

        Call<Vaga> call = vagaService.getDataById(vagaId);

        call.enqueue(new Callback<Vaga>() {
            @Override
            public void onResponse(Call<Vaga> call, Response<Vaga> response) {
                if (response.isSuccessful()) {

                    Vaga vaga = response.body();
                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create();

                    String jsonResponse = gson.toJson(response.body());
                    // Convert the response body to JSON string
                    Log.e("API", jsonResponse);

                    VagaDetails vagaDetails = vaga.getVagaDetails();
                    String local = vagaDetails.getLocal();
                    String complemento = vagaDetails.getComplemento();
                    int quantidadev = vagaDetails.getQuantidadev();
                    String area = vagaDetails.getArea();

                    // Extrair as coordenadas da string no formato "POINT (longitude latitude)"
                    String coordenadasString = vagaDetails.getCoordenadas();
                    String[] coordenadasParts = coordenadasString.replace("POINT (", "").replace(")", "").split(" ");
                    double longitude = Double.parseDouble(coordenadasParts[0]);
                    double latitude = Double.parseDouble(coordenadasParts[1]);

                    // Exibir as coordenadas
                    String coordenadas = latitude + " " + longitude;

                    existingVaga.setTotalAvaliacoes(vaga.getTotalAvaliacoes());
                    existingVaga.setMediaNotas(vaga.getMediaNotas());
                    existingVaga.setVaga(vaga.getVaga());

                    VagaDetails existingDetails = vaga.getVagaDetails();
                    existingVaga.setVagaDetails(existingDetails);

                    TextView mediaTitle = findViewById(R.id.tela_avaliacao_titulo_media);
                    TextView localTextView = findViewById(R.id.tela_avaliacao_container_local);
                    TextView proximidadeTextView = findViewById(R.id.tela_avaliacao_container_proximidade);
                    TextView numeroDeVagasTextView = findViewById(R.id.tela_avaliacao_container_numero_vagas);
                    TextView areaTextView = findViewById(R.id.tela_avaliacao_container_area);
                    TextView coordenadasTextView = findViewById(R.id.tela_avaliacao_container_coordenadas);
                    existingVaga.setVaga(vaga.getVaga());

                    mediaTitle.setText(getString(R.string.tela_vaga_individual_media) + " " + vaga.getMediaNotas());
                    localTextView.setText(getString(R.string.tela_avaliacao_container_local) + " " + existingDetails.getLocal());
                    proximidadeTextView.setText(getString(R.string.tela_avaliacao_container_proximidade) + " " + existingDetails.getComplemento());
                    numeroDeVagasTextView.setText(getString(R.string.tela_avaliacao_container_vagas) + " " + existingDetails.getQuantidadev());
                    areaTextView.setText(getString(R.string.tela_avaliacao_container_area) + " " + existingDetails.getArea());
                    coordenadasTextView.setText(getString(R.string.tela_avaliacao_container_coordenadas) + ":\n" + coordenadas);
                } else {
                    Log.e("Avaliação", "Error");

                }
            }

            @Override
            public void onFailure(Call<Vaga> call, Throwable t) {
                Log.e("Avaliação", "Error: " + t.getMessage());
            }
        });


        //Desenhar estrelas
        for (int i = 1; i <= 5; i++) {
            int imageViewId = getResources().getIdentifier("tela_avaliacao_estrela_" + i, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);

            if (i <= existingVaga.getMediaNotas()) {
                imageView.setImageResource(R.drawable.icon_star_gold);
            } else {
                imageView.setImageResource(R.drawable.icon_star_filled);
            }
        }

        LinearLayout linearLayoutAC = findViewById(R.id.tela_avaliacao_container_acessibilidades);
        LinearLayout linearLayoutAV = findViewById(R.id.container_comentarios_avaliacao);

        //Desenhar acessibilidades
        for (Acessibilidade acessibilidade : acessibilidades) {

            Log.e("Avaliação", String.valueOf(acessibilidade.descricaoResourceId));

            if (!acess.contains(acessibilidade.descricaoResourceId)) continue;

            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 8);
            textView.setLayoutParams(params);
            textView.setText(acessibilidade.descricao);
            textView.setMaxEms(9);
            textView.setFontFeatureSettings(String.valueOf(R.font.jura));
            textView.setTextColor(getResources().getColor(R.color.primary_text));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            linearLayoutAC.addView(textView);
        }

        //Pegar comentários
        Retrofit retrofit2 = new Retrofit.Builder()
                // Android Studio
                //.baseUrl("http://10.0.2.2:8000/")
                // Celular Fisico
                .baseUrl("http://18.209.252.205/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API apiService = retrofit2.create(API.class);

        Call<List<Comentario>> call2 = apiService.getComentarios(vagaId, 1, 3); // Adjust the first and last parameters as needed

        call2.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call2, Response<List<Comentario>> response2) {
                if (response2.isSuccessful()) {
                    List<Comentario> comentarios = response2.body();

                    Gson gson = new GsonBuilder()
                            .create();

                    String jsonResponse = gson.toJson(response2.body());

                    Log.e("Comentario", jsonResponse);

                    Log.e("Comentario","comentario" + comentarios.size());


                    if (comentarios.size() > 0) {
                        TextView avisoTextView = findViewById(R.id.titulo_comentarios_dessa_vaga_sem_comentario);
                        TextView VerMaisTextView = findViewById(R.id.button_inline_veja_mais_comentarios);
                        ImageView SetaImageView = findViewById(R.id.veja_comentarios_seta);
                        avisoTextView.setVisibility(View.GONE);
                        VerMaisTextView.setVisibility(View.VISIBLE);
                        SetaImageView.setVisibility(View.VISIBLE);
                    }


                    // Loop through your comments and show/hide comment sections
                    for (int i = 0; i < comentarios.size(); i++) {

                        Comentario comentario = comentarios.get(i);

                        LinearLayout avaliacaoLayout = new LinearLayout(TelaVagaIndividual.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                220
                        );
                        params.setMargins(16, 16, 16, 0);
                        avaliacaoLayout.setLayoutParams(params);
                        avaliacaoLayout.setOrientation(LinearLayout.VERTICAL);
                        avaliacaoLayout.setBackgroundResource(R.color.background);
                        avaliacaoLayout.setPadding(16, 8, 16, 8);

                        LinearLayout estrelaNomeLayout = new LinearLayout(TelaVagaIndividual.this);
                        estrelaNomeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        estrelaNomeLayout.setOrientation(LinearLayout.HORIZONTAL);

                        TextView nomeTextView = new TextView(TelaVagaIndividual.this);
                        nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        nomeTextView.setText(comentario.getUsername());
                        nomeTextView.setTextColor(ContextCompat.getColor(TelaVagaIndividual.this, R.color.primary));
                        nomeTextView.setTypeface(ResourcesCompat.getFont(TelaVagaIndividual.this, R.font.jura));
                        nomeTextView.setTextSize(12);
                        nomeTextView.setTypeface(null, Typeface.NORMAL);

                        LinearLayout estrelaLayout = new LinearLayout(TelaVagaIndividual.this);
                        estrelaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        estrelaLayout.setOrientation(LinearLayout.HORIZONTAL);
                        estrelaLayout.setGravity(Gravity.RIGHT);
                        estrelaLayout.setPadding(0,2,32,0);

                        for (int j = 0; j < 5; j++) {
                            ImageView estrelaImageView = new ImageView(TelaVagaIndividual.this);
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

                        TextView descricaoTextView = new TextView(TelaVagaIndividual.this);
                        descricaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                100
                        ));
                        descricaoTextView.setText(comentario.getComentario());
                        descricaoTextView.setTextColor(ContextCompat.getColor(TelaVagaIndividual.this, R.color.primary_text));
                        descricaoTextView.setTypeface(ResourcesCompat.getFont(TelaVagaIndividual.this, R.font.jura));
                        descricaoTextView.setTextSize(10);

                        LinearLayout avaliacaoRespostaLayout = new LinearLayout(TelaVagaIndividual.this);
                        avaliacaoRespostaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        avaliacaoRespostaLayout.setOrientation(LinearLayout.HORIZONTAL);
                        TextView avaliacaoTextView = new TextView(TelaVagaIndividual.this);
                        avaliacaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        avaliacaoTextView.setText(getString(R.string.tela_comentario_avaliacao_avaliacao));
                        avaliacaoTextView.setTextColor(ContextCompat.getColor(TelaVagaIndividual.this, R.color.primary));
                        avaliacaoTextView.setTypeface(ResourcesCompat.getFont(TelaVagaIndividual.this, R.font.jura));
                        avaliacaoTextView.setTextSize(10);
                        avaliacaoTextView.setTypeface(null, Typeface.NORMAL);

                        TextView responderTextView = new TextView(TelaVagaIndividual.this);
                        LinearLayout.LayoutParams responderLayoutParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        responderLayoutParams.setMargins(40, 0, 0, 0);
                        responderTextView.setLayoutParams(responderLayoutParams);

                        responderTextView.setText(getString(R.string.tela_comentario_individual_responder));
                        responderTextView.setTextColor(ContextCompat.getColor(TelaVagaIndividual.this, R.color.primary));
                        responderTextView.setTypeface(ResourcesCompat.getFont(TelaVagaIndividual.this, R.font.jura));
                        responderTextView.setTextSize(10);
                        responderTextView.setTypeface(null, Typeface.NORMAL);

                        avaliacaoRespostaLayout.addView(avaliacaoTextView);
                        avaliacaoRespostaLayout.addView(responderTextView);

                        avaliacaoLayout.addView(estrelaNomeLayout);
                        avaliacaoLayout.addView(descricaoTextView);
                        avaliacaoLayout.addView(avaliacaoRespostaLayout);

                        linearLayoutAV.addView(avaliacaoLayout);

                        // Set text and other data for each TextView based on the comment data
                        //Log.e("Avaliação", comentario.getComentario());
                        //int user = comentario.getUser();
                        nomeTextView.setText(comentario.getUsername());
                        descricaoTextView.setText(comentario.getComentario());
                        avaliacaoTextView.setText("Avaliação tem " + comentario.getRespostas() + " comentários");
                        responderTextView.setText("Responder");

                        responderTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Verificando Controle de Sessão
                                if (isAuthenticated()) {
                                    // Redirecionar para a tela de avaliação
                                    Intent intent = new Intent(TelaVagaIndividual.this, TelaComentarioFormulario.class);
                                    intent.putExtra("id", comentario.getId());
                                    intent.putExtra("user", comentario.getUsername());
                                    intent.putExtra("comentario", comentario.getComentario());
                                    intent.putExtra("nota", comentario.getNota());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(TelaVagaIndividual.this, "Para responder, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        avaliacaoTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Verificando Controle de Sessão
                                if (isAuthenticated()) {
                                    // Redirecionar para a tela de avaliação
                                    Intent intent = new Intent(TelaVagaIndividual.this, TelaComentarioIndividual.class);
                                    intent.putExtra("id", comentario.getId());
                                    intent.putExtra("user", comentario.getUsername());
                                    intent.putExtra("comentario", comentario.getComentario());
                                    intent.putExtra("nota", comentario.getNota());

                                    startActivity(intent);
                                } else {
                                    Toast.makeText(TelaVagaIndividual.this, "Para ver os comentários nessa avaliação, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }

                    // Hide any remaining comment sections
                    for (int i = comentarios.size(); i < 3; i++) {
                        //logica para sumir vagas não utilizadas
                    }

                } else {
                    Log.e("Comentario", "Error");
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.e("Comentario", "Error: " + t.getMessage());
            }
        });

        Button buttonAvaliar = findViewById(R.id.button_Avaliar_essa_vaga);

        buttonAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificando Controle de Sessão
                if (isAuthenticated()) {
                    // Redirecionar para a tela de avaliação
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaAvaliacaoFormulario.class);
                    intent.putExtra("id", vagaId);
                    startActivity(intent);

                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para avaliar as vagas, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView buttonVejaMais = findViewById(R.id.button_inline_veja_mais_comentarios);

        buttonVejaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificando Controle de Sessão
                if (isAuthenticated()) {
                    // Redirecionar para a tela de avaliação
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaComentarioAvaliacao.class);
                    intent.putExtra("id", vagaId);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para ver todos os comentários das avaliações, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
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