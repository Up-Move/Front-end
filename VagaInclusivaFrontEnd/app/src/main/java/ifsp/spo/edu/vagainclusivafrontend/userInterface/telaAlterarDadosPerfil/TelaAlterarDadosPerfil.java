package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAlterarDadosPerfil;

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
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;

public class TelaAlterarDadosPerfil extends AppCompatActivity {

    EditText perfilsenhaatual, perfilnovasenha;
    boolean senhaVisivel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alterar_dados_perfil);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        perfilsenhaatual = findViewById(R.id.alterar_dados_perfil_senha_atual);
        perfilnovasenha = findViewById(R.id.alterar_dados_perfil_senha_novo);
        Button buttonAtualizarEmail = findViewById(R.id.button_atualizar_email);
        Button buttonAtualizarSenha = findViewById(R.id.button_atualizar_senha);

        buttonAtualizarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar a mensagem "Email Alterado com Sucesso"
                Toast.makeText(TelaAlterarDadosPerfil.this, "Email Alterado com Sucesso", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de perfil
                Intent intent = new Intent(TelaAlterarDadosPerfil.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        buttonAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar a mensagem "Senha Atualizada com Sucesso"
                Toast.makeText(TelaAlterarDadosPerfil.this, "Senha Atualizada com Sucesso", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de perfil
                Intent intent = new Intent(TelaAlterarDadosPerfil.this, TelaPerfil.class);
                startActivity(intent);
            }
        });

        // Lógica para mudar icone de senha atual para Visivel/Invisivel
        perfilsenhaatual.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= perfilsenhaatual.getRight()-perfilsenhaatual.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = perfilsenhaatual.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilsenhaatual.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilsenhaatual.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilsenhaatual.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilsenhaatual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        perfilsenhaatual.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para mudar icone de senha nova para Visivel/Invisivel
        perfilnovasenha.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= perfilnovasenha.getRight()-perfilnovasenha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = perfilnovasenha.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilnovasenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_close, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilnovasenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            perfilnovasenha.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_alterar_dados_senha, 0, R.drawable.icon_eye_open, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            perfilnovasenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        perfilnovasenha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}