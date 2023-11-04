package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import android.content.Context;
import ifsp.spo.edu.vagainclusivafrontend.R;

public enum Acessibilidade {
    AC1(R.string.tela_filtro_guia_rebaixada),
    AC2(R.string.tela_filtro_recuo_cadeira_lado_direito),
    AC3(R.string.tela_filtro_recuo_cadeira_lado_esquerdo),
    AC4(R.string.tela_filtro_recuo_cadeira_traseira),
    AC5(R.string.tela_filtro_piso_tatil),
    AC6(R.string.tela_filtro_calcadas_largas),
    AC7(R.string.tela_filtro_trafego),
    AC8(R.string.tela_filtro_calcadas_niveladas),
    AC9(R.string.tela_filtro_outro);
    private final int descricaoResourceId;
    Acessibilidade(int descricaoResourceId) {
        this.descricaoResourceId = descricaoResourceId;
    }
    public String getDescricao(Context context) {
        return context.getString(descricaoResourceId);
    }
}
