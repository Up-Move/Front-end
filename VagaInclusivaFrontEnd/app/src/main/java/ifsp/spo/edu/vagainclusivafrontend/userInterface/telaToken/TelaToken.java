package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.TokenValidationAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaEsqueciSenha.TelaEsqueciSenha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaToken extends AppCompatActivity {
    TextView desc;
    TextView title;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tela_token);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        desc = findViewById(R.id.token_descricao);
        linearLayout = findViewById(R.id.token_layout);
        title = findViewById(R.id.token_title);

        // Recupera o email da Intent
        String email = getIntent().getStringExtra("email");

        // Validação de Token
        Button botaotoken = findViewById(R.id.botaotoken);

        botaotoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText validartoken = findViewById(R.id.digite_token);
                String token = validartoken.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TokenValidationAPI tokenValidationAPI = retrofit.create(TokenValidationAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("token", token);

                Call<Void> call = tokenValidationAPI.enviartoken(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaToken.this, "Token Validado com Sucesso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TelaToken.this, TelaEsqueciSenha.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else if (response.code() == 400) {
                            validartoken.setError("Token Invalido");
                            validartoken.requestFocus();
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaToken.this, "Erro, Token Não Validado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaToken.this, "Desculpe o Transtorno, " +
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