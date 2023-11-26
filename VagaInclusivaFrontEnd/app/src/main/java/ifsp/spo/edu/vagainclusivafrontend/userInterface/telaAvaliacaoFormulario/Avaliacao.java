package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaAvaliacaoFormulario;

import java.util.List;

@SuppressWarnings({"CanBeFinal", "FieldCanBeLocal"})
public class Avaliacao {
    private int nota;
    private List<Integer> acess;
    private int user = 1; // HARDCODED, should be changed when session control is properly working

    private int index;
    private String comentario = "Lorem ipsum";
    private int respostas = 0;

    private String username;

    private int vaga;


    public Avaliacao(int nota, List<Integer> acess, int vaga, String comentario, String username) {
        this.nota = nota;
        this.acess = acess;
        this.vaga = vaga;
        this.comentario = comentario;
        this.username = username;
    }

    // Getter and Setter for 'nota'
    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    // Getter and Setter for 'acess'
    public List<Integer> getAcess() {
        return acess;
    }

    public void setAcess(List<Integer> acess) {
        this.acess = acess;
    }

    // Getter and Setter for 'user'
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    // Getter and Setter for 'vaga'
    public int getVaga() {
        return index;
    }

    public void setVaga(int vaga) {
        this.index = vaga;
    }

    // Getter and Setter for 'comentario'
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    // Getter and Setter for 'respostas'
    public int getRespostas() {
        return respostas;
    }

    public void setRespostas(int respostas) {
        this.respostas = respostas;
    }
}
