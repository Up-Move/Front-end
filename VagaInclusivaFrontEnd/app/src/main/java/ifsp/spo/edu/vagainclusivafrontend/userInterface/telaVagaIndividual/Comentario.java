package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

public class Comentario {
    private int id;
    private String data;
    private String comentario;
    private int nota;
    private int vaga;
    private int respostas;
    private int user;

    private String user_username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getVaga() {
        return vaga;
    }

    public void setVaga(int vaga) {
        this.vaga = vaga;
    }

    public int getRespostas() {
        return respostas;
    }

    public void setRespostas(int respostas) {
        this.respostas = respostas;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setUsername(String user) {
        this.user_username = user;
    }

    // Getter and Setter for 'username'
    public String getUsername() {
        return user_username;
    }

}
