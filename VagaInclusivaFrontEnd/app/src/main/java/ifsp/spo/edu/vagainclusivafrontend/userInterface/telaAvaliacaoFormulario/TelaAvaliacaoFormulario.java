package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Vaga;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.VagaDetails;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.VagaManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"Convert2Lambda", "CanBeFinal", "RedundantIfStatement"})
public class TelaAvaliacaoFormulario extends AppCompatActivity {
    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];
    Button botaoAvaliar;
    private List<Integer> acess = new ArrayList<Integer>();
    String comment;
    Integer vagaId;
    Vaga vaga;
    EditText comentario;
    private String globalMail;
    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_avaliacao_formulario);
        fetchUserProfile();

        Intent intent = getIntent();
        vagaId = intent.getIntExtra("id", 0); // You can provide a default value
        VagaManager vagaManager = VagaManager.getInstance();
        Vaga vaga = vagaManager.getVagaById(vagaId);
        VagaDetails existingDetails = vaga.getVagaDetails();

        VagaDetails vagaDetails = vaga.getVagaDetails();
        String local = vagaDetails.getLocal();
        String complemento = vagaDetails.getComplemento();
        int quantidadev = vagaDetails.getQuantidadev();
        String area = vagaDetails.getArea();
        String coordenadas = vagaDetails.getCoordenadas();
        Log.e("API", local);
        Log.e("API", vagaId.toString());
        acess = vaga.getAcess();

        String coordenadasString = vagaDetails.getCoordenadas();
        String[] coordenadasParts = coordenadasString.replace("POINT (", "").replace(")", "").split(" ");
        double longitude = Double.parseDouble(coordenadasParts[0]);
        double latitude = Double.parseDouble(coordenadasParts[1]);

        // Exibir as coordenadas
        String coord = latitude + " " + longitude;

        TextView mediaTitle = findViewById(R.id.titulo_media);
        TextView localTextView = findViewById(R.id.container_local);
        TextView proximidadeTextView = findViewById(R.id.container_proximidade);
        TextView numeroDeVagasTextView = findViewById(R.id.container_numero_vagas);
        TextView areaTextView = findViewById(R.id.container_area);
        TextView coordenadasTextView = findViewById(R.id.container_coordenadas);

        mediaTitle.setText(getString(R.string.tela_vaga_individual_media) + " " + vaga.getMediaNotas());
        localTextView.setText(getString(R.string.tela_avaliacao_container_local) + " " + vagaDetails.getLocal());
        proximidadeTextView.setText(getString(R.string.tela_avaliacao_container_proximidade) + " " + vagaDetails.getComplemento());
        numeroDeVagasTextView.setText(getString(R.string.tela_avaliacao_container_vagas) + " " + vagaDetails.getQuantidadev());
        areaTextView.setText(getString(R.string.tela_avaliacao_container_area) + " " + vagaDetails.getArea());
        coordenadasTextView.setText(getString(R.string.tela_avaliacao_container_coordenadas) + ":\n" + coord);

        LinearLayout linearLayoutAC = findViewById(R.id.tela_avaliacao_formulario_container_acessibilidades);

        comentario = findViewById(R.id.container_comentarios_formulario);

        //Desenhar estrelas

        for (int i = 1; i <= 5; i++) {
            int imageViewId = getResources().getIdentifier("estrela_" + i, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);

            if (i <= vaga.getMediaNotas()) {
                imageView.setImageResource(R.drawable.icon_star_gold);
            } else {
                imageView.setImageResource(R.drawable.icon_star_filled);
            }
        }

        Acessibilidade[] acessibilidades = Acessibilidade.values();

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

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
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


            // Lógica para Avaliar
            botaoAvaliar = findViewById(R.id.button_enviar_avaliacao);

            botaoAvaliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentRating == 0) {
                        Toast.makeText(TelaAvaliacaoFormulario.this, "Você precisa selecionar uma nota para avaliar.", Toast.LENGTH_SHORT).show();
                        return; // Exit the onClick method if nota is not a valid integer
                    }

                    int j = 0;
                    for (Integer i = 0; i < checkboxes.length; i++) {
                        if(checkboxStatus[i] > 0) {
                            acess.add(i + 1); // Ajuste Acessibilidade
                            j++;
                        }
                    }

                    if (j == 0) {
                        Toast.makeText(TelaAvaliacaoFormulario.this, "Selecione no mínimo uma acessibilidade para avaliar.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    comment = comentario.getText().toString();

                    //UserManager userManager = UserManager.getInstance();

                    //UserLogin userLogin = userManager.getUserLogin();

                    System.out.println("email: " + globalMail);

                    Avaliacao avaliacao = new Avaliacao(currentRating, acess, vagaId, comment, globalMail);

                    Retrofit retrofit = new Retrofit.Builder()
                            // Android Studio
                            //.baseUrl("http://10.0.2.2:8000/")
                            // Celular Fisico
                            .baseUrl("http://18.209.252.205/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    API api = retrofit.create(API.class);

                    // Make the Retrofit POST request
                    Call<Void> call = api.submitAvaliacao(avaliacao);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            acess.clear();
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                String jsonAvaliacao = gson.toJson(avaliacao);

                                // Log the JSON string
                                Log.e("POST", jsonAvaliacao);
                                Toast.makeText(TelaAvaliacaoFormulario.this, "Avaliacao submitted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                String jsonAvaliacao = gson.toJson(avaliacao);

                                // Log the JSON string
                                Log.e("POST", jsonAvaliacao);
                                // Handle an unsuccessful response
                                Toast.makeText(TelaAvaliacaoFormulario.this, "Failed to submit avaliacao", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(TelaAvaliacaoFormulario.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
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

    // Recuperando Dados para Disponibilizar na Tela (Nome, Email e Data de Criação da Conta)
    private void fetchUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("AUTH_KEY", "");

        OkHttpClient client = new OkHttpClient();
        // Android Studio
        //String url = "http://10.0.2.2:8000/users/";
        // Celular Fisico
        String url = "http://18.209.252.205/users/";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Token " + authToken)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseBody);
                        if (jsonArray.length() > 0) {
                            JSONObject userJson = jsonArray.getJSONObject(0); // Pega o primeiro objeto do array
                            String email = userJson.getString("email");

                            globalMail = email;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Usuário Não Autenticado");
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

    // Função para a Pessoa caso ela não seja autenticada
    private void redirect() {
        Intent intent = new Intent(this, TelaLogin.class);
        startActivity(intent);
        finish();
    }
}