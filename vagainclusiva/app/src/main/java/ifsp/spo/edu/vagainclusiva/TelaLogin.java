package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaLogin extends AppCompatActivity {

    EditText emaillogin, senhalogin;
    Button botaologin;
    boolean senhaVisivel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        getSupportActionBar().hide();

        emaillogin = findViewById(R.id.emaillogin);
        senhalogin = findViewById(R.id.senhalogin);
        botaologin = findViewById(R.id.botaologin);
        TextView ViewLogin = findViewById(R.id.viewLogin);
        TextView ViewCadastro = findViewById(R.id.viewCadastro);

        // Sublinhar Login
        ViewLogin.setPaintFlags(ViewLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Trocar para Tela de Cadastro
        ViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        // Lógica para mudar icone de senha para Visivel/Invisivel
        senhalogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= senhalogin.getRight()-senhalogin.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = senhalogin.getSelectionEnd();
                        if(senhaVisivel) {
                            // Imagem que será desenhada quando senha não for mostrada
                            senhalogin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock , 0, R.drawable.eyeclosed, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            senhalogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaVisivel = false;
                        } else {
                            // Imagem que será desenhada quando senha não for mostrada
                            senhalogin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye, 0);

                            // Imagem que será desenhada quando a senha for mostrada
                            senhalogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaVisivel = true;
                        }
                        senhalogin.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Lógica para Login
        botaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillogin.getText().toString();
                String senha = senhalogin.getText().toString();

                UserLogin userLogin = new UserLogin(email, senha);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://18.205.155.235:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API userService = retrofit.create(API.class);

                Call<JsonObject> call = userService.login(userLogin);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TelaLogin.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TelaLogin.this, Map.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TelaLogin.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(TelaLogin.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}