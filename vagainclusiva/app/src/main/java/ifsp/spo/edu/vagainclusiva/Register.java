package ifsp.spo.edu.vagainclusiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Register extends AppCompatActivity {

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                EditText u;
                EditText p1;
                EditText p2;
                EditText email;
                email = (EditText)findViewById(R.id.inputEmail);
                p1 = (EditText)findViewById(R.id.inputPassword);
                p2 = (EditText)findViewById(R.id.inputConfirmPassword);
                u = (EditText)findViewById(R.id.inputName);

                Log.d("REGISTER", "Login:" + u.getText().toString() + " Password: " + p1.getText().toString()+ " Password2: " + p2.getText().toString()+ " Email: " + email.getText().toString());

                UserRegister reg = new UserRegister(u.getText().toString(), p1.getText().toString(), p2.getText().toString(), email.getText().toString());

                RequestQueue volleyQueue = Volley.newRequestQueue(Register.this);
                String url = "http://10.0.2.2:8000/auth/registration/";

                Gson gson = new Gson();
                String jsonString = gson.toJson(reg);
                JSONObject req = null;
                try {
                    req = new JSONObject(jsonString);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest( Request.Method.POST, url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        CharSequence text = "Erro";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(Register.this, text, duration);
                        toast.show();

                        Log.e("VOLLEY-ERR", error.toString());
                        NetworkResponse networkResponse = error.networkResponse;

                        if (networkResponse != null && networkResponse.data != null) {
                            String jsonError = new String(networkResponse.data);
                            Log.e("REGISTRATION", "Requesting url : " + jsonError);

                        }
                    }
                })
                {
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        try {
                            final String json = new String(
                                    response.data, HttpHeaderParser.parseCharset(response.headers));

                            if (TextUtils.isEmpty(json)) {
                                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                            }

                            return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JsonSyntaxException e) {
                            return Response.error(new ParseError(e));
                        }
                    }

                };

                volleyQueue.add(jsonRequest);

            }
        });
    }
}