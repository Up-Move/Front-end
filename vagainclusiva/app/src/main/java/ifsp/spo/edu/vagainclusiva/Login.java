package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button btnLogin;

    public void requestReadPhoneStatePermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{
                                android.Manifest.permission.READ_PHONE_STATE
                        },
                        0x1
                );
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btnLogin);

        this.requestReadPhoneStatePermission(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                EditText u;
                EditText p;
                u = (EditText)findViewById(R.id.inputLoginEmail);
                p = (EditText)findViewById(R.id.inputLoginPassword);

                Log.d("LOGIN", "Login:" + u.getText().toString() + " Password: " + p.getText().toString());

                User user = new User(u.getText().toString(), p.getText().toString());

                RequestQueue volleyQueue = Volley.newRequestQueue(Login.this);
                String url = "http://18.205.155.235:8000/auth/login/";

                Gson gson = new Gson();
                String jsonString = gson.toJson(user);
                JSONObject req = null;
                try {
                    req = new JSONObject(jsonString);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest( Request.Method.POST, url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("VOLLEY", response.toString());

                        Intent intent = new Intent(Login.this, Map.class);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());

                        CharSequence text = "Erro";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(Login.this, text, duration);
                        toast.show();

                    }
                }) {
                };

                volleyQueue.add(jsonRequest);
            }
        });
    }
}