package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

@SuppressWarnings({"Convert2Lambda", "CanBeFinal", "RedundantIfStatement"})
public class TelaInfos extends AppCompatActivity {
    TextView[] descArray = new TextView[6];
    TextView[] titleArray = new TextView[6];
    LinearLayout[] linearLayoutArray = new LinearLayout[6];
    int currentlyExpandedIndex = -1;

    //Variaveis para Inicio, Perfil, Info, Filtros
    ImageView imagemInicioBlack;
    ImageView imagemPersonBlack;
    ImageView imagemInfoBlack;
    ImageView imagemFiltroBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_infos);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
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
                    Intent intent = new Intent(TelaInfos.this, TelaMapaAutenticado.class);
                    startActivity(intent);
                }
            });

            imagemPersonBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaInfos.this, TelaPerfil.class);
                    startActivity(intent);
                }
            });

            //imagemInfoBlack.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
            //        Intent intent = new Intent(TelaInfos.this, TelaInfos.class);
            //        startActivity(intent);
            //    }
            //});

            imagemFiltroBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TelaInfos.this, TelaFiltros.class);
                    startActivity(intent);
                }
            });


            // Mover FAQs
            for (int i = 0; i < 6; i++) {
                @SuppressLint("DiscouragedApi") int descId = getResources().getIdentifier("faq_desc" + (i + 1), "id", getPackageName());
                @SuppressLint("DiscouragedApi") int titleId = getResources().getIdentifier("faq_title" + (i + 1), "id", getPackageName());
                @SuppressLint("DiscouragedApi") int linearLayoutId = getResources().getIdentifier("faq_layout" + (i + 1), "id", getPackageName());

                descArray[i] = findViewById(descId);
                titleArray[i] = findViewById(titleId);
                linearLayoutArray[i] = findViewById(linearLayoutId);

                final int finalIndex = i;
                linearLayoutArray[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expand(finalIndex);
                    }
                });
            }
        } else {
            redirect();
        }
    }

    public void expand(int index) {
        if (currentlyExpandedIndex != -1) {
            descArray[currentlyExpandedIndex].setVisibility(View.GONE);
            titleArray[currentlyExpandedIndex].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowdowncirclefill, 0);
        }

        if (index != currentlyExpandedIndex) {
            descArray[index].setVisibility(View.VISIBLE);
            titleArray[index].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowupcirclefill, 0);
            currentlyExpandedIndex = index;
        } else {
            currentlyExpandedIndex = -1;
        }
    }

    // Função para verificar se a pessoa esta autenticada
    private boolean isAuthenticated() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("AUTH_KEY", "");
        // Verifique se o token está presente e é válido
        //noinspection ConstantConditions
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