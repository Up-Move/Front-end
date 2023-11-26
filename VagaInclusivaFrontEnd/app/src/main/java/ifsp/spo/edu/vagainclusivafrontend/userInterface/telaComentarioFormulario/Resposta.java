package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaComentarioFormulario;

public class Resposta {
    private String comentario;
    private String user;
    private Integer avaliacao;

    // Constructor
    public Resposta(String comentario, String user, Integer avaliacao) {
        this.comentario = comentario;
        this.user = user;
        this.avaliacao = avaliacao;
    }

    // Getters and Setters
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }
}
