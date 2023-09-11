package ifsp.spo.edu.vagainclusivafrontend.api;

import com.google.gson.JsonObject;

// Classes da API
import ifsp.spo.edu.vagainclusivafrontend.userCadastro.UserCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserLogin;

// Classes Retrofit
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("auth/registration/")
    Call<JsonObject> registerUser(@Body UserCadastro user);

    @POST("auth/login/")
    Call<JsonObject> login(@Body UserLogin user);
}
