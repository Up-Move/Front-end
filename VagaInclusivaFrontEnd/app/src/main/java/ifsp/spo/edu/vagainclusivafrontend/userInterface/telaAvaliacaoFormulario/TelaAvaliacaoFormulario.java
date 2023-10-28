package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaAvaliacaoFormulario extends AppCompatActivity {
    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];
    Button botaoAvaliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
            setContentView(R.layout.tela_avaliacao_formulario);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            estrelas[0] = findViewById(R.id.estrela_avaliacao1);
            estrelas[1] = findViewById(R.id.estrela_avaliacao2);
            estrelas[2] = findViewById(R.id.estrela_avaliacao3);
            estrelas[3] = findViewById(R.id.estrela_avaliacao4);
            estrelas[4] = findViewById(R.id.estrela_avaliacao5);

            checkboxes[0] = findViewById(R.id.checkbox_acessibilidade1);
            checkboxes[1] = findViewById(R.id.checkbox_acessibilidade2);
            checkboxes[2] = findViewById(R.id.checkbox_acessibilidade3);
            checkboxes[3] = findViewById(R.id.checkbox_acessibilidade4);
            checkboxes[4] = findViewById(R.id.checkbox_acessibilidade5);
            checkboxes[5] = findViewById(R.id.checkbox_acessibilidade6);
            checkboxes[6] = findViewById(R.id.checkbox_acessibilidade7);
            checkboxes[7] = findViewById(R.id.checkbox_acessibilidade8);
            checkboxes[8] = findViewById(R.id.checkbox_acessibilidade9);

            for (int i = 0; i < maxRating; i++) {
                final int finalI = i;
                estrelas[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentRating = finalI + 1;
                        updateRatingImages();
                    }
                });
            }

            for (int i = 0; i < checkboxes.length; i++) {
                final int index = i;
                checkboxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleCheckbox(index);
                    }
                });
            }

            /*
            // Lógica para Avaliar
            botaoAvaliar = findViewById(R.id.button_enviar_avaliacao);

            botaoAvaliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notaText = editTextNota.getText().toString();
                    int nota;
                    try {
                        nota = Integer.parseInt(notaText);
                    } catch (NumberFormatException e) {
                        Toast.makeText(TelaAvaliacao.this, "Please input a valid value for nota", Toast.LENGTH_SHORT).show();
                        return; // Exit the onClick method if nota is not a valid integer
                    }

                    if (nota < 1 || nota > 5) {
                        Toast.makeText(TelaAvaliacao.this, "Nota must be between 1 and 5", Toast.LENGTH_SHORT).show();
                        return; // Exit the onClick method if nota is not in the valid range
                    }

                    if (acess.isEmpty()) {
                        Toast.makeText(TelaAvaliacao.this, "Select at least one radio button", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Avaliacao avaliacao = new Avaliacao(nota, acess, vagaId);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    API api = retrofit.create(API.class);

                    // Make the Retrofit POST request
                    Call<Void> call = api.submitAvaliacao(avaliacao);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                String jsonAvaliacao = gson.toJson(avaliacao);

                                // Log the JSON string
                                Log.e("POST", jsonAvaliacao);
                                Toast.makeText(TelaAvaliacao.this, "Avaliacao submitted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                String jsonAvaliacao = gson.toJson(avaliacao);

                                // Log the JSON string
                                Log.e("POST", jsonAvaliacao);
                                // Handle an unsuccessful response
                                Toast.makeText(TelaAvaliacao.this, "Failed to submit avaliacao", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(TelaAvaliacao.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }); */
        } else {
            redirect();
        }
    }

    private void updateRatingImages() {
        for (int i = 0; i < maxRating; i++) {
            if (i < currentRating) {
                estrelas[i].setImageResource(R.drawable.icon_star_gold);
            } else {
                estrelas[i].setImageResource(R.drawable.icon_star);
            }
        }
    }

    private void toggleCheckbox(int index) {
        if (checkboxStatus[index] == 0) {
            checkboxes[index].setImageResource(R.drawable.icon_checkbox);
            checkboxStatus[index] = 1;
        } else {
            checkboxes[index].setImageResource(R.drawable.icon_check2square);
            checkboxStatus[index] = 0;
        }
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