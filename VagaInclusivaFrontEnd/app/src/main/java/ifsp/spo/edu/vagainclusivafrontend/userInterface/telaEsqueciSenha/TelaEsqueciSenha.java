package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaEsqueciSenha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.ChangePasswordAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken.TelaToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaEsqueciSenha extends AppCompatActivity {

    EditText SenhaNova, ConfirmarSenhaNova;
    boolean senhaVisivel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tela_esqueci_senha);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Recupera o email da Intent
        String email = getIntent().getStringExtra("email");

        SenhaNova = findViewById(R.id.esqueci_senha_nova);
        ConfirmarSenhaNova = findViewById(R.id.esqueci_senha_nova_confirma);

        // Trocando Senha do Usuario
        Button botaoAtualizarSenha = findViewById(R.id.button_atualizar_senha);

        botaoAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupere o email da Intent
                String email = getIntent().getStringExtra("email");

                // Validar senha
                String senhaNovaTexto = SenhaNova.getText().toString();
                if (senhaNovaTexto.isEmpty()) {
                    SenhaNova.setError("A Senha não pode estar vazia");
                    SenhaNova.requestFocus();
                    return;
                }
                if (senhaNovaTexto.length() < 6) {
                    SenhaNova.setError("A senha deve ter no mínimo 6 caracteres");
                    SenhaNova.requestFocus();
                    return;
                }
                if (!senhaNovaTexto.matches(".*[A-Z].*")) {
                    SenhaNova.setError("A senha deve ter no mínimo 1 letra maiúscula");
                    SenhaNova.requestFocus();
                    return;
                }

                // Validar Confirmar Senha
                String confirmarSenhaTexto = ConfirmarSenhaNova.getText().toString();
                if (confirmarSenhaTexto.isEmpty()) {
                    ConfirmarSenhaNova.setError("Confirmar Senha não pode estar vazio");
                    ConfirmarSenhaNova.requestFocus();
                    return;
                }

                if (!confirmarSenhaTexto.equals(senhaNovaTexto)) {
                    ConfirmarSenhaNova.setError("As senhas não correspondem");
                    ConfirmarSenhaNova.requestFocus();
                    return;
                }

                // Obtenha a nova senha
                EditText esqueciSenhaNova = findViewById(R.id.esqueci_senha_nova);
                String novaSenha = esqueciSenhaNova.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Faça uma solicitação POST para atualizar a senha
                ChangePasswordAPI changePasswordAPI = retrofit.create(ChangePasswordAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", email);
                requestBody.put("nova_senha", novaSenha);

                Call<Void> call = changePasswordAPI.atualizarSenha(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaEsqueciSenha.this, "Senha atualizada com Sucesso", Toast.LENGTH_SHORT).show();

                            // Redirecione para a tela desejada
                            Intent intent = new Intent(TelaEsqueciSenha.this, TelaLogin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TelaEsqueciSenha.this, "Erro ao atualizar a Senha", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaEsqueciSenha.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Lógica para mudar icone de senha para Visivel/Invisivel
        SenhaNova.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= SenhaNova.getRight() - SenhaNova.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = SenhaNova.getSelectionEnd();
                        if (senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            SenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            SenhaNova.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            SenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            SenhaNova.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        SenhaNova.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para mudar icone do Confirmarsenha para Visivel/Invisivel
        ConfirmarSenhaNova.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= ConfirmarSenhaNova.getRight() - ConfirmarSenhaNova.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = ConfirmarSenhaNova.getSelectionEnd();
                        if (senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenhaNova.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenhaNova.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        ConfirmarSenhaNova.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}