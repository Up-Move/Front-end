package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserLogin;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"Convert2Lambda", "CanBeFinal"})
public class TelaFiltros extends AppCompatActivity {

    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];
    Button botaoSalvar;
    Button botaoFiltrar;
    private String globalMail;

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    private List<Integer> acess = new ArrayList<Integer>();
    private List<Integer> savedAcess = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_filtros);
        fetchUserProfile();

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
                Intent intent = new Intent(TelaFiltros.this, TelaMapaAutenticado.class);
                startActivity(intent);
            }
        });

        imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFiltros.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFiltros.this, TelaInfos.class);
                startActivity(intent);
            }
        });

        //imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(TelaFiltros.this, TelaFiltros.class);
        //        startActivity(intent);
        //    }
        //});

        estrelas[0] = findViewById(R.id.filtro_estrela_avaliacao1);
        estrelas[1] = findViewById(R.id.filtro_estrela_avaliacao2);
        estrelas[2] = findViewById(R.id.filtro_estrela_avaliacao3);
        estrelas[3] = findViewById(R.id.filtro_estrela_avaliacao4);
        estrelas[4] = findViewById(R.id.filtro_estrela_avaliacao5);

        checkboxes[0] = findViewById(R.id.filtro_checkbox_acessibilidade1);
        checkboxes[1] = findViewById(R.id.filtro_checkbox_acessibilidade2);
        checkboxes[2] = findViewById(R.id.filtro_checkbox_acessibilidade3);
        checkboxes[3] = findViewById(R.id.filtro_checkbox_acessibilidade4);
        checkboxes[4] = findViewById(R.id.filtro_checkbox_acessibilidade5);
        checkboxes[5] = findViewById(R.id.filtro_checkbox_acessibilidade6);
        checkboxes[6] = findViewById(R.id.filtro_checkbox_acessibilidade7);
        checkboxes[7] = findViewById(R.id.filtro_checkbox_acessibilidade8);
        checkboxes[8] = findViewById(R.id.filtro_checkbox_acessibilidade9);

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


        // Aplicar filtro atual
        botaoFiltrar = findViewById(R.id.filtro_btn_filtrar);

        botaoFiltrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                acess.clear();

                //UserManager userManager = UserManager.getInstance();

                //UserLogin userLogin = userManager.getUserLogin();

                Filtro filtro = new Filtro(globalMail,"Placeholder",  acess, currentRating);
                int j = 0;
                for (Integer i = 0; i < checkboxes.length; i++) {
                    if (checkboxStatus[i] > 0) {
                        acess.add(i + 1);
                        j++;
                    }
                }

                /*
                if (j == 0) {
                    Toast.makeText(TelaFiltros.this, "Selecione no mínimo uma acessibilidade para avaliar.", Toast.LENGTH_SHORT).show();
                    return;
                } */

                applyFilter(filtro);
            }

        });


        // Lógica para Salvar
        botaoSalvar = findViewById(R.id.filtro_btn_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acess.clear();

                int j = 0;
                for (Integer i = 0; i < checkboxes.length; i++) {
                    if (checkboxStatus[i] > 0) {
                        acess.add((i+1));
                        j++;
                    }
                }

                /*
                if (j == 0) {
                    Toast.makeText(TelaFiltros.this, "Selecione no mínimo uma acessibilidade para avaliar.", Toast.LENGTH_SHORT).show();
                    return;
                } */


                //UserManager userManager = UserManager.getInstance();

                //UserLogin userLogin = userManager.getUserLogin();


                Filtro filtro = new Filtro(globalMail,"Placeholder", acess, currentRating);

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://18.209.252.205/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API api = retrofit.create(API.class);

                // Make the Retrofit POST request
                Call<Void> call = api.submitFiltro(filtro);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            String jsonAvaliacao = gson.toJson(filtro);

                            // Log the JSON string
                            Log.e("POST", jsonAvaliacao);
                            Toast.makeText(TelaFiltros.this, "Filtro Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                            acess.clear();

                        } else {
                            Gson gson = new Gson();
                            String jsonAvaliacao = gson.toJson(filtro);

                            // Log the JSON string
                            Log.e("POST", jsonAvaliacao);
                            // Handle an unsuccessful response
                            Toast.makeText(TelaFiltros.this, "Falha ao Salvar Filtro", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaFiltros.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void applyFilter(Filtro filtro) {

        UserManager userManager = UserManager.getInstance();

        UserLogin userLogin = userManager.getUserLogin();

        userManager.setAcess(filtro.getAcess());

        userManager.setNota(filtro.getNota());

        Toast.makeText(TelaFiltros.this, "Aplicando filtros...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(TelaFiltros.this, TelaMapaAutenticado.class);

        startActivity(intent);

        finish();
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
                            //Lógica para pegar filtros
                            Retrofit retrofit2 = new Retrofit.Builder()
                                    // Android Studio
                                    //.baseUrl("http://10.0.2.2:8000/")
                                    // Celular Fisico
                                    .baseUrl("http://18.209.252.205/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            API apiService = retrofit2.create(API.class);

                            //UserManager userManager = UserManager.getInstance();

                            //UserLogin userLogin = userManager.getUserLogin();

                            System.out.println("email: " + globalMail);

                            Call<List<Filtro>> call2 = apiService.getFiltroByUsername(globalMail); // Adjust the first and last parameters as needed

                            call2.enqueue(new Callback<List<Filtro>>() {
                                @Override
                                public void onResponse(Call<List<Filtro>> call2, Response<List<Filtro>> response2) {
                                    if (response2.isSuccessful()) {
                                        List<Filtro> filtros = response2.body();

                                        Gson gson = new GsonBuilder()
                                                .create();

                                        String jsonResponse = gson.toJson(response2.body());

                                        Log.e("Filtro", jsonResponse);

                                        Log.e("Filtro","filtros size:" + filtros.size());

                                        if (filtros.size() > 0) {
                                            TextView filtroContainer = findViewById(R.id.tela_filtro_container);
                                            filtroContainer.setVisibility(View.INVISIBLE);
                                            Acessibilidade[] acessibilidades = Acessibilidade.values();

                                            LinearLayout linearLayoutFiltro = findViewById(R.id.tela_filtro_container_filtro_salvo);

                                            LinearLayout estrelasLayout = findViewById(R.id.tela_filtro_container_filtro_estrela);

                                            for (Filtro filtro : filtros) {
                                                savedAcess = filtro.getAcess();
                                                for (Acessibilidade acessibilidade : acessibilidades) {

                                                    if (!savedAcess.contains(acessibilidade.descricaoResourceId)) continue;

                                                    TextView textView = new TextView(TelaFiltros.this);
                                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                                    );
                                                    params.setMargins(0, 0, 0, 8);
                                                    textView.setLayoutParams(params);
                                                    textView.setText(acessibilidade.descricao);
                                                    textView.setFontFeatureSettings(String.valueOf(R.font.jura));
                                                    textView.setTextColor(getResources().getColor(R.color.primary_text));
                                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                                    linearLayoutFiltro.addView(textView);

                                                    linearLayoutFiltro.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            applyFilter(filtro);

                                                        }
                                                    });

                                                }

                                                /* SE NÃO TIVER AVALIACAO NO FILTRO SALVO, VAI TIRAR O LAYOUT DAS ESTRELAS */
                                                if (filtro.getNota() == 0) {
                                                    estrelasLayout.setVisibility(View.GONE);
                                                }
                                                int vetorEstrelas = R.drawable.icon_star_gold;
                                                for (int i = 1; i <= 5; i++) {
                                                    ImageView estrelaImageView = estrelasLayout.findViewById(getResources().getIdentifier("tela_filtro_container_filtro_estrela_" + i, "id", getPackageName()));
                                                    if (i <= filtro.getNota()) {
                                                        estrelaImageView.setImageResource(vetorEstrelas);
                                                    } else {
                                                        estrelaImageView.setImageResource(R.drawable.icon_star_filled);
                                                    }
                                                }


                                            }
                                        } else {
                                            Toast.makeText(TelaFiltros.this, "Você não tem Filtros Salvos.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Log.e("Filtros", "Error");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Filtro>> call, Throwable t) {
                                    Log.e("Filtros", "Error: " + t.getMessage());
                                }
                            });
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
}