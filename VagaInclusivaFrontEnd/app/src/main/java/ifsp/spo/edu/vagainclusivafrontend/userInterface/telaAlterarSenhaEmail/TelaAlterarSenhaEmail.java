package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarSenhaEmail;

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
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken.TelaToken;

public class TelaAlterarSenhaEmail extends AppCompatActivity {

    TextView desc;
    TextView title;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alterar_senha_email);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        desc = findViewById(R.id.email_alterar_senha_descricao);
        linearLayout = findViewById(R.id.email_layout);
        title = findViewById(R.id.email_alterar_senha_title);
        Button botaoemail = findViewById(R.id.botaoemail);

        botaoemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar a mensagem "Senha Atualizada com Sucesso"
                Toast.makeText(TelaAlterarSenhaEmail.this, "Token enviado com Sucesso", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de perfil
                Intent intent = new Intent(TelaAlterarSenhaEmail.this, TelaToken.class);
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