package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario.Acessibilidade;

@SuppressWarnings("NullableProblems")
public class Vaga {
    private String localidade;
    private String proximidade;
    private String area;
    private String coordenada;
    private int qtdVaga;
    private Acessibilidade[] acessibilidades;
    private double media;
    public Vaga(String localidade, String coordenada, String proximidade, String area, int qtdVaga, double media, Acessibilidade[] acessibilidades) {
        this.localidade = localidade;
        this.coordenada = coordenada;
        this.proximidade = proximidade;
        this.area = area;
        this.qtdVaga = qtdVaga;
        this.media = media;
        this.acessibilidades = acessibilidades;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getProximidade() {
        return proximidade;
    }

    public String getArea() {
        return area;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public int getQtdVaga() {
        return qtdVaga;
    }

    public double getMedia() {
        return media;
    }

    public Acessibilidade[] getAcessibilidades() {
        return acessibilidades;
    }
}
