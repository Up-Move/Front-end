package ifsp.spo.edu.vagainclusivafrontend.api;

import com.google.gson.JsonObject;

// Classes da API
import java.util.List;

import ifsp.spo.edu.vagainclusivafrontend.userCadastro.UserCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userCadastro.TokenValidationCadastroAPI;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Avaliacao;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario.Resposta;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.Filtro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Comentario;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Vaga;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserLogin;

// Classes Retrofit
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @POST("auth/registration/")
    Call<JsonObject> registerUser(@Body UserCadastro user);

    @POST("auth/login/")
    Call<JsonObject> login(@Body UserLogin user);

    @POST("register-validation/")
    Call<JsonObject> validationUser(@Body TokenValidationCadastroAPI user);

    @GET("avaliacoes/vaga/{id}")
    Call<Vaga> getDataById(@Path("id") int id);

    @POST("avaliacoes/")
    Call<Void> submitAvaliacao(@Body Avaliacao avaliacao);

    @GET("avaliacoes/lista")
    Call<List<Comentario>> getComentarios(
            @Query("vaga_id") int vagaId,
            @Query("first") int first,
            @Query("last") int last
    );

    @GET("avaliacoes/respostas")
    Call<List<Resposta>> getRespostas(
            @Query("avaliacao_id") int comentarioId
    );

    @GET("filtro/user/")
    Call<List<Filtro>> getFiltroByUsername(
            @Query("email") String email
    );

    @POST("filtro/")
    Call<Void> submitFiltro(@Body Filtro filtro);

    @POST("avaliacoes/respostas/")
    Call<Void> submitResposta(@Body Resposta resposta);
}
