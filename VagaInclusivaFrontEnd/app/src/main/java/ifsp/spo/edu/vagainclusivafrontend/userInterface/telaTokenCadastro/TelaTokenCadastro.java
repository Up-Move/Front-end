package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaTokenCadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userCadastro.UserCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.TokenValidationAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"NullableProblems", "Convert2Lambda"})
public class TelaTokenCadastro extends AppCompatActivity {

    TextView desc;
    TextView title;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tela_token_cadastro);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        desc = findViewById(R.id.token_descricao);
        linearLayout = findViewById(R.id.token_layout);
        title = findViewById(R.id.token_title);

        // Recebendo os Valores do Cadastro
        String email = getIntent().getStringExtra("email");
        String nome = getIntent().getStringExtra("nome");
        String senha = getIntent().getStringExtra("senha");
        String confirmarsenha = getIntent().getStringExtra("confirmarsenha");

        // Validação de Token
        Button botaotoken = findViewById(R.id.botaotoken);

        botaotoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText validartoken = findViewById(R.id.digite_token);
                String token = validartoken.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://23.23.1.10/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Criação de Conta do Usuario
                UserCadastro user = new UserCadastro(nome, email, senha, confirmarsenha);
                API API = retrofit.create(API.class);
                Call<JsonObject> call = API.registerUser(user);

                // Validação do Token
                TokenValidationAPI tokenValidationAPI = retrofit.create(TokenValidationAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("token", token);

                Call<Void> callvoid = tokenValidationAPI.enviartoken(requestBody);
                callvoid.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> callvoid, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaTokenCadastro.this, "Token Validado com Sucesso, Cadastro Concluido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TelaTokenCadastro.this, TelaLogin.class);

                            // Lógica para Fazer o Cadastro após Token ser Validado
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(@NonNull Call<JsonObject>  call, @NonNull Response<JsonObject> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("Validação do Cadastro", "Cadastro Concluido com Sucesso");
                                    } else {
                                        // Erro Generico para caso ocorra outro tipo de Erro
                                        Toast.makeText(TelaTokenCadastro.this, "Erro, Cadastro não Concluido", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                                    Toast.makeText(TelaTokenCadastro.this, "Desculpe o Transtorno, " +
                                            "\nErro de Conexão com o Servidor, " +
                                            "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(intent);
                            finish();
                        } else if (response.code() == 400) {
                            validartoken.setError("Token Invalido");
                            validartoken.requestFocus();
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaTokenCadastro.this, "Erro, Token Não Validado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaTokenCadastro.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void expand(View view) {
        int v = (desc.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        if (desc.getVisibility() == View.GONE) {
            title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowupcirclefill, 0);
        } else {
            title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowdowncirclefill, 0);
        }

        TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
        desc.setVisibility(v);
    }
}