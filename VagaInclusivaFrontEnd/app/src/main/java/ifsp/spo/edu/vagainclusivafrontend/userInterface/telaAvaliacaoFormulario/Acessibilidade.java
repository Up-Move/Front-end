package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import android.content.Context;
import ifsp.spo.edu.vagainclusivafrontend.R;

public enum Acessibilidade {
    AC1(1, R.string.tela_filtro_calcadas_niveladas),
    AC2(2, R.string.tela_filtro_recuo_cadeira_lado_direito),
    AC3(3, R.string.tela_filtro_recuo_cadeira_lado_esquerdo),
    AC4(4, R.string.tela_filtro_recuo_cadeira_traseira),
    AC5(5, R.string.tela_filtro_trafego),
    AC6(6, R.string.tela_filtro_calcadas_largas),
    AC7(7, R.string.tela_filtro_guia_rebaixada),
    AC8(8, R.string.tela_filtro_piso_tatil),
    AC9(9, R.string.tela_filtro_outro);
    public final int descricaoResourceId;
    public final int descricao;
    Acessibilidade(int descricaoResourceId, int descricao) {
        this.descricaoResourceId = descricaoResourceId;
        this.descricao = descricao;
    }

}
