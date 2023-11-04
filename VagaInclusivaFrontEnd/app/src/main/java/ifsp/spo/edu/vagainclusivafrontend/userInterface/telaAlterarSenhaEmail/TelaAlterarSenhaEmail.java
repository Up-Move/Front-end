package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarSenhaEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.userEmail.EmailAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken.TelaToken;

@SuppressWarnings({"NullableProblems", "Convert2Lambda"})
public class TelaAlterarSenhaEmail extends AppCompatActivity {

    TextView desc;
    TextView title;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.tela_alterar_senha_email);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            desc = findViewById(R.id.email_alterar_senha_descricao);
            linearLayout = findViewById(R.id.email_layout);
            title = findViewById(R.id.email_alterar_senha_title);

            // Envio de Token pode Email
        Button botaoemail = findViewById(R.id.botaoemail);

        botaoemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText alterarSenhaEmail = findViewById(R.id.alterar_senha_email);
                String email = alterarSenhaEmail.getText().toString();

                // Validar Campo de Email
                if (email.isEmpty()) {
                    alterarSenhaEmail.setError("O E-Mail não pode estar vazio");
                    alterarSenhaEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    alterarSenhaEmail.setError("Digite um email válido");
                    alterarSenhaEmail.requestFocus();
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://23.23.1.10/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EmailAPI emailAPI = retrofit.create(EmailAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", email);

                Call<Void> call = emailAPI.enviarEmail(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaAlterarSenhaEmail.this, "Token enviado com Sucesso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TelaAlterarSenhaEmail.this, TelaToken.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaAlterarSenhaEmail.this, "Erro, Token Não Enviado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaAlterarSenhaEmail.this, "Desculpe o Transtorno, " +
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