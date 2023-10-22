package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaCadastro.TelaCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;

public class TelaInfos extends AppCompatActivity {
    TextView[] descArray = new TextView[6];
    TextView[] titleArray = new TextView[6];
    LinearLayout[] linearLayoutArray = new LinearLayout[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificando Controle de Sessão
        if (isAuthenticated()) {
        setContentView(R.layout.tela_infos);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        for (int i = 0; i < 6; i++) {
            int descId = getResources().getIdentifier("faq_desc" + (i + 1), "id", getPackageName());
            int titleId = getResources().getIdentifier("faq_title" + (i + 1), "id", getPackageName());
            int linearLayoutId = getResources().getIdentifier("faq_layout" + (i + 1), "id", getPackageName());

            descArray[i] = findViewById(descId);
            titleArray[i] = findViewById(titleId);
            linearLayoutArray[i] = findViewById(linearLayoutId);

            final int finalIndex = i; // Variável final para uso dentro do OnClickListener
            titleArray[i].setOnClickListener(new View.OnClickListener() {
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
        int v = (descArray[index].getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        if (descArray[index].getVisibility() == View.GONE) {
            titleArray[index].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowupcirclefill, 0);
        } else {
            titleArray[index].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowdowncirclefill, 0);
        }

        TransitionManager.beginDelayedTransition(linearLayoutArray[index], new AutoTransition());
        descArray[index].setVisibility(v);
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