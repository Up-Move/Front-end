package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPermissaoTelefone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa.TelaMapa;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapaAutenticado.TelaMapaAutenticado;

public class TelaPermissaoTelefone extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_permissao_telefone);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Button btnPermitir = findViewById(R.id.btn_permitir_telefone);
        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        btnPermitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifique se já temos permissão do telefone
                if (ActivityCompat.checkSelfPermission(TelaPermissaoTelefone.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // Se não tivermos permissão, solicite-a ao usuário
                    ActivityCompat.requestPermissions(TelaPermissaoTelefone.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
                } else {
                    Intent intent = new Intent(TelaPermissaoTelefone.this, TelaMapa.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Verificar permissão do telefone no início
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // Se a permissão já foi concedida, verificar a autenticação
            checkAndStartNextScreen();
        }
    }

    private void checkAndStartNextScreen() {
        String authToken = sharedPreferences.getString("AUTH_KEY", "");
        if (!authToken.isEmpty()) {
            // Se o token for válido, inicie a próxima tela autenticada
            Intent intent = new Intent(this, TelaMapaAutenticado.class);
            startActivity(intent);
            finish();
        } else {
            // Se o token não for válido, redirecione para a tela de login (ou outra tela de autenticação)
            Intent intent = new Intent(this, TelaMapa.class);
            startActivity(intent);
            finish();
        }
    }
}