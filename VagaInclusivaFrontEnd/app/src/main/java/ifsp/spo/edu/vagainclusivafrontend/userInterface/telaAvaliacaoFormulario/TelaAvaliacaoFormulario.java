package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;

public class TelaAvaliacaoFormulario extends AppCompatActivity {
    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];

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