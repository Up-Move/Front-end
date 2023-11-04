package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarDadosPerfil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.ChangeEmailAPI;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.ChangePasswordAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"ConstantConditions", "Convert2Lambda", "RedundantIfStatement"})
public class TelaAlterarDadosPerfil extends AppCompatActivity {

    EditText perfilsenhaatual, perfilnovasenha, EmailAtual, EmailNovo, SenhaAtual, SenhaNova;
    boolean senhaVisivel;
    private String emailCadastradoDoUsuario, senhaCadastradaDoUsuario;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
        setContentView(R.layout.tela_alterar_dados_perfil);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Chamando Email e Senha Cadastrados do Usuario
        fetchUserProfile();

        perfilsenhaatual = findViewById(R.id.alterar_dados_perfil_senha_atual);
        perfilnovasenha = findViewById(R.id.alterar_dados_perfil_senha_novo);
        Button buttonAtualizarEmail = findViewById(R.id.button_atualizar_email);
        Button buttonAtualizarSenha = findViewById(R.id.button_atualizar_senha);

        EmailAtual = findViewById(R.id.alterar_dados_perfil_email_atual);
        EmailNovo = findViewById(R.id.alterar_dados_perfil_email_novo);
        SenhaAtual = findViewById(R.id.alterar_dados_perfil_senha_atual);
        SenhaNova = findViewById(R.id.alterar_dados_perfil_senha_novo);

        // Lógica para Alteração de Email
        buttonAtualizarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar Email Novo e Atual
                String emailAtual = EmailAtual.getText().toString();
                String emailNovo = EmailNovo.getText().toString();

                if (emailAtual.isEmpty()) {
                    EmailAtual.setError("O E-Mail não pode estar vazio");
                    EmailAtual.requestFocus();
                    return;
                }

                if (emailNovo.isEmpty()) {
                    EmailNovo.setError("O E-Mail não pode estar vazio");
                    EmailNovo.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailAtual).matches()) {
                    EmailAtual.setError("Digite um email válido");
                    EmailAtual.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailNovo).matches()) {
                    EmailNovo.setError("Digite um email válido");
                    EmailNovo.requestFocus();
                    return;
                }

                // Compara o email novo com o email atual
                if (emailAtual != null && emailAtual.equals(emailNovo)) {
                    EmailNovo.setError("O novo email não pode ser igual ao email atual");
                    EmailNovo.requestFocus();
                    return;
                }

                // Compara o email atual com o email obtido no método fetchUserProfile(), que é o Email Cadastrado do Usuario
                if (!emailAtual.equals(emailCadastradoDoUsuario)) {
                    // Os emails são iguais, exiba uma mensagem de erro
                    EmailAtual.setError("Email atual incorreto");
                    EmailAtual.requestFocus();
                    return;
                }

                // Obtenha o novo email
                EditText esqueciEmailNovo = findViewById(R.id.alterar_dados_perfil_email_novo);
                String novoEmail = esqueciEmailNovo.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://23.23.1.10/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Faça uma solicitação POST para atualizar a senha
                ChangeEmailAPI changeEmailAPI = retrofit.create(ChangeEmailAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email_cadastrado", emailCadastradoDoUsuario);
                requestBody.put("email_novo", novoEmail);

                Call<Void> call = changeEmailAPI.atualizarEmail(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaAlterarDadosPerfil.this, "Email atualizado com Sucesso", Toast.LENGTH_SHORT).show();

                            // Redirecione para a tela desejada
                            Intent intent = new Intent(TelaAlterarDadosPerfil.this, TelaPerfil.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TelaAlterarDadosPerfil.this, "Erro ao atualizar o Email", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaAlterarDadosPerfil.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Lógica para Alteração de Senha
        buttonAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar Senha Nova e Atual
                String senhaAtual = SenhaAtual.getText().toString();
                String senhaNova = SenhaNova.getText().toString();

                if (senhaAtual.isEmpty()) {
                    SenhaAtual.setError("A Senha não pode estar vazia");
                    SenhaAtual.requestFocus();
                    return;
                }

                if (senhaNova.isEmpty()) {
                    SenhaNova.setError("A Senha não pode estar vazia");
                    SenhaNova.requestFocus();
                    return;
                }

                if (senhaAtual.length() < 6) {
                    SenhaAtual.setError("A senha deve ter no mínimo 6 caracteres");
                    SenhaAtual.requestFocus();
                    return;
                }

                if (senhaNova.length() < 6) {
                    SenhaNova.setError("A senha deve ter no mínimo 6 caracteres");
                    SenhaNova.requestFocus();
                    return;
                }

                if (!senhaAtual.matches(".*[A-Z].*")) {
                    SenhaAtual.setError("A senha deve ter no mínimo 1 letra maiúscula");
                    SenhaAtual.requestFocus();
                    return;
                }

                if (!senhaNova.matches(".*[A-Z].*")) {
                    SenhaNova.setError("A senha deve ter no mínimo 1 letra maiúscula");
                    SenhaNova.requestFocus();
                    return;
                }

                // Compara o senha nova com o senha atual
                if (senhaAtual != null && senhaAtual.equals(senhaNova)) {
                    SenhaNova.setError("A nova senha não pode ser igual a senha atual");
                    SenhaNova.requestFocus();
                    return;
                }

                // Obtenha a nova senha
                EditText esqueciSenhaNova = findViewById(R.id.alterar_dados_perfil_senha_novo);
                String novaSenha = esqueciSenhaNova.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://23.23.1.10/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Faça uma solicitação POST para atualizar a senha
                ChangePasswordAPI changePasswordAPI = retrofit.create(ChangePasswordAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", emailCadastradoDoUsuario);
                requestBody.put("nova_senha", novaSenha);

                Call<Void> call = changePasswordAPI.atualizarSenha(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaAlterarDadosPerfil.this, "Senha atualizada com Sucesso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TelaAlterarDadosPerfil.this, TelaPerfil.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TelaAlterarDadosPerfil.this, "Erro ao atualizar a Senha", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaAlterarDadosPerfil.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // Lógica para mudar icone de senha atual para Visivel/Invisivel
        perfilsenhaatual.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= perfilsenhaatual.getRight() - perfilsenhaatual.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = perfilsenhaatual.getSelectionEnd();
                        if (senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilsenhaatual.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilsenhaatual.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilsenhaatual.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilsenhaatual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        perfilsenhaatual.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para mudar icone de senha nova para Visivel/Invisivel
        perfilnovasenha.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= perfilnovasenha.getRight() - perfilnovasenha.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = perfilnovasenha.getSelectionEnd();
                        if (senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilnovasenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilnovasenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilnovasenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilnovasenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        perfilnovasenha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    } else {
        redirect();
        }
    }

    // Recuperando Email do Usuário para Validação
    private void fetchUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("AUTH_KEY", "");

        OkHttpClient client = new OkHttpClient();
        // Android Studio
        //String url = "http://10.0.2.2:8000/users/";
        // Celular Fisico
        String url = "http://23.23.1.10/users/";

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
                            emailCadastradoDoUsuario = userJson.getString("email");
                            senhaCadastradaDoUsuario = userJson.getString("password");
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