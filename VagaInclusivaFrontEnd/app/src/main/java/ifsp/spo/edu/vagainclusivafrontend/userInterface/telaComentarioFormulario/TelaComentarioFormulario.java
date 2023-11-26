package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.TelaAvaliacaoFormulario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
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

@SuppressWarnings("Convert2Lambda")
public class TelaComentarioFormulario extends AppCompatActivity {

    Integer comentarioId;
    String comentario;
    String usuario;
    Button botaoComentar;
    EditText comentarioBox;
    private String globalMail;

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_comentario_formulario);
        fetchUserProfile();
        if(getSupportActionBar() != null) {
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

        Intent intent = getIntent();
        comentarioId = intent.getIntExtra("id", 0);// You can provide a default value
        comentario = intent.getStringExtra("comentario");
        usuario = intent.getStringExtra("user");

        TextView nomeTitle = findViewById(R.id.tela_comentario_formulario_title);

        nomeTitle.setText(getString(R.string.tela_comentario_formulario_titulo) + " " + usuario);
        TextView nome = findViewById(R.id.tela_comentario_formulario_nome);
        nome.setText(usuario);
        TextView descricao = findViewById(R.id.tela_comentario_formulario_descricao);
        descricao.setText(comentario);

        comentarioBox = findViewById(R.id.tela_comentario_formulario_texto);

        botaoComentar = findViewById(R.id.button_atualizar_email);

        botaoComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentario = comentarioBox.getText().toString();


                if (comentario.isEmpty()) {
                    Toast.makeText(TelaComentarioFormulario.this, "Campo de resposta vazio.", Toast.LENGTH_SHORT).show();
                    return;
                }


                //UserManager userManager = UserManager.getInstance();

                //UserLogin userLogin = userManager.getUserLogin();

                System.out.println("email: " + globalMail);

                Resposta resposta = new Resposta(comentario,  globalMail, comentarioId);

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://18.209.252.205/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API api = retrofit.create(API.class);

                // Make the Retrofit POST request
                Call<Void> call = api.submitResposta(resposta);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            String jsonAvaliacao = gson.toJson(resposta);

                            // Log the JSON string
                            Log.e("POST", jsonAvaliacao);
                            Toast.makeText(TelaComentarioFormulario.this, "Resposta submitted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            String jsonAvaliacao = gson.toJson(resposta);

                            // Log the JSON string
                            Log.e("POST", jsonAvaliacao);
                            // Handle an unsuccessful response
                            Toast.makeText(TelaComentarioFormulario.this, "Failed to submit resposta", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(TelaComentarioFormulario.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
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

}