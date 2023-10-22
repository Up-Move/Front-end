package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        fetchUserProfile();
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        // Faz Logout do Sistema
        Button logoutButton = findViewById(R.id.btn_logout);
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
            }
        });
    }

    private void fetchUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("AUTH_KEY", "");

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:8000/users/";

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

                            runOnUiThread(() -> {
                                // Mandar o Nome do Usuário para a TextView
                                updateUIWithUsername(username);
                            });
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

    private void updateUIWithUsername(String username) {
        // Atualize a interface do usuário com o nome de usuário recuperado
        TextView usernameTextView = findViewById(R.id.username);
        usernameTextView.setText(username);
    }

}