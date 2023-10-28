package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import ifsp.spo.edu.vagainclusivafrontend.R;
public class TelaFiltros extends AppCompatActivity {

    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_filtros);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        estrelas[0] = findViewById(R.id.filtro_estrela_avaliacao1);
        estrelas[1] = findViewById(R.id.filtro_estrela_avaliacao2);
        estrelas[2] = findViewById(R.id.filtro_estrela_avaliacao3);
        estrelas[3] = findViewById(R.id.filtro_estrela_avaliacao4);
        estrelas[4] = findViewById(R.id.filtro_estrela_avaliacao5);

        checkboxes[0] = findViewById(R.id.filtro_checkbox_acessibilidade1);
        checkboxes[1] = findViewById(R.id.filtro_checkbox_acessibilidade2);
        checkboxes[2] = findViewById(R.id.filtro_checkbox_acessibilidade3);
        checkboxes[3] = findViewById(R.id.filtro_checkbox_acessibilidade4);
        checkboxes[4] = findViewById(R.id.filtro_checkbox_acessibilidade5);
        checkboxes[5] = findViewById(R.id.filtro_checkbox_acessibilidade6);
        checkboxes[6] = findViewById(R.id.filtro_checkbox_acessibilidade7);
        checkboxes[7] = findViewById(R.id.filtro_checkbox_acessibilidade8);
        checkboxes[8] = findViewById(R.id.filtro_checkbox_acessibilidade9);

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
}