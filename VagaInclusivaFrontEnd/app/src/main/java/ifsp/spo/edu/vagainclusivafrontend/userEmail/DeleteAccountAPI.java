package ifsp.spo.edu.vagainclusivafrontend.userEmail;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeleteAccountAPI {
    @POST("deletar-conta/")
    Call<Void> deletarConta(@Body Map<String, String> requestBody);
}
