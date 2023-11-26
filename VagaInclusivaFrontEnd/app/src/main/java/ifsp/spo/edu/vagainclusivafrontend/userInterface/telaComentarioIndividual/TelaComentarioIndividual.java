package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioIndividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario.Resposta;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("Convert2Lambda")
public class TelaComentarioIndividual extends AppCompatActivity {

    Integer comentarioId;
    String comentario;
    String usuario;
    Integer nota;
    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_individual);

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

        Intent intent = getIntent();
        comentarioId = intent.getIntExtra("id", 0);// You can provide a default value
        comentario = intent.getStringExtra("comentario");
        usuario = intent.getStringExtra("user");
        nota = intent.getIntExtra("nota", 0);

        TextView nameTextView = findViewById(R.id.tela_comentario_individual_nome);
        TextView descTextView = findViewById(R.id.tela_comentario_individual_descricao);

        TextView nomeTitle = findViewById(R.id.tela_comentario_individual_title);
        nomeTitle.setText(getString(R.string.tela_comentario_individual_titulo) + " " + usuario);
        TextView nomeIndividualTextView = findViewById(R.id.tela_comentario_individual_nome);
        nomeIndividualTextView.setText(usuario);

        TextView descricaoIndividualTextView = findViewById(R.id.tela_comentario_individual_descricao);
        descricaoIndividualTextView.setText(comentario);

        LinearLayout estrelasLayout = findViewById(R.id.tela_comentario_individual_estrela);

        int vetorEstrelas = R.drawable.icon_star_gold;
        for (int i = 1; i <= 5; i++) {
            ImageView estrelaImageView = estrelasLayout.findViewById(getResources().getIdentifier("tela_comentario_individual_estrela_" + i, "id", getPackageName()));
            if (i <= nota) {
                estrelaImageView.setImageResource(vetorEstrelas);
            } else {
                estrelaImageView.setImageResource(R.drawable.icon_star_filled);
            }
        }

        LinearLayout parentLayout = findViewById(R.id.tela_comentario_individual_viewScroll_linear);



        //Preencher respostas
        Retrofit retrofit2 = new Retrofit.Builder()
                // Android Studio
                //.baseUrl("http://10.0.2.2:8000/")
                // Celular Fisico
                .baseUrl("http://18.209.252.205/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API apiService = retrofit2.create(API.class);

        Call<List<Resposta>> call2 = apiService.getRespostas(comentarioId); // Adjust the first and last parameters as needed

        call2.enqueue(new Callback<List<Resposta>>() {
            @Override
            public void onResponse(Call<List<Resposta>> call2, Response<List<Resposta>> response2) {
                if (response2.isSuccessful()) {
                    List<Resposta> respostas = response2.body();


                    Gson gson = new GsonBuilder()
                            .create();

                    String jsonResponse = gson.toJson(response2.body());

                    Log.e("Respostas", jsonResponse);

                    Log.e("Respostas", "resposta" + respostas.size());


                    // Loop through your comments and show/hide comment sections
                    for (int i = 0; i < respostas.size(); i++) {
                        Resposta resposta = respostas.get(i);

                        LinearLayout avaliacaoLayout = new LinearLayout(TelaComentarioIndividual.this);
                        avaliacaoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                200));
                        avaliacaoLayout.setOrientation(LinearLayout.VERTICAL);
                        avaliacaoLayout.setBackgroundResource(R.color.background);
                        avaliacaoLayout.setPadding(26, 16, 26, 0);

                        TextView nomeTextView = new TextView(TelaComentarioIndividual.this);
                        nomeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        nomeTextView.setText(resposta.getUser());
                        nomeTextView.setTextColor(ContextCompat.getColor(TelaComentarioIndividual.this, R.color.primary));
                        nomeTextView.setTextSize(12);
                        nomeTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioIndividual.this, R.font.jura));
                        nomeTextView.setPadding(8, 8, 8, 0);
                        nomeTextView.setTypeface(null, Typeface.NORMAL);

                        TextView descricaoTextView = new TextView(TelaComentarioIndividual.this);
                        descricaoTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                146));
                        descricaoTextView.setText(resposta.getComentario());
                        descricaoTextView.setTextColor(ContextCompat.getColor(TelaComentarioIndividual.this, R.color.primary_text));
                        descricaoTextView.setTextSize(10);
                        descricaoTextView.setTypeface(ResourcesCompat.getFont(TelaComentarioIndividual.this, R.font.jura));
                        descricaoTextView.setPadding(8, 0, 8, 0);

                        avaliacaoLayout.addView(nomeTextView);
                        avaliacaoLayout.addView(descricaoTextView);

                        parentLayout.addView(avaliacaoLayout);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Resposta>> call, Throwable t) {
                Log.e("Resposta", "Error: " + t.getMessage());
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}