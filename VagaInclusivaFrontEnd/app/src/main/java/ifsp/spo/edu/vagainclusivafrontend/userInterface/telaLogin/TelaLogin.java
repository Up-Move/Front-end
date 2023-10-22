package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;

import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaCadastro.TelaCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaUsuario.TelaUsuario;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"Convert2Lambda", "SpellCheckingInspection"})
public class TelaLogin extends AppCompatActivity {
    EditText emaillogin, senhalogin;
    Button botaologin;
    boolean senhaVisivel;
    SharedPreferences sharedPreferences;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        emaillogin = findViewById(R.id.emaillogin);
        senhalogin = findViewById(R.id.senhalogin);
        botaologin = findViewById(R.id.botaologin);
        TextView ViewLogin = findViewById(R.id.viewLogin);
        TextView ViewCadastro = findViewById(R.id.viewCadastro);


        // Sublinhar Login
        ViewLogin.setPaintFlags(ViewLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Trocar para Tela de Cadastro
        ViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        // Lógica para mudar icone de senha para Visivel/Invisivel
        senhalogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= senhalogin.getRight()-senhalogin.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = senhalogin.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            senhalogin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            senhalogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            senhalogin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            senhalogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        senhalogin.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // SharedPreferences para Armazenar Token
        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        // Lógica para Login
        botaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillogin.getText().toString();
                String senha = senhalogin.getText().toString();

                // Validar Email
                if (email.isEmpty()) {
                    emaillogin.setError("O E-Mail não Pode Estar Vazio");
                    emaillogin.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emaillogin.setError("Digite um Formato de Email Válido");
                    emaillogin.requestFocus();
                    return;
                }

                // Validar Senha
                if (senha.isEmpty()) {
                    senhalogin.setError("A Senha não Pode Estar Vazia");
                    senhalogin.requestFocus();
                    return;
                }

                UserLogin userLogin = new UserLogin(email, senha);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API userService = retrofit.create(API.class);

                Call<JsonObject> call = userService.login(userLogin);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            // Armazene o token nas SharedPreferences
                            String authKey = response.body().get("key").getAsString();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("AUTH_KEY", authKey);
                            editor.apply();

                            Toast.makeText(TelaLogin.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                            // Verifique e inicie a próxima tela
                            checkAndStartNextScreen();
                        } else if (response.code() == 400) { // Erro para Senha
                            senhalogin.setError("Senha Incorreta");
                            senhalogin.requestFocus();
                        } else if (response.code() == 500) { // Erro para Email
                            emaillogin.setError("Entre com um Email Cadastrado ou Cadastre-se");
                            emaillogin.requestFocus();
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaLogin.this, "Erro, Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaLogin.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Checar Token
    private void checkAndStartNextScreen() {
        String authToken = sharedPreferences.getString("AUTH_KEY", "");
        if (!authToken.isEmpty()) {
            // Se o token for válido, inicie a próxima tela
            Intent intent = new Intent(TelaLogin.this, TelaUsuario.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(TelaLogin.this, "Você não está autenticado.", Toast.LENGTH_SHORT).show();
        }
    }
}
