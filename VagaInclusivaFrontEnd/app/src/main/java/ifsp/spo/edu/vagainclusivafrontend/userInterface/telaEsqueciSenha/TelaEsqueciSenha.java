package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaEsqueciSenha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarSenhaEmail.TelaAlterarSenhaEmail;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaToken.TelaToken;

public class TelaEsqueciSenha extends AppCompatActivity {

    EditText SenhaNova, ConfirmarSenhaNova;
    boolean senhaVisivel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_esqueci_senha);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SenhaNova = findViewById(R.id.esqueci_senha_nova);
        ConfirmarSenhaNova = findViewById(R.id.esqueci_senha_nova_confirma);
        Button button_atualizar_senha = findViewById(R.id.button_atualizar_senha);

        button_atualizar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar a mensagem "Senha Atualizada com Sucesso"
                Toast.makeText(TelaEsqueciSenha.this, "Senha Alterada com Sucesso", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de perfil
                Intent intent = new Intent(TelaEsqueciSenha.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        // Lógica para mudar cone de senha para Visivel/Invisivel
        SenhaNova.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= SenhaNova.getRight()-SenhaNova.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = SenhaNova.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            SenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            SenhaNova.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            SenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            SenhaNova.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        SenhaNova.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para mudar icone do Confirmarsenha para Visivel/Invisivel
        ConfirmarSenhaNova.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= ConfirmarSenhaNova.getRight()-ConfirmarSenhaNova.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = ConfirmarSenhaNova.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenhaNova.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            ConfirmarSenhaNova.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_lock_blue, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            ConfirmarSenhaNova.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        ConfirmarSenhaNova.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}