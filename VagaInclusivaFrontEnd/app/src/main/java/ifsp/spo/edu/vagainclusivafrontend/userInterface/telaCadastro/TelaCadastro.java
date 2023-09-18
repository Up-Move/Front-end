package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaCadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.api.API;
import ifsp.spo.edu.vagainclusivafrontend.userCadastro.UserCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;

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
import android.widget.Toast;
import android.widget.TextView;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.JsonObject;

@SuppressWarnings({"Convert2Lambda", "SpellCheckingInspection"})
public class TelaCadastro extends AppCompatActivity {

    EditText Nome, Email, Senha, ConfirmarSenha;
    boolean senhaVisivel;

    @SuppressLint({"ClickableViewAccessibility", "SpellCheckingInspection"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Nome = findViewById(R.id.nome);
        Email = findViewById(R.id.email);
        Senha = findViewById(R.id.senha);
        ConfirmarSenha = findViewById(R.id.confirmarsenha);
        Button btnRegister = findViewById(R.id.btn_register);
        TextView ViewLogin = findViewById(R.id.ViewLogin);
        TextView ViewCadastro = findViewById(R.id.ViewCadastro);

        // Sublinhar Cadastro
        ViewCadastro.setPaintFlags(ViewCadastro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Trocar para Tela de Login
        ViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        // Lógica para mudar cone de senha para Visivel/Invisivel
        Senha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= Senha.getRight()-Senha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = Senha.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            Senha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            Senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            Senha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            Senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        Senha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para mudar icone do Confirmarsenha para Visivel/Invisivel
        ConfirmarSenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= ConfirmarSenha.getRight()-ConfirmarSenha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = ConfirmarSenha.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        ConfirmarSenha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para Cadastro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = Nome.getText().toString();
                String email = Email.getText().toString();
                String senha = Senha.getText().toString();
                String confirmarsenha = ConfirmarSenha.getText().toString();

                // Validar email
                if (email.isEmpty()) {
                    Email.setError("O E-Mail não pode estar vazio");
                    Email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email.setError("Digite um email válido");
                    Email.requestFocus();
                    return;
                }

                // Validar nome de usuário
                if (nome.isEmpty()) {
                    Nome.setError("O nome de usuário não pode estar vazio");
                    Nome.requestFocus();
                    return;
                }

                if (nome.contains(" ")) {
                    Nome.setError("O nome de usuário não pode conter espaços vazios, " +
                            "Por favor, use o caractere '_' " +
                            "para separar palavras no nome de usuário");
                    Nome.requestFocus();
                    return;
                }

                // Validar senha
                if (senha.isEmpty()) {
                    Senha.setError("A Senha não pode estar vazia");
                    Senha.requestFocus();
                    return;
                }
                if (senha.length() < 6) {
                    Senha.setError("A senha deve ter no mínimo 6 caracteres");
                    Senha.requestFocus();
                    return;
                }
                if (!senha.matches(".*[A-Z].*")) {
                    Senha.setError("A senha deve ter no mínimo 1 letra maiúscula");
                    Senha.requestFocus();
                    return;
                }

                // Validar Confirmar Senha
                if (confirmarsenha.isEmpty()) {
                    ConfirmarSenha.setError("Confirmar Senha não pode estar vazio");
                    ConfirmarSenha.requestFocus();
                    return;
                }

                if (!confirmarsenha.equals(senha)) {
                    ConfirmarSenha.setError("As senhas não correspondem");
                    ConfirmarSenha.requestFocus();
                    return;
                }

                UserCadastro user = new UserCadastro(nome, email, senha, confirmarsenha);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API API = retrofit.create(API.class);

                Call<JsonObject> call = API.registerUser(user);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject>  call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaCadastro.this, "Cadastro Concluido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
                            startActivity(intent);
                            finish();
                        } else if (response.code() == 400) { // Erro para Email
                            Email.setError("Email ja Cadastrado no Sistema");
                            Email.requestFocus();
                        } else if (response.code() == 500) { // Erro para Nome de Usuario
                            Nome.setError("Nome de Usuário ja Cadastrado no Sistema");
                            Nome.requestFocus();
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaCadastro.this, "Erro, Cadastro não Concluido", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Toast.makeText(TelaCadastro.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
