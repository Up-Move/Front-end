package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

public class Vaga {
    private int id;
    private int total_avaliacoes;
    private String media_notas;
    private int vaga;

    @Override
    public String toString() {
        return "Vaga{" +
                "id=" + id +
                ", total_avaliacoes=" + total_avaliacoes +
                ", media_notas='" + media_notas + '\'' +
                ", vaga=" + vaga +
                '}';
    }


    public String getMediaNota(){ return this.media_notas; }
    public Integer getId(){ return this.id; }
}
