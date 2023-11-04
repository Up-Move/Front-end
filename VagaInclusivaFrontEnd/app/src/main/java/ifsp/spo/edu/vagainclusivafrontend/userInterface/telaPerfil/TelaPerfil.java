package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.DeleteAccountAPI;
import ifsp.spo.edu.vagainclusivafrontend.userEmail.TokenValidationAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarDadosPerfil.TelaAlterarDadosPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaEsqueciSenha.TelaEsqueciSenha;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken.TelaToken;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("Convert2Lambda")
public class TelaPerfil extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_LANGUAGE = "language";
    private static final String PREF_THEME = "theme";

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    String globalMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean(PREF_THEME, false);
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        String savedLanguage = preferences.getString(PREF_LANGUAGE, "pt");
        setLocale(savedLanguage);

        setContentView(R.layout.tela_perfil);
        fetchUserProfile();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Troca para Tela de Alteração de Email/Senha
        ImageView btnTelaPerfilSetaDireita = findViewById(R.id.tela_perfil_seta_direita);

        btnTelaPerfilSetaDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para navegar para a outra tela
                Intent intent = new Intent(TelaPerfil.this, TelaAlterarDadosPerfil.class);
                startActivity(intent);
            }
        });

        // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
        imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
        imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
        imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
        imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

        imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPerfil.this, TelaMapaAutenticado.class);
                startActivity(intent);
            }
        });

        //imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(TelaPerfil.this, TelaPerfil.class);
        //        startActivity(intent);
        //    }
        //});

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPerfil.this, TelaInfos.class);
                startActivity(intent);
            }
        });

        imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPerfil.this, TelaFiltros.class);
                startActivity(intent);
            }
        });

        // Faz Logout do Sistema
        Button logoutButton = findViewById(R.id.tela_perfil_button_logout);
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpe o token de autenticação nas SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("AUTH_KEY");
                editor.apply();

                // Redirecione o usuário para a tela de login
                Intent intent = new Intent(TelaPerfil.this, TelaLogin.class);
                startActivity(intent);
                finish(); // Isso encerra a atividade atual (TelaPerfil)

                // Exiba uma mensagem de sucesso
                Toast.makeText(TelaPerfil.this, "Usuário Deslogado com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        // Troca de Idioma
        ImageView brasilImageView = findViewById(R.id.tela_perfil_brasil);
        ImageView graBretanhaImageView = findViewById(R.id.tela_perfil_grabretanha);
        SwitchMaterial switchBtn = findViewById(R.id.tela_perfil_switch);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(PREF_THEME, isChecked);
                editor.apply();

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        brasilImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("pt");
                recreate();
            }
        });

        graBretanhaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                recreate();
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        showDeleteAccountConfirmationDialog();
    }

    private void showDeleteAccountConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmação de exclusão de conta");
        builder.setMessage("Tem certeza de que deseja excluir sua conta? Esta ação é irreversível.");

        // Caso o Usuario queira deletar a Conta
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Retrofit retrofit = new Retrofit.Builder()
                        // Android Studio
                        //.baseUrl("http://10.0.2.2:8000/")
                        // Celular Fisico
                        .baseUrl("http://23.23.1.10/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                DeleteAccountAPI deleteAccountAPI = retrofit.create(DeleteAccountAPI.class);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email_cadastrado", globalMail);

                retrofit2.Call<Void> call = deleteAccountAPI.deletarConta(requestBody);
                call.enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaPerfil.this, "Conta Deletada com Sucesso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TelaPerfil.this, TelaLogin.class);
                            startActivity(intent);
                        } else {
                            // Erro Generico para caso ocorra outro tipo de Erro
                            Toast.makeText(TelaPerfil.this, "Erro, Conta Não Deletada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        // Erro para Problemas no Servidor
                        Toast.makeText(TelaPerfil.this, "Desculpe o Transtorno, " +
                                "\nErro de Conexão com o Servidor, " +
                                "\nTente Novamente mais Tarde", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // Caso o Usuario não queira deletar a Conta
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sem Mensagem, Apenas fecha a caixa de Dialogo
            }
        });

        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();

        // Defina a cor do botão "Sim" e "Não"
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.primary));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.primary));
            }
        });

        alertDialog.show();
    }

    @SuppressLint("AppBundleLocaleChanges")
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LANGUAGE, lang);
        editor.apply();
    }

    // Recuperando Dados para Disponibilizar na Tela (Nome, Email e Data de Criação da Conta)
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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseBody);
                        if (jsonArray.length() > 0) {
                            JSONObject userJson = jsonArray.getJSONObject(0); // Pega o primeiro objeto do array
                            String username = userJson.getString("username");
                            String email = userJson.getString("email");
                            String criacaoConta = userJson.getString("date_joined");

                            // Deixando o Email como variavel Global
                            globalMail = email;

                            //Convertendo data de TimeStamp para Hora:Minuto:Segundo Dia-Mes-Ano
                            // Formato da data original
                            SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);

                            // Formato desejado
                            SimpleDateFormat formatoDesejado = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.US);

                            Date data = formatoOriginal.parse(criacaoConta);
                            String dataFormatada = formatoDesejado.format(data);

                            // Adicione strings personalizadas
                            String dataHoraFormatada = "Horario: " + dataFormatada.substring(0, 8) + " Data: " + dataFormatada.substring(9);

                            final String dataFormatadaFinal = dataHoraFormatada;

                            runOnUiThread(() -> {
                                // Mandar o Nome do Usuário, Email e Data de Criação de Conta para a TextView
                                updateUI(username, email, dataFormatadaFinal);
                            });
                        }
                    } catch (ParseException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Usuário Não Autenticado");
                }
            }
        });
    }

    // Atualiza a Tela de Perfil com as Informações do Usuario (Nome, Email e Data de Criação da Conta)
    private void updateUI(String username, String email, String dataFormatadaFinal) {
        // Atualize a interface do usuário com o nome de usuário recuperado
        TextView usernameTextView = findViewById(R.id.tela_perfil_username);
        TextView emailTextView = findViewById(R.id.tela_perfil_email);
        TextView criacaoContaTextView = findViewById(R.id.tela_perfil_create);
        usernameTextView.setText(username);
        emailTextView.setText(email);
        criacaoContaTextView.setText(dataFormatadaFinal);
    }
}
