package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import ifsp.spo.edu.vagainclusivafrontend.R;

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

        local = "R. Cavalheiro Nabuco";
        proximidade = "R. Joaquin Nabuco";
        numeroDeVagas = 2;
        area = "Bras";
        coordenadas = "1234567890123456";

        TextView localTextView = findViewById(R.id.container_local);
        TextView proximidadeTextView = findViewById(R.id.container_proximidade);
        TextView numeroDeVagasTextView = findViewById(R.id.container_numero_vagas);
        TextView areaTextView = findViewById(R.id.container_area);
        TextView coordenadasTextView = findViewById(R.id.container_coordenadas);

        localTextView.setText("Local: " + local);
        proximidadeTextView.setText("Prox: " + proximidade);
        numeroDeVagasTextView.setText("Número de Vagas: " + numeroDeVagas);
        areaTextView.setText("Área: " + area);
        coordenadasTextView.setText("Coordenadas: " + coordenadas);
    }
}