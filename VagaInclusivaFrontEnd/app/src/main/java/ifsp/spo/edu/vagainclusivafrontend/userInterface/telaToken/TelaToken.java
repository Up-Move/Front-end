package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaEsqueciSenha.TelaEsqueciSenha;

public class TelaToken extends AppCompatActivity {

    TextView desc;
    TextView title;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_token);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        desc = findViewById(R.id.token_descricao);
        linearLayout = findViewById(R.id.token_layout);
        title = findViewById(R.id.token_title);
        Button botaotoken = findViewById(R.id.botaotoken);

        botaotoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar a mensagem "Senha Atualizada com Sucesso"
                Toast.makeText(TelaToken.this, "Token validado com Sucesso", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de perfil
                Intent intent = new Intent(TelaToken.this, TelaEsqueciSenha.class);
                startActivity(intent);
            }
        });
    }

    public void expand(View view) {
        int v = (desc.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        if (desc.getVisibility() == View.GONE) {
            title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowupcirclefill, 0);
        } else {
            title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrowdowncirclefill, 0);
        }

        TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
        desc.setVisibility(v);
    }
}