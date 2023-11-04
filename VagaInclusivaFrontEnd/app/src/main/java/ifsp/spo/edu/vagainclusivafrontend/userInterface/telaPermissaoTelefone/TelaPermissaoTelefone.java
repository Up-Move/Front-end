package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPermissaoTelefone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa.TelaMapa;

public class TelaPermissaoTelefone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_permissao_telefone);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Button btnPermitir = findViewById(R.id.btn_permitir_telefone);

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
    }
}