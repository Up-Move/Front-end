package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.TelaAvaliacaoFormulario;

public class TelaVagaIndividual extends AppCompatActivity {

    private String local;
    private String proximidade;
    private int numeroDeVagas;
    private String area;
    private String coordenadas;

    String[] nomes = {"Joana", "João", "Pedro"};
    int[] estrelas = {5, 3, 4};
    String[] descricoes = {"blablabla1", "blablabla2", "blablabla3"};
    int[] quantidadesComentario = {4, 0, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vaga_individual);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //local = "R. Cavalheiro Nabuco";
        //proximidade = "R. Joaquin Nabuco";
        //numeroDeVagas = 2;
        //area = "Bras";
        //coordenadas = "1234567890123456";

        // Recupere os valor da vaga Clicada na Tela Mapa
        int retrievedId = getIntent().getIntExtra("index", 0);
        System.out.println(retrievedId);
        int retrievedQuantidadeV = getIntent().getIntExtra("quantidadeV", 0);
        String retrievedArea = getIntent().getStringExtra("area");
        String retrievedComplemento = getIntent().getStringExtra("complemento");
        String retrievedLocal = getIntent().getStringExtra("local");
        Double retrievedLatitude = getIntent().getDoubleExtra("lat", 0);
        Double retrievedLongitude = getIntent().getDoubleExtra("lng", 0);

        TextView localTextView = findViewById(R.id.tela_avaliacao_container_local);
        TextView proximidadeTextView = findViewById(R.id.tela_avaliacao_container_proximidade);
        TextView numeroDeVagasTextView = findViewById(R.id.tela_avaliacao_container_numero_vagas);
        TextView areaTextView = findViewById(R.id.tela_avaliacao_container_area);
        TextView coordenadasTextView = findViewById(R.id.tela_avaliacao_container_coordenadas);

        localTextView.setText("Local: " + retrievedLocal);
        proximidadeTextView.setText("Complemento: " + retrievedComplemento);
        numeroDeVagasTextView.setText("Número de Vagas: " + retrievedQuantidadeV);
        areaTextView.setText("Área: " + retrievedArea);
        coordenadasTextView.setText("Coordenadas: " + retrievedLatitude + "," + retrievedLongitude);

        Button buttonAvaliar = findViewById(R.id.button_Avaliar_essa_vaga);

        buttonAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificando Controle de Sessão
                if (isAuthenticated()) {
                    // Redirecionar para a tela de avaliação
                    Intent intent = new Intent(TelaVagaIndividual.this, TelaAvaliacaoFormulario.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TelaVagaIndividual.this, "Para avaliar as vagas, é necessário que seja feito o login no aplicativo", Toast.LENGTH_LONG).show();
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
}