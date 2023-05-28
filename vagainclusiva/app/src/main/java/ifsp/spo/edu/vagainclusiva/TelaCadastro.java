package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
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

public class TelaCadastro extends AppCompatActivity {

    EditText Nome, Email, Senha, ConfirmarSenha;
    boolean senhaVisivel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        getSupportActionBar().hide();

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

        // Lógica para mudar icone de senha para Visivel/Invisivel
        Senha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= Senha.getRight()-Senha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = Senha.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            Senha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock , 0, R.drawable.eyeclosed, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            Senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            Senha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye, 0);

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
                            ConfirmarSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock , 0, R.drawable.eyeclosed, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye, 0);

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

                // Validar nome
                if (nome.isEmpty()) {
                    Nome.setError("Digite um nome");
                    Nome.requestFocus();
                    return;
                }
                if (!nome.matches("^[\\p{L}]+( [\\p{L}]+)*$")) {
                    Nome.setError("O nome não pode conter números");
                    Nome.requestFocus();
                    return;
                }

                // Validar email
                if (email.isEmpty()) {
                    Email.setError("Digite um email");
                    Email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email.setError("Digite um email válido");
                    Email.requestFocus();
                    return;
                }

                // Validar senha
                if (senha.isEmpty()) {
                    Senha.setError("Digite uma senha");
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

                // Validar Confirmar senha
                if (!confirmarsenha.equals(senha)) {
                    ConfirmarSenha.setError("As senhas não correspondem");
                    ConfirmarSenha.requestFocus();
                    return;
                }

                User user = new User(nome, email, senha, confirmarsenha);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://18.205.155.235:8000/")
                        //.baseUrl("http://vagainclusivaenv.eba-mgb62xtj.us-east-1.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API API = retrofit.create(API.class);

                Call<JsonObject> call = API.registerUser(user);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject>  call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaCadastro.this, "Cadastro Concluido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TelaCadastro.this, "Erro, Cadastro não Concluido", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(TelaCadastro.this, "Erro, Verifique sua conexão com a Internet: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
