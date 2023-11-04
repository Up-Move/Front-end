package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros;

import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;

public class Filtro {
    private Acessibilidade[] acessibilidades;
    private int avaliacao;

    public Filtro( Acessibilidade[] acessibilidades, int avaliacao){
        this.acessibilidades = acessibilidades;
        this.avaliacao = avaliacao;
    }

    public Acessibilidade[] getAcessibilidades() {
        return acessibilidades;
    }

    public int getAvaliacao() {
        return avaliacao;
    }
}