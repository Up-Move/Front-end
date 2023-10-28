package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import java.util.List;

public class Avaliacao {
    private int nota;
    private List<Integer> acess;
    //USER IS STILL HARDCODED, SHOULD BE CHANGED WHEN SESSION CONTROL IS PROPERLY WORKING
    private int user = 1;
    private int vaga;

    private String comentario = "Lorem ipsum";

    private int respostas = 0;

    public Avaliacao(int nota, List<Integer> acess, int vaga) {
        this.nota = nota;
        this.acess = acess;
        this.vaga = vaga;
    }
}
