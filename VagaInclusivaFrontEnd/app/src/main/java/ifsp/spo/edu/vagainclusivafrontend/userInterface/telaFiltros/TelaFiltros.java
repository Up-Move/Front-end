package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings({"Convert2Lambda", "CanBeFinal"})
public class TelaFiltros extends AppCompatActivity {

    private int currentRating = 0;
    private int maxRating = 5;
    private ImageView[] estrelas = new ImageView[maxRating];
    private ImageView[] checkboxes = new ImageView[9];
    private int[] checkboxStatus = new int[9];

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_filtros);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Troca de Telas Footer (Inicio, Perfil, Info, Filtros)
        imagemInicioBlack = findViewById(R.id.imagemInicioBlack);
        imagemPersonBlack = findViewById(R.id.imagemPersonBlack);
        imagemInfoBlack = findViewById(R.id.imagemInfoBlack);
        imagemFiltroBlack = findViewById(R.id.imagemFiltroBlack);

        imagemInicioBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFiltros.this, TelaMapaAutenticado.class);
                startActivity(intent);
            }
        });

        imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFiltros.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFiltros.this, TelaInfos.class);
                startActivity(intent);
            }
        });

        //imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(TelaFiltros.this, TelaFiltros.class);
        //        startActivity(intent);
        //    }
        //});

        // Filtros
        /* INSTANCIA UMA CLASSE ACESSIBILIDADE */
        Acessibilidade[] acessibilidades = { Acessibilidade.AC1, Acessibilidade.AC5, Acessibilidade.AC7,};

        /* INSTANCIA UMA CLASSE FILTRO */
        Filtro filtro = new Filtro(acessibilidades, 2);

        boolean filtroExist = true;

        if (filtroExist == true) {
            TextView filtroContainer = findViewById(R.id.tela_filtro_container);
            filtroContainer.setVisibility(View.INVISIBLE);
        }

        LinearLayout linearLayoutFiltro = findViewById(R.id.tela_filtro_container_filtro_salvo);

        /* DESENHANDO ACESSIBILIDADE */
        for (Acessibilidade acessibilidade : acessibilidades) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 8);
            textView.setLayoutParams(params);
            textView.setText(acessibilidade.getDescricao(this));
            textView.setFontFeatureSettings(String.valueOf(R.font.jura));
            textView.setTextColor(getResources().getColor(R.color.primary_text));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            linearLayoutFiltro.addView(textView);
        }

        LinearLayout estrelasLayout = findViewById(R.id.tela_filtro_container_filtro_estrela);

        /* SE NÃO TIVER AVALIACAO NO FILTRO SALVO, VAI TIRAR O LAYOUT DAS ESTRELAS */
        if (filtro.getAvaliacao() == 0) {
            estrelasLayout.setVisibility(View.GONE);
        }
        int vetorEstrelas = R.drawable.icon_star_gold;
        for (int i = 1; i <= 5; i++) {
            ImageView estrelaImageView = estrelasLayout.findViewById(getResources().getIdentifier("tela_filtro_container_filtro_estrela_" + i, "id", getPackageName()));
            if (i <= filtro.getAvaliacao()) {
                estrelaImageView.setImageResource(vetorEstrelas);
            } else {
                estrelaImageView.setImageResource(R.drawable.icon_star_filled);
            }
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